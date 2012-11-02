using FightFleet;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using System;
using FightFleet.Managers;

namespace FightFleetTest
{
    /// <summary>
    ///This is a test class for AuthenticationTest and is intended
    ///to contain all AuthenticationTest Unit Tests
    ///</summary>
    [TestClass()]
    public class UserManagerTests
    {
        [TestMethod]
        public void TestAuthentication()
        {
            var user = new UserManager().Authenticate("test2", "test");
            Assert.AreEqual(user.UserId, 2);
            Assert.AreEqual(user.UserName, "test2");
            Assert.IsNotNull(user.AccessToken);

        }

        [TestMethod]
        public void TestCreateValidUser()
        {
            var user = new User
            {
                UserName = "testuser" + DateTime.Now.ToString(),
                Password = "test",
            };

            new UserManager().SaveUser(user);

            Assert.AreNotEqual(user.UserId, 0);
            Assert.AreNotEqual(user.Password, "test");
        }

        [TestMethod]
        [ExpectedException(typeof(FightFleet.Exceptions.UserNameExistsException))]
        public void TestCreateDuplicateUsername()
        {
            var user = new User { UserName = "test2", Password = "test" };
            new UserManager().SaveUser(user);
            //no need to do anything
        }

        [TestMethod]
        [ExpectedException(typeof(MissingFieldException))]
        public void TestCreateNoUsername()
        {
            var user = new User { Password = "test" };
            new UserManager().SaveUser(user);
            //no need to do anything
        }

        [TestMethod]
        [ExpectedException(typeof(MissingFieldException))]
        public void TestCreateNoPassword()
        {
            var user = new User { UserName = "test" + DateTime.Now };
            new UserManager().SaveUser(user);
            //no need to do anything
        }
    }
}
