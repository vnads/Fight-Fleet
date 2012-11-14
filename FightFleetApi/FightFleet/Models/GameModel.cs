using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using FightFleet.Extensions;

namespace FightFleet.Models
{
    public class GameModel
    {
        public int GameId { get; set; }
        public string GameStatus { get; set; }
        public int UserId { get; set; }
        public int OpponentUserId { get; set; }
        public string UserBoardData { get; set; }
        public string OpponentBoardData { get; set; }
        public int CurrentPlayerId { get; set; }       
    }


}
