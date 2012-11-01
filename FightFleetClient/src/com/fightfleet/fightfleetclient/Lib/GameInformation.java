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
    
    public String OpponentUserName(){
    	return m_OpponentUserName;
    }
    
    public String CreatedOn(){
    	return m_CreatedOn;
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
}
