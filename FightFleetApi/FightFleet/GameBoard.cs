using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.Script.Serialization;

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
            var matrix = new JsonMatrix();

            for (int i = 0; i < YSIZE; i++)
            {
                matrix.Matrix.Add(new int[XSIZE]);
                for (int j = 0; j < XSIZE; j++)
                {
                    matrix.Matrix[i][j] = this.BoardCells[i,j];
                }
            }

            return new JavaScriptSerializer().Serialize(matrix.Matrix);
        }

        private class JsonMatrix
        {
            public IList<int[]> Matrix { get; set; }

            public JsonMatrix()
            {
                Matrix = new List<int[]>();
            }
        }
    }
}