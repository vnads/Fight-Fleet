package com.fightfleet.fightfleetclient.Domain;

public class CreateUserRequest {
	String m_UserName;
	String m_Password;
	
	public CreateUserRequest(String userName, String password){
		m_UserName= userName;
		m_Password = password;
	}
	
	public String GetUserName(){
		return m_UserName;
	}
	public String GetPassword(){
		return m_Password;
	}
}
