using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet.Models
{
    public class UserAuthenticationModel
    {
        public string UserName { get; set; }
        public int UserId { get; set; }
        public Guid AccessToken { get; set; }
    }
}
