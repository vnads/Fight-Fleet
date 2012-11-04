package com.fightfleet.fightfleetclient.Domain;

import java.util.UUID;

public class LoginResponse {
	String m_UserName;
	Integer m_UserID;
	UUID m_UUID;
	
	public LoginResponse(String userName, Integer userID, UUID uuid){
		m_UserName=userName;
		m_UserID = userID;
		m_UUID = uuid;
	}
	
	public UUID getUUID(){
		return m_UUID;
	}
	
	public Integer getUserID(){
		return m_UserID;
	}
	
	public String getUserName(){
		return m_UserName;
	}
}
