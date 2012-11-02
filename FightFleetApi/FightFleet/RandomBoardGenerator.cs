using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace FightFleet
{
    /// <summary>
    /// Randomly places 5 ships to a game board.
    /// </summary>
    class RandomGeneratedBoard
    {
        private GameBoard Board { get; set; }

        public RandomGeneratedBoard()
        {
            Board = new GameBoard();

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
        public void RandomlyPlaceShip(Ship ship)
        {
            var random = new Random();

            while (true)
            {
                var randomPosition = new KeyValuePair<int, int>(random.Next(0, GameBoard.XSIZE - 1), random.Next(0, GameBoard.YSIZE - 1));

                if (Board.BoardCells[randomPosition.Key, randomPosition.Value] != CellStatus.Blank) 
                    continue;

                var directions = (int[])Enum.GetValues(typeof (ShipDirections));

                // Randomly sort the direction array to pick a random direction of the ship
                directions.OrderBy(x => random.Next());

                foreach (int direction in directions)
                {
                    var shipPositions = ShipCellPositions((ShipDirections) direction, randomPosition, ship.Size);

                    if (shipPositions != null)
                    {
                        MarkBoardWithShipPositions(shipPositions);
                        return;
                    }
                }
            }
        }

        private void MarkBoardWithShipPositions(IEnumerable<KeyValuePair<int, int>> shipPositions)
        {
            foreach (var shipPosition in shipPositions)
            {
                Board.BoardCells[shipPosition.Key, shipPosition.Value] = CellStatus.Ship;
            }
        }

        /// <summary>
        /// Returns locations where the ship can be places. It checks if the ship has enough space on the direction specified.
        /// </summary>
        /// <param name="direction"></param>
        /// <param name="ranPosition"></param>
        /// <param name="shipSize"></param>
        /// <returns></returns>
        public List<KeyValuePair<int, int>> ShipCellPositions(ShipDirections direction, KeyValuePair<int, int> ranPosition, int shipSize)
        {
            var markShipPositions = new List<KeyValuePair<int, int>>();

            int xPos = ranPosition.Key;
            int yPos =  ranPosition.Value;

            switch (direction)
            {
                case ShipDirections.Up:
                    if ((yPos - shipSize) < 0)
                        return null;

                    for (int i = 0; i < shipSize; i++)
                    {
                        if (Board.BoardCells[xPos, yPos - i] != CellStatus.Blank)
                        {
                            return null;
                        }
                        else
                        {
                            markShipPositions.Add(new KeyValuePair<int, int>(xPos, yPos - i));
                        }
                    }
                    break;
                case ShipDirections.Right:
                    if ((xPos + shipSize) >= GameBoard.XSIZE)
                        return null;

                    for (int i = 0; i < shipSize; i++)
                    {
                        if (Board.BoardCells[xPos + i, yPos] != CellStatus.Blank)
                        {
                            return null;
                        } else {
                            markShipPositions.Add(new KeyValuePair<int, int>(xPos + i, yPos));
                        }
                    }
                    break;
                case ShipDirections.Down:
                    if ((yPos + shipSize) >= GameBoard.YSIZE)
                        return null;

                    for (int i = 1; i < shipSize; i++) {
                        if (Board.BoardCells[xPos, yPos + i] != CellStatus.Blank) {
                            return null;
                        } else {
                            markShipPositions.Add(new KeyValuePair<int, int>(xPos, yPos + i));
                        }
                    }
                    break;
                case ShipDirections.Left:
                    if ((xPos - shipSize) < 0)
                        return null;

                    for (int i = 1; i < shipSize; i++) {
                        if (Board.BoardCells[xPos - i, yPos] != CellStatus.Blank) {
                            return null;
                        } else {
                            markShipPositions.Add(new KeyValuePair<int, int>(xPos - i, yPos));
                        }
                    }
                    break;
                default:
                    return null;
            }
            return markShipPositions;
        }
    }

}
