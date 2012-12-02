package com.fightfleet.fightfleetclient.Lib;

import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;

import android.os.Parcel;
import android.os.Parcelable;

public class GameInformation implements Parcelable {
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
    	m_LastMoveBy = lastMoveBy;
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
    		return "["+m_GameID+"] "+m_OpponentUserName + "::: Your Turn!";
    	}
    	else{
    		return "["+m_GameID+"] "+m_OpponentUserName + "::: Their Turn";
    	}
    }

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(m_GameID);
		dest.writeInt(m_OpponentUserID);
		dest.writeString(m_OpponentUserName);
		dest.writeSerializable(m_CreatedOn);
		dest.writeSerializable(m_GameStatus);
		dest.writeSerializable(m_LastMoveBy);
	}
	
    public static final Parcelable.Creator<GameInformation> CREATOR = new Parcelable.Creator<GameInformation>() {
        public GameInformation createFromParcel(Parcel in) {
            return new GameInformation(in);
        }

        public GameInformation[] newArray(int size) {
            return new GameInformation[size];
        }
    };

    private GameInformation(Parcel in) {
		m_GameID = in.readInt();
		m_OpponentUserID = in.readInt();
		m_OpponentUserName = in.readString();
		m_CreatedOn = (Date) in.readSerializable();
		m_GameStatus = (GameStatus) in.readSerializable();
		m_LastMoveBy = (Integer) in.readSerializable();
    }
}