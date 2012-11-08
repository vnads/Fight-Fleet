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
        public int[,] UserBoardData { get; set; }
        public int[,] OpponentBoardData { get; set; }
        public int CurrentPlayerId { get; set; }

        public class JsonGameModel
        {
            public int GameId { get; set; }
            public string GameStatus { get; set; }
            public int UserId { get; set; }
            public int OpponentUserId { get; set; }
            public string UserBoardData { get; set; }
            public string OpponentBoardData { get; set; }
            public int CurrentPlayerId { get; set; }
        }

        public JsonGameModel ToJson()
        {
            return new JsonGameModel
            {
                GameId = this.GameId,
                CurrentPlayerId = this.CurrentPlayerId,
                GameStatus = this.GameStatus,
                OpponentBoardData = this.OpponentBoardData.ToJsonMatrix(),
                UserBoardData = this.UserBoardData.ToJsonMatrix(),
                UserId = this.UserId,
                OpponentUserId = this.OpponentUserId
            };
        }
    }


}
