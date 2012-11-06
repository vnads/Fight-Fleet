using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet
{
    public class GameBoard
    {
        public const int XSIZE = 10;
        public const int YSIZE = 10;

        public int[,] BoardCells { get; set; }

        public GameBoard()
        {
            BoardCells = new int[XSIZE, XSIZE]; 

            for (int i = 0; i < XSIZE; i++) {
                for (int j = 0; j < XSIZE; j++) {
                    BoardCells[i, j] = (int)BoardCellStatus.Blank;
                }
            }
        }

        public override string ToString()
        {
            return new System.Web.Script.Serialization.JavaScriptSerializer().Serialize(this.BoardCells);
        }
    }
    
}