using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.Script.Serialization;

namespace FightFleet
{
    partial class Board
    {
        public int[,] ToMatrix()
        {
            var matrix = new int[10, 10];
            dynamic data = new JavaScriptSerializer().DeserializeObject(this.BoardData);
            for (int i = 0; i < data.Length; i++)
            {
                for (int j = 0; j < data[i].Length; j++)
                {
                    matrix[i, j] = data[i][j];
                }
            }

            return matrix;
        }
    }
}
