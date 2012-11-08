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
                var pendingGame = ctx.Games.FirstOrDefault(c => c.Player1Id != userId && c.Player2Id != userId && c.GameStatusId == (int)GameStatus.Pending);
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

                return ReturnGameModel(game.GameId, userId);
            }
        }

        private GameModel ReturnGameModel(int gameId, int currentUserId)
        {
            using (var ctx = new FightFleetDataContext())
            {
                var game = ctx.Games.First(c => c.GameId == gameId);
                var boards = ctx.Boards.Where(c => c.GameId == gameId);
                var userBoard = game.Player1Id == currentUserId ? boards.First(c => c.UserId == game.Player1Id) : boards.First(c => c.UserId == game.Player2Id);
                var opponentBoard = game.Player1Id == currentUserId ? boards.FirstOrDefault(c => c.UserId == game.Player2Id) : boards.FirstOrDefault(c => c.UserId == game.Player1Id);
                var lastMove = ctx.Moves.OrderByDescending(c => c.CreatedDate).FirstOrDefault(c => c.GameId == gameId);

                int currentPlayerId;
                if (lastMove == null)
                    currentPlayerId = game.Player2Id.HasValue ? game.Player2Id.Value : game.Player1Id;
                else
                    currentPlayerId = game.Player1Id;

                return new GameModel
                {
                    CurrentPlayerId = currentPlayerId,
                    GameId = gameId,
                    GameStatus = ((GameStatus)game.GameStatusId).ToString(),
                    OpponentBoardData = opponentBoard == null ? new int[0, 0] : opponentBoard.ToMatrix(),
                    OpponentUserId = opponentBoard == null ? 0 : opponentBoard.UserId,
                    UserBoardData = userBoard.ToMatrix(),
                    UserId = currentUserId
                };
            }
        }
    }
}
