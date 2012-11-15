package com.fightfleet.fightfleetclient.Domain;

import java.util.HashMap;
import java.util.UUID;

import com.fightfleet.fightfleetclient.Interface.Request;

public class MoveRequest implements Request {
	Integer m_xCord;
	Integer m_yCord;
	Integer m_UserId;
	Integer m_GameId;
	UUID m_UUID;
	String m_EndPoint;
	
	public MoveRequest(Integer xCord, Integer yCord, Integer userId, Integer gameId, UUID uuid, String endPoint){
		m_xCord = xCord;
		m_yCord = yCord;
		m_UserId = userId;
		m_GameId = gameId;
		m_UUID = uuid;
		m_EndPoint = endPoint;
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

	public String getEndPoint() {
		return m_EndPoint;
	}

	public HashMap<String, Object> getParameters() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userid", m_UserId);
		map.put("gameid", m_GameId);
		map.put("x", m_xCord);
		map.put("y", m_yCord);
		map.put("AccessToken", m_UUID);
		return map;
	}
}
