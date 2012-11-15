package com.fightfleet.fightfleetclient.Domain;

import java.util.HashMap;
import java.util.UUID;

import com.fightfleet.fightfleetclient.Interface.Request;

public class GameDataRequest implements Request {
	Integer m_UserID;
	UUID m_UUID;
	Integer m_GameID;
	String m_EndPoint;
	
	public GameDataRequest(Integer userID, Integer gameID, UUID uuid, String endPoint){
		m_UserID = userID;
		m_GameID = gameID;
		m_UUID = uuid;
		m_EndPoint = endPoint;
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

	public String getEndPoint() {
		return m_EndPoint;
	}

	public HashMap<String, Object> getParameters() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("gameid", m_GameID);
		map.put("userId", m_UserID);
		map.put("AccessToken", m_UUID);
		return map;
	}
}
