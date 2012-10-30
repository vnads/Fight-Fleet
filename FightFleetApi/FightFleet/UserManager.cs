using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;
using FightFleet.Exceptions;
using FightFleet.Models;

namespace FightFleet
{
    public class UserManager
    {
        #region Static

        public UserAuthenticationModel Authenticate(string userName, string passWord)
        {
            var model = new UserAuthenticationModel();
            var encryptedPassword = HashPassword(passWord);
            using (var ctx = new FightFleetDataContext())
            {
                var user = ctx.Users.FirstOrDefault(c => c.UserName.ToLower() == userName.ToLower() 
                    && c.Password == encryptedPassword);
                if (user == null)
                    return model;

                model.UserName = user.UserName;
                model.UserId = user.UserId;
                var authentication = user.Authentications.FirstOrDefault(c => c.ExpiresOn <= DateTime.Now);
                if (authentication == null)
                {
                    authentication = new Authentication
                    {
                        ExpiresOn = DateTime.Now.AddDays(7),
                        AccessToken = Guid.NewGuid(),
                        CreatedOn = DateTime.Now,

                    };
                    user.Authentications.Add(authentication);
                    ctx.SubmitChanges();
                }
                model.AccessToken = authentication.AccessToken;
            }

            return model;
        }

        #endregion

        public void SaveUser(User user)
        {
            if (user.UserId == 0)
                Create(user);
            else
                Update(user);
        }

        public static string HashPassword(string password)
        {
            byte[] bytes = Encoding.Unicode.GetBytes(password);
            byte[] inArray = HashAlgorithm.Create("SHA1").ComputeHash(bytes);
            return Convert.ToBase64String(inArray);
            
        }

        private void Update(User user)
        {
            using (var ctx = new FightFleetDataContext())
            {

                ctx.Users.Attach(user, true);
                ctx.SubmitChanges();
            }
        }

        private void Create(User user)
        {
            using (var ctx = new FightFleetDataContext())
            {
                var existingUserName = ctx.Users.FirstOrDefault(c => c.UserName.ToLower() == user.UserName.ToLower());
                if (existingUserName != null)
                    throw new UserNameExistsException("Username Is In Use");
                if (user.CreatedDate == DateTime.MinValue)
                    user.CreatedDate = DateTime.Now;
                user.Password = HashPassword(user.Password);
                ctx.Users.InsertOnSubmit(user);
                ctx.SubmitChanges();
            }
        }
    }
}
