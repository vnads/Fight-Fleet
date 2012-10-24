using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FightFleet;

namespace FightFleetApi.Controllers
{
    public class AccountController : Controller
    {
        public JsonResult CreateUser(string userName, string password)
        {
            var user = new FightFleet.User
            {
                UserName = userName,
                Password
            };
        }

        public JsonResult Authenticate(string userName, string password)
        {

        }

    }
}
