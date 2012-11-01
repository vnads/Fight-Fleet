using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using FightFleet.Models;

namespace FightFleet.Managers
{
    public class BoardManager
    {
        private const int BOARD_SIDE_LENGTH = 10;

        //private const Dictionary<int, int> SHIP_SIZES_AND_QUANTITIES = new Dictionary<int, int>
        //{
        //    {2, 1},
        //    {3, 2},
        //    {4, 1},
        //    {5, 1}
        //};
        
        public int[,] CreateRandomBoard()
        {
            var board = new int[10,10];

            var shipSizesAndQuantities = new Dictionary<int, int>
            {
                {2, 1},
                {3, 2},
                {4, 1},
                {5, 1}
            };

            foreach (var ship in shipSizesAndQuantities)
            {
                var quantity = ship.Value;
                var size = ship.Key;
                while (quantity > 0)
                {
                    var rand = new Random();

                    var isValidPlacement = true;
                    do
                    {
                        var vert = rand.Next(2);
                        isValidPlacement = true;
                        var start = rand.Next(100);
                        var x = start % 10;
                        var y = start / 10;
                        if (vert == 1) //this will be a vertical placement
                        {
                            if (BOARD_SIDE_LENGTH - y < size)
                                y = BOARD_SIDE_LENGTH - size - 1;

                            int index = size - 1;
                            while (index >= 0)
                            {
                                if (board[x, y + index] != 0)
                                {
                                    isValidPlacement = false;
                                    break;
                                }
                                index --;
                            }
                            if (isValidPlacement)
                            {
                                index = size - 1;
                                while (index >= 0)
                                {
                                    board[x, y + index] = size;
                                    index--;
                                }
                            }
                        }
                        else //this is a horizontal placement
                        {
                            if (BOARD_SIDE_LENGTH - x < size)
                                x = BOARD_SIDE_LENGTH - size - 1;

                            int index = size - 1;
                            while (index >= 0)
                            {
                                if (board[x + index, y] != 0)
                                {
                                    isValidPlacement = false;
                                    break;
                                }
                                index--;
                            }
                            if (isValidPlacement)
                            {
                                index = size - 1;
                                while (index >= 0)
                                {
                                    board[x + index, y] = size;
                                    index--;
                                }
                            }
                        }
                    }
                    while (!isValidPlacement);
                    quantity--;
                }
            }

            //generate randomized xml

            //save to database


            return board ;
        }        
    }
}
