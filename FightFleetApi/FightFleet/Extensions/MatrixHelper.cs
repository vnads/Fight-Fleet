using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet.Extensions
{
    public static class MatrixHelper
    {
        public static string ToJsonMatrix(this int[,] matrix)
        {
            if (matrix.Length == 0)
                return "[]";
            var sb = new StringBuilder();
            sb.Append("[[");
            for (int i = 0; i < 10; i++)
            {
                if (i != 0)
                    sb.Append("],[");
                for (int j = 0; j < 10; j++)
                {
                    sb.Append(matrix[i, j].ToString());
                }
            }

            sb.Append("]]");

            return sb.ToString();
        }
    }
}
