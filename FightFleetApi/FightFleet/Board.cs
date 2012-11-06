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
            return new JavaScriptSerializer().Deserialize<int[,]>(this.BoardData);
        }
    }
}
