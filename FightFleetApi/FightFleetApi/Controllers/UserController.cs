using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using FightFleet;
using System.IO;
using System.Web.Script.Serialization;
using FightFleet.Exceptions;
using UserManager = FightFleet.UserManager;

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

            try
            {
                new UserManager().SaveUser(user);
                return Authenticate(userName, password);
            }
            catch (UserNameExistsException)
            {
                return Json("username not unique", JsonRequestBehavior.AllowGet);
            }
            catch (Exception ex)
            {
                return Json("error: " + ex.Message, JsonRequestBehavior.AllowGet);
            }
        }

        [HttpGet]
        public JsonResult Authenticate(string userName, string password)
        {
            var model = new UserManager().Authenticate(userName, password);
            if(string.IsNullOrEmpty(model.UserName))
                return Json("invalid credentials", JsonRequestBehavior.AllowGet);

            return Json(model, JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult GetUser(string userName, string accessToken)
        {
            return Json("", JsonRequestBehavior.AllowGet);
        }

        [HttpGet]
        public JsonResult TestGet()
        {
            return Json("GOT!", JsonRequestBehavior.AllowGet);
        }

        [HttpPost]
        public JsonResult TestPost()
        {
            string inputContent;
            using (var sr = new StreamReader(Request.InputStream, System.Text.Encoding.UTF8))
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
