package com.fightfleet.fightfleetclient.Domain;

import java.util.UUID;

public class CreateGameRequest {
	Integer m_UserId;
	UUID m_UUID;
	
	public CreateGameRequest(Integer userID, UUID uuid){
		m_UserId = userID;
		m_UUID = uuid;
	}
	
	public Integer getUserID(){
		return m_UserId;
	}
	
	public UUID getUUID(){
		return m_UUID;
	}
}
