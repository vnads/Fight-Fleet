using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using FightFleet.Models;

namespace FightFleet.Managers
{
    public class GameManager
    {
        public IEnumerable<UserGameModel> GetGamesForUser(int userId)
        {
            using (var ctx = new FightFleetDataContext())
            {
                var games = ctx.Games.Where(c => (c.Player1Id == userId || c.Player2Id == userId) && c.GameStatusId != (int)GameStatus.Finished)
                    .Select(c => new
                    {
                        CreatedOn = c.CreatedDate.ToShortDateString(),
                        GameId = c.GameId,
                        GameStatusId = c.GameStatusId,
                        LastMove = c.Moves.OrderByDescending(x => x.CreatedDate).FirstOrDefault(),
                        c.Player1Id,
                        c.Player2Id,
                        Player1UserName = c.Player1.UserName,
                        Player2UserName = c.Player2.UserName
                    });

                foreach (var game in games)
                    yield return new UserGameModel
                    {
                        CreatedOn = game.CreatedOn,
                        GameId = game.GameId,
                        GameStatus = ((GameStatus)game.GameStatusId).ToString(),
                        LastMoveOn = game.LastMove == null ? "No moves yet" : game.LastMove.CreatedDate.ToShortDateString(),
                        OpponentUserName = game.Player1Id == userId ? game.Player2UserName : game.Player1UserName,
                        OpponentUserId = game.Player1Id == userId ? (game.Player2Id ?? 0) : game.Player1Id,
                        LastMoveBy = game.LastMove == null ? -1 : game.LastMove.UserId
                    };
            }
        }

        public GameModel CreateGame(int userId)
        {
            Game game;
            var generator = new RandomlyGenerateBoard();
            generator.Generate();

            using (var ctx = new FightFleetDataContext())
            {
                var pendingGame = ctx.Games.FirstOrDefault(c => c.Player1Id != userId && 
                                                            (c.Player2Id == null || c.Player2Id != userId) && 
                                                            c.GameStatusId == (int)GameStatus.Pending);
                if (pendingGame != null)
                {
                    game = pendingGame;
                    

                    pendingGame.Player2Id = userId;
                    pendingGame.GameStatusId = (int)GameStatus.InProgress;
                    ctx.SubmitChanges();
                }
                else
                {
                    game = new Game
                    {
                        Player1Id = userId,
                        GameStatusId = (int)GameStatus.Pending,
                        CreatedDate = DateTime.Now
                    };
                    ctx.Games.InsertOnSubmit(game);
                }
                game.Boards.Add(new Board
                    {
                        UserId = userId,
                        BoardData = generator.Board.ToString()
                    });
                ctx.SubmitChanges();

                return GetGameModel(game.GameId, userId);
            }
        }

        public GameModel GetGameModel(int gameId, int currentUserId)
        {
            using (var ctx = new FightFleetDataContext())
            {
                var game = ctx.Games.FirstOrDefault(c => c.GameId == gameId);

                if (game == null)
                    return new GameModel();

                var boards = ctx.Boards.Where(c => c.GameId == gameId);
                var userBoard = game.Player1Id == currentUserId ? boards.First(c => c.UserId == game.Player1Id) : boards.First(c => c.UserId == game.Player2Id);
                
                var opponentBoard = game.Player1Id == currentUserId ? boards.FirstOrDefault(c => c.UserId == game.Player2Id) : boards.FirstOrDefault(c => c.UserId == game.Player1Id);
                var lastMove = ctx.Moves.OrderByDescending(c => c.CreatedDate).FirstOrDefault(c => c.GameId == gameId);

                int currentPlayerId;
                if (lastMove == null)
                    currentPlayerId = game.Player2Id.HasValue ? game.Player2Id.Value : game.Player1Id;
                else
                    currentPlayerId = game.Player1Id;

                var moves = Move.GetForGame(game.GameId).ToList();
                if (moves.Any())
                {
                    opponentBoard.BoardData = PopulateBoardData(opponentBoard, moves, opponentBoard.UserId);
                    userBoard.BoardData = PopulateBoardData(userBoard, moves, userBoard.UserId);
                }

                var opponent = currentUserId != game.Player1Id ? game.Player1 : (game.Player2 ?? new User());
                
                return new GameModel
                {
                    CurrentPlayerId = currentPlayerId,
                    GameId = gameId,
                    GameStatus = ((GameStatus)game.GameStatusId).ToString(),
                    OpponentBoardData = opponentBoard == null ? "" : opponentBoard.BoardData,
                    OpponentUserId = opponentBoard == null ? 0 : opponentBoard.UserId,
                    UserBoardData = userBoard.BoardData,
                    UserId = currentUserId,
                    LastMoveBy =  lastMove == null ? 0 : lastMove.UserId,
                    OpponentUsername = opponent.UserName
                };
            }
        }

        public MoveResultModel MakeMove(int userId, int gameId, int position)
        {
            using (var ctx = new FightFleetDataContext())
            {
                var game = ctx.Games.First(c => c.GameId == gameId);
                if(game.Player1Id != userId && (game.Player2Id == null || game.Player2Id != userId))
                    return new MoveResultModel{ MoveResult = MoveResult.InvalidGame.ToString() };

                var board = game.Boards.First(c => c.UserId != userId);

                var moves = ctx.Moves.OrderByDescending(c => c.CreatedDate).Where(c => c.GameId == gameId).ToList();

                //first we check all moves to make sure it's this person's turn
                if (moves.Count != 0 && moves.ElementAt(0).UserId == userId)
                    return new MoveResultModel{ MoveResult = MoveResult.NotYourTurn.ToString() };

                //now we only look at their moves
                moves = moves.Where(c => c.UserId == userId).ToList();

                if (moves.Any(c => c.Position == position))
                    return new MoveResultModel{ MoveResult = MoveResult.PositionAlreadyPlayed.ToString() };

                ctx.Moves.InsertOnSubmit(new Move
                {
                    CreatedDate = DateTime.Now,
                    UserId = userId,
                    GameId = gameId,
                    Position = position,
                });


                var boardData = PopulateBoardData(board, moves, userId);

                if (boardData.IndexOf('1') < 0)
                    game.GameStatusId = (int)GameStatus.Finished;


                ctx.SubmitChanges();

                return new MoveResultModel
                {
                    GameStatus = ((GameStatus)game.GameStatusId).ToString(),
                    MoveResult = board.BoardDataArray[position] == "0" ? MoveResult.Miss.ToString() : MoveResult.Hit.ToString(),
                    Xcoord = position % 10,
                    Ycoord = position / 10
                };
                 
            }
        }

        private string PopulateBoardData(Board board, IEnumerable<Move> moves, int userId)
        {
            var moveModels = moves.Select(c => new MoveModel
            {
                CreatedDate = c.CreatedDate,
                GameId = c.GameId,
                MoveId = c.MoveId,
                Position = c.Position,
                UserId = c.UserId
            });

            return PopulateBoardData(board, moveModels, userId);
        }

        private string PopulateBoardData(Board board, IEnumerable<MoveModel> moves, int userId)
        {
            var boardMatrix = board.BoardDataArray;

            string matrixStr = "[";

            for (int i = 0; i < boardMatrix.Length; i++)
            {
                if (i != 0)
                    matrixStr += ",";
                var move = moves.FirstOrDefault(c => c.Position == i && c.UserId != userId);

                if (boardMatrix[i] == "0")
                    matrixStr += (move == null ? "0" : "3");

                else
                    matrixStr += (move == null ? "1" : "2");
            }

            matrixStr += "]";

            return matrixStr;
        }
    }
}
