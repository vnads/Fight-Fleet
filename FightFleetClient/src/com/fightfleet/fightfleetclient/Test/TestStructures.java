package com.fightfleet.fightfleetclient.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.fightfleet.fightfleetclient.Lib.CellState;
import com.fightfleet.fightfleetclient.Lib.GameInformation;
import com.fightfleet.fightfleetclient.Lib.GameStatus;
import com.fightfleet.fightfleetclient.Lib.UserData;

public class TestStructures {

	private TestStructures(){}
	
	public static CellState[][] TestBoard = generateTestBoard();
	public static CellState[][] TestBoard2 = generateTestBoard2();
	public static UserData PlayerUserData = new UserData("frank.castle@gmail.com", "test", 1, getUUID());
	public static UserData OpponentUserData1 = new UserData("judge.dread@gmail.com", "test", 2, getUUID());
	public static UserData OpponentUserData2 = new UserData("max.payne@gmail.com", "test", 3, getUUID());
	public static ArrayList<GameInformation> GameList = generateGameList();
	
	private static ArrayList<GameInformation> generateGameList(){
		ArrayList<GameInformation> gameInfo= new ArrayList<GameInformation>();
		
		GameInformation gi1 = new GameInformation(1, OpponentUserData1.getUserID(), OpponentUserData1.getUserName(),
												"11/7/2012" , GameStatus.InProgress, "11/5/2012", PlayerUserData.getUserID());
		GameInformation gi2 = new GameInformation(1, OpponentUserData2.getUserID(), OpponentUserData2.getUserName(),
				"11/9/2012" , GameStatus.InProgress, "11/8/2012", OpponentUserData2.getUserID());

		gameInfo.add(gi1);
		gameInfo.add(gi2);
		return gameInfo;
	}
	
	private static CellState[][] generateTestBoard2(){
		CellState[][] boardInfo = new CellState[10][10];
		//set all the cells to empty
		for (int i =0;i<10;i++){
			for (int j = 0;j<10;j++){
				boardInfo[i][j] = CellState.Empty;
			}
		}
		
		//Create a test game for the UI to render.
		boardInfo[0][0] = CellState.Ship;
		boardInfo[1][0] = CellState.Ship;
		boardInfo[2][0] = CellState.Ship;
		
		boardInfo[5][5] = CellState.Ship;
		boardInfo[5][6] = CellState.Ship;
		boardInfo[5][7] = CellState.DamagedShip;
		boardInfo[5][8] = CellState.Ship;
		
		boardInfo[9][2] = CellState.Miss;
		return boardInfo;
	}
	
	
	private static CellState[][] generateTestBoard(){
		CellState[][] boardInfo = new CellState[10][10];
		//set all the cells to empty
		for (int i =0;i<10;i++){
			for (int j = 0;j<10;j++){
				boardInfo[i][j] = CellState.Empty;
			}
		}
		
		//Create a test game for the UI to render.
		boardInfo[0][0] = CellState.Ship;
		boardInfo[0][1] = CellState.Ship;
		boardInfo[0][2] = CellState.Ship;
		
		boardInfo[5][5] = CellState.Ship;
		boardInfo[6][5] = CellState.Ship;
		boardInfo[7][5] = CellState.DamagedShip;
		boardInfo[8][5] = CellState.Ship;
		
		boardInfo[2][9] = CellState.Miss;
		return boardInfo;
	}
	
	private static UUID getUUID(){
		UUID uuid = new UUID(Long.MAX_VALUE, Long.MIN_VALUE);
		return uuid;
	}
}
