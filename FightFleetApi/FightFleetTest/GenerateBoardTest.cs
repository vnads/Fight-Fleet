//using FightFleet;
//using Microsoft.VisualStudio.TestTools.UnitTesting;
//using System;
//using System.Collections.Generic;

//namespace FightFleetTest
//{
    
    
//    /// <summary>
//    ///This is a test class for GenerateBoardTest and is intended
//    ///to contain all GenerateBoardTest Unit Tests
//    ///</summary>
//    [TestClass()]
//    public class GenerateBoardTest
//    {


//        private TestContext testContextInstance;

//        /// <summary>
//        ///Gets or sets the test context which provides
//        ///information about and functionality for the current test run.
//        ///</summary>
//        public TestContext TestContext {
//            get {
//                return testContextInstance;
//            }
//            set {
//                testContextInstance = value;
//            }
//        }

//        #region Additional test attributes
//        // 
//        //You can use the following additional attributes as you write your tests:
//        //
//        //Use ClassInitialize to run code before running the first test in the class
//        //[ClassInitialize()]
//        //public static void MyClassInitialize(TestContext testContext)
//        //{
//        //}
//        //
//        //Use ClassCleanup to run code after all tests in a class have run
//        //[ClassCleanup()]
//        //public static void MyClassCleanup()
//        //{
//        //}
//        //
//        //Use TestInitialize to run code before running each test
//        //[TestInitialize()]
//        //public void MyTestInitialize()
//        //{
//        //}
//        //
//        //Use TestCleanup to run code after each test has run
//        //[TestCleanup()]
//        //public void MyTestCleanup()
//        //{
//        //}
//        //
//        #endregion


//        internal virtual GenerateBoard_Accessor CreateGenerateBoard_Accessor() {
//            // TODO: Instantiate an appropriate concrete class.
//            GenerateBoard_Accessor target = null;
//            return target;
//        }

//        /// <summary>
//        ///A test for MarkBoardWithShipPositions
//        ///</summary>
//        [TestMethod()]
//        [DeploymentItem("FightFleet.dll")]
//        public void MarkBoardWithShipPositionsTest() {
//            PrivateObject param0 = null; // TODO: Initialize to an appropriate value
//            GenerateBoard_Accessor target = new GenerateBoard_Accessor(param0); // TODO: Initialize to an appropriate value
//            IEnumerable<KeyValuePair<int, int>> shipPositions = null; // TODO: Initialize to an appropriate value
//            target.MarkBoardWithShipPositions(shipPositions);
//            Assert.Inconclusive("A method that does not return a value cannot be verified.");
//        }

//        internal virtual GenerateBoard CreateGenerateBoard() {
//            // TODO: Instantiate an appropriate concrete class.
//            GenerateBoard target = null;
//            return target;
//        }

//        /// <summary>
//        ///A test for ShipCellPositions
//        ///</summary>
//        [TestMethod()]
//        public void ShipCellPositionsTest() {
//            GenerateBoard target = CreateGenerateBoard(); // TODO: Initialize to an appropriate value
//            ShipDirections direction = new ShipDirections(); // TODO: Initialize to an appropriate value
//            KeyValuePair<int, int> ranPosition = new KeyValuePair<int, int>(); // TODO: Initialize to an appropriate value
//            int shipSize = 0; // TODO: Initialize to an appropriate value
//            List<KeyValuePair<int, int>> expected = null; // TODO: Initialize to an appropriate value
//            List<KeyValuePair<int, int>> actual;
//            actual = target.ShipCellPositions(direction, ranPosition, shipSize);
//            Assert.AreEqual(expected, actual);
//            Assert.Inconclusive("Verify the correctness of this test method.");
//        }
//    }
//}
