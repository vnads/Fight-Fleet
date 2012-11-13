package com.fightfleet.fightfleetclient.Test;

import java.util.ArrayList;

import com.fightfleet.fightfleetclient.Domain.CreateGameRequest;
import com.fightfleet.fightfleetclient.Domain.CreateUserRequest;
import com.fightfleet.fightfleetclient.Domain.GameDataRequest;
import com.fightfleet.fightfleetclient.Domain.GameDataResponse;
import com.fightfleet.fightfleetclient.Domain.GameListRequest;
import com.fightfleet.fightfleetclient.Domain.GameListResponse;
import com.fightfleet.fightfleetclient.Domain.LoginRequest;
import com.fightfleet.fightfleetclient.Domain.LoginResponse;
import com.fightfleet.fightfleetclient.Domain.MoveRequest;
import com.fightfleet.fightfleetclient.Domain.MoveResponse;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.GameInformation;
import com.fightfleet.fightfleetclient.Lib.GameStatus;
import com.fightfleet.fightfleetclient.Lib.MoveResult;

public class TestServiceInterface implements ServiceInterface {
	
	public LoginResponse requestCreateUser(CreateUserRequest request) {		
		return new LoginResponse(TestStructures.PlayerUserData.getUserName(), TestStructures.PlayerUserData.getUserID(),
									TestStructures.PlayerUserData.getUUID());
	}

	public GameDataResponse requestGameData(GameDataRequest request) {
		ArrayList<GameInformation> gameInfo = TestStructures.GameList;
		GameInformation gi;
		if (request.getGameID() == 1){
			gi = gameInfo.get(0);
		}
		else gi = gameInfo.get(1);
															
		GameDataResponse response = new GameDataResponse(gi.GetGameID(), TestStructures.TestBoard, TestStructures.TestBoard2, 
											gi.GetOpponentUserID(),request.getUserID(), gi.GetGameStatus(), gi.GetLastMoveBy());							
		return response;
	}

	public LoginResponse requestLogin(LoginRequest request) {
		return new LoginResponse(TestStructures.PlayerUserData.getUserName(), TestStructures.PlayerUserData.getUserID(), 
										TestStructures.PlayerUserData.getUUID());
	}
	
	public GameListResponse requestGameList(GameListRequest request) {
		ArrayList<GameInformation> gameInfo = TestStructures.GameList;
		GameListResponse response = new GameListResponse(gameInfo);
		return response;
	}

	public MoveResponse requestMove(MoveRequest request) {
		Integer xCord = request.getXCord();
		Integer yCord = request.getYCord();
		
		MoveResponse response;
		if (xCord == 1 && yCord ==1){
			MoveResult result = MoveResult.Hit;
			response = new MoveResponse(GameStatus.InProgress, result, xCord, yCord);
		}
		else if (xCord ==2 && yCord == 2){
			response = new MoveResponse(GameStatus.Finished , MoveResult.Hit, xCord, yCord);
		}
		else{
			response = new MoveResponse(GameStatus.InProgress,  MoveResult.Miss,  xCord, yCord);
		}
		return response;
	}

	public GameDataResponse requestCreateGame(CreateGameRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
}
