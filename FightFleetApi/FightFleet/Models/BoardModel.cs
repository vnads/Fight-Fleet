using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml;

namespace FightFleet.Models
{
    public class BoardModel
    {
        public int UserId { get; set; }
        public int[,] BoardData { get; set; }
    }
}
