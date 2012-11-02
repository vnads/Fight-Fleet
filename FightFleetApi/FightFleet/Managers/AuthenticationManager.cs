using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet.Managers
{
    public class AuthenticationManager
    {
        public bool IsValid(int userId, Guid accessToken)
        {
            using (var ctx = new FightFleetDataContext())
            {
                var token = ctx.Authentications.FirstOrDefault(c => c.UserId == userId && c.AccessToken == accessToken);
                if (token == null || token.ExpiresOn <= DateTime.Now)
                    return false;

                return true;
            }
        }   
    }
}
