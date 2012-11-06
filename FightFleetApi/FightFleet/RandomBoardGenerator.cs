using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet
{
    /// <summary>
    /// Randomly places 5 ships to a game board.
    /// </summary>
    class RandomlyGenerateBoard : GenerateBoard
    {
        public override void Generate() {
            // Place 5 ships on the board
            RandomlyPlaceShip(new AircraftCarrier());
            RandomlyPlaceShip(new BattleShip());
            RandomlyPlaceShip(new Submarine());
            RandomlyPlaceShip(new Cruiser());
            RandomlyPlaceShip(new Destroyer());
        }

        /// <summary>
        /// Randomly places a ship in the board. Random location as well as direction (horizontal, vertical) of the ship
        /// </summary>
        /// <param name="ship"></param>
        public void RandomlyPlaceShip(Ship ship) {
            var random = new Random();

            while (true) {
                var randomPosition = new KeyValuePair<int, int>(random.Next(0, GameBoard.XSIZE),
                                                                random.Next(0, GameBoard.YSIZE));

                if (Board.BoardCells[randomPosition.Key, randomPosition.Value] != (int)BoardCellStatus.Blank)
                    continue;

                var directions = (int[])Enum.GetValues(typeof(ShipDirections));

                // Randomly sort the direction array to pick a random direction of the ship
                directions.OrderBy(x => random.Next());

                // tries all possible directions until it find an appropriate one. 
                foreach (int direction in directions) {
                    var shipPositions = ShipCellPositions((ShipDirections)direction, randomPosition, ship.Size);

                    if (shipPositions != null) {
                        MarkBoardWithShipPositions(shipPositions);
                        return;
                    }
                }

                // it it getts to this point, it means the ship has not been placed on the board. 
                // So we try with a different random location
                RandomlyPlaceShip(ship);
            }
        }
    }
}
