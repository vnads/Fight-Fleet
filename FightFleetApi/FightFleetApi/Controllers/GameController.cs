using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FightFleet.Managers;

namespace FightFleetApi.Controllers
{
    public class GameController : Controller
    {
        private AuthenticationManager manager = new AuthenticationManager();
        //
        // GET: /Game/
        [HttpGet]
        public JsonResult GetGamesForUser(int userId, string accessToken)
        {
            if(!manager.IsValid(userId, new Guid(accessToken)))
                return Json("Invalid Access Token", JsonRequestBehavior.AllowGet);
            return Json(new GameManager().GetGamesForUser(userId), JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult GetGame(int gameId, string accessToken)
        {
            return Json("", JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult MakeMove(int gameId, int userId, string moveData, string accessToken)
        {
            return Json("", JsonRequestBehavior.AllowGet);
        }

        public JsonResult CreateGame(int userId, int opponentId, string accessToken)
        {
            //var model = new BoardManager().CreateRandomBoard(userId);
            return Json("", JsonRequestBehavior.AllowGet);
        }

        public JsonResult TestCreateGame()
        {
            return Json(new GameManager().CreateGame(1).ToJson(), JsonRequestBehavior.AllowGet);
        }

    }
}
