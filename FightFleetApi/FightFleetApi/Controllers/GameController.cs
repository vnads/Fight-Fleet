using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace FightFleetApi.Controllers
{
    public class GameController : Controller
    {
        //
        // GET: /Game/
        [HttpGet]
        public JsonResult GetGamesForUser(int userId, string accessToken)
        {
            return Json("", JsonRequestBehavior.AllowGet);
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
            return Json("", JsonRequestBehavior.AllowGet);
        }
    }
}
