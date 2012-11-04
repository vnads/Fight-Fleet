package com.fightfleet.fightfleetclient.Test;

import java.util.ArrayList;
import java.util.UUID;

import com.fightfleet.fightfleetclient.Domain.CreateUserRequest;
import com.fightfleet.fightfleetclient.Domain.GameDataRequest;
import com.fightfleet.fightfleetclient.Domain.GameDataResponse;
import com.fightfleet.fightfleetclient.Domain.GameListRequest;
import com.fightfleet.fightfleetclient.Domain.GameListResponse;
import com.fightfleet.fightfleetclient.Domain.LoginRequest;
import com.fightfleet.fightfleetclient.Domain.LoginResponse;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.CellState;
import com.fightfleet.fightfleetclient.Lib.GameInformation;
import com.fightfleet.fightfleetclient.Lib.GameStatus;

public class TestServiceInterface implements ServiceInterface {

	Integer m_UserID = 1;
	UUID m_UUID = new UUID(0,  Long.MAX_VALUE);
	
	public LoginResponse requestCreateUser(CreateUserRequest request) {		
		return new LoginResponse(request.getUserName(), 1, m_UUID);
	}

	public GameDataResponse requestGameData(GameDataRequest request) {
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
		
		GameDataResponse response = new GameDataResponse(request.getGameID(), boardInfo);
		return response;
	}

	public LoginResponse requestLogin(LoginRequest request) {
		return new LoginResponse(request.getUserName(), m_UserID, m_UUID);
	}
	
	/**
	 * Good spot for adding test games. Add a new GameInformation object into the gameInfo arraylist 
	 * to add it to the list of games we see in the UI to create different scenarios that the 
	 * UI has to handle.
	 */
	public GameListResponse requestGameList(GameListRequest request) {
		ArrayList<GameInformation> gameInfo = new ArrayList<GameInformation>();
		GameInformation game = new GameInformation(1, 2, "Frank Castle", "11/1/2012", GameStatus.InProgress, "11/1/2012", 2);
		gameInfo.add(game);
		
		GameListResponse response = new GameListResponse(gameInfo);
		return response;
	}
}
