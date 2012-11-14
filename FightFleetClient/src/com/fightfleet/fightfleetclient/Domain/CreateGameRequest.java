package com.fightfleet.fightfleetclient.Domain;

import java.util.HashMap;
import java.util.UUID;

import com.fightfleet.fightfleetclient.Interface.Request;

public class CreateGameRequest implements Request {
	Integer m_UserId;
	UUID m_UUID;
	String m_EndPoint;
	
	public CreateGameRequest(Integer userID, UUID uuid, String endPoint){
		m_UserId = userID;
		m_UUID = uuid;
		m_EndPoint = endPoint;
	}
	
	public Integer getUserID(){
		return m_UserId;
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
		map.put("AccessToken", m_UUID);
		return map;
	}
}
