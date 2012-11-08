package com.fightfleet.fightfleetclient.Domain;

import java.util.UUID;

public class MoveRequest {
	Integer m_xCord;
	Integer m_yCord;
	Integer m_UserId;
	Integer m_GameId;
	UUID m_UUID;
	
	public MoveRequest(Integer xCord, Integer yCord, Integer userId, Integer gameId, UUID uuid){
		m_xCord = xCord;
		m_yCord = yCord;
		m_UserId = userId;
		m_GameId = gameId;
		m_UUID = uuid;
	}
	
	public Integer getXCord(){
		return m_xCord;
	}
	public Integer getYCord(){
		return m_yCord;
	}
	public Integer getUserID(){
		return m_UserId;
	}
	public Integer getGameID(){
		return m_GameId;
	}
	public UUID getUUID(){
		return m_UUID;
	}
}
