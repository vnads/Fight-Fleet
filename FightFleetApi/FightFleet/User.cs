using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Security.Cryptography;

namespace FightFleet
{
    partial class User
    {
        public void Save()
        {
            using (var ctx = new FightFleetDataContext())
            {
                if (this.UserId == 0)
                    ctx.Users.InsertOnSubmit(this);
                else
                    ctx.Users.
            }
        }

        private void HashPassword()
        {
            var provider = new SHA1CryptoServiceProvider();
            var encoding = new UnicodeEncoding();
            this.Password = provider.ComputeHash(encoding.GetBytes(this.Password)).ToString();    
        }
    }
}
