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
                        OpponentUserId = game.Player1Id == userId ? game.Player2Id : game.Player1Id,
                        LastMoveBy = game.LastMove == null ? -1 : game.LastMove.UserId
                    };
            }
        }
    }
}
