using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Web.Script.Serialization;

namespace FightFleet
{
    partial class Board
    {
        public string[] BoardDataArray
        {
            get
            {
                return this.BoardData.Replace("[", "").Replace("]", "").Split(',');
            }
        }
    }
}
