using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace FightFleet.Models
{
    public class GameModel
    {
        public int GameId { get; set; }
        public string GameStatus { get; set; }
        public int UserId { get; set; }
        public int OpponentUserId { get; set; }
        public int[,] UserBoardData { get; set; }
        public int[,] OpponentBoardData { get; set; }
        public int CurrentPlayerId { get; set; }
    }
}
