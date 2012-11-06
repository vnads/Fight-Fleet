using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using FightFleet.Models;

namespace FightFleet.Managers
{
    public class BoardManager
    {
        public BoardModel CreateRandomBoard(int userId)
        {
            var generator = new RandomlyGenerateBoard();

            using (var ctx = new FightFleetDataContext())
            {
                ctx.Boards.InsertOnSubmit(new Board
                {
                    BoardData = generator.Board.ToString(),
                    UserId = userId
                });

                ctx.SubmitChanges();
            }

            return new BoardModel
            {
                BoardData = generator.Board.BoardCells, 
                UserId = userId
            };

            
        }
    }
}
