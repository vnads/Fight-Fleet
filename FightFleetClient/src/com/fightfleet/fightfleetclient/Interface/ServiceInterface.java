package com.fightfleet.fightfleetclient.Interface;

import com.fightfleet.fightfleetclient.Domain.*;

public interface ServiceInterface {
	LoginResponse requestCreateUser(CreateUserRequest request);
	GameDataResponse requestGameData(GameDataRequest request);
	LoginResponse requestLogin(LoginRequest request); 
	GameListResponse requestGameList(GameListRequest request);
	MoveResponse requestMove(MoveRequest request);
	GameDataResponse requestCreateGame(CreateGameRequest request);
}
