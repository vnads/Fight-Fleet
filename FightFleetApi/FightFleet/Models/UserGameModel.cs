using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet.Models
{
    public class UserGameModel
    {
        public int GameId { get; set; }
        public int OpponentUserId { get; set; }
        public string OpponentUserName { get; set; }
        public string CreatedOn { get; set; }
        public string GameStatus { get; set; }
        public string LastMoveOn { get; set; }
    }


}
