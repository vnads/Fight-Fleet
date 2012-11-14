package com.fightfleet.fightfleetclient.Domain;

import java.util.HashMap;
import com.fightfleet.fightfleetclient.Interface.Request;

public class LoginRequest implements Request {
	String m_UserName;
	String m_Password;
	String m_EndPoint;
	
	public LoginRequest(String userName, String password, String endPoint){
		m_UserName= userName;
		m_Password = password;
		m_EndPoint = endPoint;
	}
	
	public String getUserName(){
		return m_UserName;
	}
	public String getPassword(){
		return m_Password;
	}

	public String getEndPoint() {
	    return m_EndPoint;
	}

	public HashMap<String, Object> getParameters() {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("username", m_UserName);
		map.put("password", m_Password);
		return map;
	}
}
