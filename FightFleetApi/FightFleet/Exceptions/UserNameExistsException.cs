using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet.Exceptions
{
    public class UserNameExistsException : Exception
    {
        public UserNameExistsException(string message) : base(message) { }
    }
}
