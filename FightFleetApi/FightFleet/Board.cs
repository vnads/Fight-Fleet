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

        public CellStatus[,] BoardCells { get; set; }

        public GameBoard()
        {
            BoardCells = new CellStatus[XSIZE, XSIZE]; 

            for (int i = 0; i < XSIZE; i++) {
                for (int j = 0; j < XSIZE; j++) {
                    BoardCells[i, j] = CellStatus.Blank;
                }
            }
        }
    }
    
    public enum CellStatus
    {
        Blank,
        Ship,
        Hit
    }

    public enum ShipDirections
    {
        Up = 1,
        Right = 2,
        Down = 3,
        Left = 4
    }
}