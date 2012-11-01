package com.fightfleet.fightfleetclient.Interface;

import com.fightfleet.fightfleetclient.Domain.*;

public interface ServiceInterface {
	CreateUserRequest RequestCreateUser(CreateUserRequest request);
	GameDataResponse RequestGameData(GameDataRequest request);
	LoginResponse RequestLogin(LoginRequest request); 
}
