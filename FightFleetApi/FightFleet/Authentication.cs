using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet
{
    partial class Authentication
    {
        public void Save()
        {
            ValidateToken();
            using (var ctx = new FightFleetDataContext())
            {
                if (this.AuthenticationId == 0)
                    ctx.Authentications.InsertOnSubmit(this);
                else
                    ctx.Authentications.Attach(this, true);

                ctx.SubmitChanges();
            }
        }

        private void ValidateToken()
        {
            if (this.AccessToken == Guid.Empty)
                this.AccessToken = Guid.NewGuid();
            if (this.UserId == 0)
                throw new MissingFieldException("UserId");
            if (this.CreatedOn == DateTime.MinValue)
                this.CreatedOn = DateTime.Now;
            if (this.ExpiresOn == DateTime.MinValue)
                this.ExpiresOn = DateTime.Now.AddDays(7);
        }
    }
}
