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
        public JsonResult GetGame(int gameId, int userId, string accessToken)
        {   
            if(!manager.IsValid(userId, new Guid(accessToken)))
                return Json("Invalid AccessToken", JsonRequestBehavior.AllowGet);
            var game = new GameManager().GetGameModel(gameId, userId);
            if(game.GameId == 0)
                return Json("Invalid Game For User Id");

            return Json(game, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult MakeMove(int gameId, int userId, int x, int y, string accessToken)
        {
            if(!manager.IsValid(userId, new Guid(accessToken)))
                return Json("Invalid Access Token", JsonRequestBehavior.AllowGet);
            
            var position = 10 * y + x;

            var game = new GameManager().MakeMove(userId, gameId, position);

            if (game == null)
                return Json("ERROR ERROR ERROR", JsonRequestBehavior.AllowGet);

            return Json(game, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult CreateGame(int userId, string accessToken)
        {
            if (!manager.IsValid(userId, new Guid(accessToken)))
                return Json("Invalid Access Token", JsonRequestBehavior.AllowGet);

            var game = new GameManager().CreateGame(userId);


            return Json(game, JsonRequestBehavior.AllowGet);
        }

        public JsonResult TestCreateGame()
        {
            return Json(new GameManager().CreateGame(1), JsonRequestBehavior.AllowGet);
        }

    }
}
