using System;
using System.Collections.Generic;
using System.Linq;

namespace FightFleet
{
    internal abstract class GenerateBoard
    {
        protected GameBoard Board { get; set; }

        protected GenerateBoard()
        {
            Board = new GameBoard();
        }

        public abstract void Generate();

        protected void MarkBoardWithShipPositions(IEnumerable<KeyValuePair<int, int>> shipPositions)
        {
            foreach (var shipPosition in shipPositions)
            {
                Board.BoardCells[shipPosition.Key, shipPosition.Value] = (int)BoardCellStatus.Ship;
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
                        if (Board.BoardCells[xPos, yPos - i] != (int)BoardCellStatus.Blank)
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
                        if (Board.BoardCells[xPos + i, yPos] != (int)BoardCellStatus.Blank)
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
                        if (Board.BoardCells[xPos, yPos + i] != (int)BoardCellStatus.Blank) {
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
                        if (Board.BoardCells[xPos - i, yPos] != (int)BoardCellStatus.Blank) {
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