package com.fightfleet.fightfleetclient.Domain;

import java.util.HashMap;
import java.util.UUID;

import com.fightfleet.fightfleetclient.Interface.Request;

public class GameListRequest implements Request {
	Integer m_UserID;
	UUID m_UUID;
	String m_EndPoint;
	
	public GameListRequest(Integer userID, UUID uuid, String endPoint){
		m_UserID = userID;
		m_UUID = uuid;
		m_EndPoint = endPoint;
	}
	
	public UUID getUUID(){
		return m_UUID;
	}
	
	public Integer getUserID(){
		return m_UserID;
	}

	public String getEndPoint() {
		return m_EndPoint;
	}

	public HashMap<String, Object> getParameters() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userid", m_UserID);
		map.put("AccessToken", m_UUID);
		return map;
	}
}
