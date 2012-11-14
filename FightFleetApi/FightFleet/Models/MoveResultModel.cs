using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet.Models
{
    public class MoveResultModel
    {
        public string GameStatus { get; set; }
        public string MoveResult { get; set; }
        public int Xcoord { get; set; }
        public int Ycooard { get; set; }
    }

    public enum MoveResult
    {
        Hit,
        Miss,
        InvalidGame,
        NotYourTurn,
        PositionAlreadyPlayed
    }
}
