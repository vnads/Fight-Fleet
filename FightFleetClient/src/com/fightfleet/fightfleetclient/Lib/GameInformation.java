package com.fightfleet.fightfleetclient.Lib;

import java.util.Date;
import java.text.SimpleDateFormat;

public class GameInformation {
    Integer m_GameID;
    Integer m_OpponentUserID;
    String m_OpponentUserName;
    Date m_CreatedOn;
    GameStatus m_GameStatus;
    Date m_LastMoveOn;
    Integer m_LastMoveBy;
    
    public GameInformation(Integer gameID, Integer opponentUserID, String opponentUserName, String createdOn,
    						GameStatus gameStatus, String lastMoveOn, Integer lastMoveBy){
    	m_GameID = gameID;
    	m_OpponentUserID = opponentUserID;
    	m_OpponentUserName = opponentUserName;
    	m_CreatedOn = ParseDate(createdOn);
    	m_GameStatus = gameStatus;
    	m_LastMoveOn = ParseDate(lastMoveOn);
    	m_LastMoveBy=lastMoveBy;
    }
    
    public Integer GetGameID(){
    	return m_GameID;
    }
    public Integer GetOpponentUserID(){
    	return m_OpponentUserID;
    }
    public String GetOpponentUserName(){
    	return m_OpponentUserName;
    }
    public Date GetCreatedOn(){
    	return m_CreatedOn;
    }
    public GameStatus GetGameStatus(){
    	return m_GameStatus;
    }
    public Date GetLastMoveOn(){
    	return m_LastMoveOn;
    }
    public Integer GetLastMoveBy(){
    	return m_LastMoveBy;
    }
    		
    Date ParseDate(String dateAsString){
    	try
    	{
    		SimpleDateFormat sdf = new SimpleDateFormat();
    		return sdf.parse(dateAsString);
    	}
    	catch (Exception ex){
    		return new Date();
    	}
    }
    
    @Override
    public String toString(){
    	if (m_LastMoveBy == m_OpponentUserID){
    		return m_OpponentUserName + "::: Your Turn!";
    	}
    	else{
    		return m_OpponentUserName + "::: Their Turn";
    	}
    }
}
