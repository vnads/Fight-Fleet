using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FightFleet;
using System.IO;
using System.Web.Script.Serialization;

namespace FightFleetApi.Controllers
{
    public class UserController : Controller
    {
        [HttpGet]
        public JsonResult CreateUser(string userName, string password)
        {
            var user = new FightFleet.User
            {   
                UserName = userName,
                Password = password
            };

            return Json(user, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult Authenticate(string userName, string password)
        {
            return Json("", JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult GetUser(string userName, string accessToken)
        {
            return Json("", JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult TestPost()
        {
            string inputContent;
            using (var sr = new StreamReader(Request.InputStream))
            {
                inputContent = sr.ReadToEnd();
            }


            dynamic json = new JavaScriptSerializer().DeserializeObject(inputContent);

            if (json["test"] != null)
            {
                return Json("success", JsonRequestBehavior.AllowGet);
            }

            return Json("could not find post data", JsonRequestBehavior.AllowGet);
        }
    }
}
