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
                return ctx.Games.Where(c => (c.Player1Id == userId || c.Player2Id == userId) && c.GameStatusId != (int)GameStatus.Finished)
                    .Select(c => new UserGameModel
                    {
                        CreatedOn = c.CreatedDate.ToShortDateString(),
                        GameId = c.GameId,
                        GameStatus = ((GameStatus) c.GameStatusId).ToString(),
                        LastMoveOn = c.GameStatusId == (int)GameStatus.Pending ? DateTime.MinValue.ToShortDateString() : c.Moves.OrderByDescending(x => x.CreatedDate).First().CreatedDate.ToShortDateString(),
                        OpponentUserId = c.Player1Id == userId ? c.Player2Id : c.Player1Id,
                        OpponentUserName = c.Player1.UserId == userId ? c.Player2.UserName : c.Player1.UserName
                    });
            }
        }
    }
}
