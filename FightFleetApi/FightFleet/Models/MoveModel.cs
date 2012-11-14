using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet.Models
{
    public class MoveModel
    {
        public int MoveId { get; set; }
        public int GameId { get; set; }
        public int Position { get; set; }
        public int UserId { get; set; }
        public DateTime CreatedDate { get; set; }
    }
}
