using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using FightFleet.Models;

namespace FightFleet
{
    partial class Move
    {
        public static IEnumerable<MoveModel> GetForGame(int gameId)
        {
            using (FightFleetDataContext ctx = new FightFleetDataContext())
            {
                return ctx.Moves.Where(c => c.GameId == gameId).ToList().Select(c => new MoveModel
                    {
                        CreatedDate = c.CreatedDate,
                        GameId = gameId,
                        MoveId = c.MoveId,
                        Position = c.Position,
                        UserId = c.UserId
                    });

            }
        }
    }
}
