package com.fightfleet.fightfleetclient.Domain;

import java.util.UUID;

public class GameDataRequest {
	Integer m_UserID;
	UUID m_UUID;
	Integer m_GameID;
	
	public GameDataRequest(Integer userID, Integer gameID, UUID uuid){
		m_UserID = userID;
		m_GameID = gameID;
		m_UUID = uuid;
	}
	
	public Integer getGameID(){
		return m_GameID;
	}
	
	public Integer getUserID(){
		return m_UserID;
	}
	
	public UUID getUUID(){
		return m_UUID;
	}
}
