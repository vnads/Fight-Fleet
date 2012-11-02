using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using FightFleet;
using FightFleet.Managers;

namespace FightFleetTest
{
    [TestClass()]
    public class AuthenticationManagerTests
    {
        [TestMethod]
        public void TestSaveNew()
        {
            var authToken = new Authentication
            {
                UserId = 4,
                AccessToken = Guid.NewGuid(),
                CreatedOn = DateTime.Now,
                ExpiresOn = DateTime.Now.AddDays(7),
            };
            authToken.Save();

            Assert.AreNotEqual(authToken.AuthenticationId, 0);
        }

        [TestMethod]
        [ExpectedException(typeof(MissingFieldException))]
        public void TestSaveNoUserId()
        {
            var authToken = new Authentication();
            authToken.Save();
            //no need to do anything
        }

        [TestMethod]
        public void TestSaveOnlyUserIdEverythingElseGetsPopulated()
        {
            var authToken = new Authentication { UserId = 4 };
            authToken.Save();

            Assert.AreNotEqual(authToken.AuthenticationId, 0);
            Assert.AreNotEqual(authToken.AccessToken, Guid.Empty);
            Assert.AreNotEqual(authToken.CreatedOn, DateTime.MinValue);
            Assert.AreNotEqual(authToken.ExpiresOn, DateTime.MinValue);
        }

        [TestMethod]
        public void TestValidTokenReturnsTrue()
        {
            var token = Guid.NewGuid();
            var authToken = new Authentication { UserId = 4, AccessToken = token };
            authToken.Save();

            Assert.AreEqual(new AuthenticationManager().IsValid(4, token), true);
        }

        [TestMethod]
        public void TestBogusToeknReturnsFalse()
        {
            Assert.AreEqual(new AuthenticationManager().IsValid(4, Guid.Empty), false);
        }

        [TestMethod]
        public void TestExpiredTokenReturnsFalse()
        {
            var token = Guid.NewGuid();
            var authToken = new Authentication { UserId = 4, AccessToken = token, ExpiresOn = DateTime.Now.AddDays(-1) };
            authToken.Save();

            Assert.AreEqual(new AuthenticationManager().IsValid(4, token), false);
        }
    }
}
