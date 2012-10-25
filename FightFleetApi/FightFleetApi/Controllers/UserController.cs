using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FightFleet;

namespace FightFleetApi.Controllers
{
    public class UserController : Controller
    {
        [HttpPost]
        public JsonResult CreateUser(string userName, string password)
        {
            var user = new FightFleet.User
            {
                UserName = userName,
                Password = password
            };

            return Json("", JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult Authenticate(string userName, string password)
        {
            return Json("", JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult GetUser(string userName, string accessToken)
        {
            return Json("", JsonRequestBehavior.AllowGet);
        }

    }
}
