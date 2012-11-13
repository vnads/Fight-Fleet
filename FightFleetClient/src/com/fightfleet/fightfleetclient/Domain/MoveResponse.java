package com.fightfleet.fightfleetclient.Domain;

import com.fightfleet.fightfleetclient.Lib.GameStatus;
import com.fightfleet.fightfleetclient.Lib.MoveResult;

public class MoveResponse {
	MoveResult m_Result;
	GameStatus m_Status;
	Integer m_XCord;
	Integer m_YCord;
	
	public MoveResponse(GameStatus status, MoveResult result, Integer xCord, Integer yCord){
		m_Result = result;
		m_Status= status;
		m_XCord = xCord;
		m_YCord = yCord;
	}
	
	public Integer getXCord(){
		return m_XCord;
	}
	
	public Integer getYCord(){
		return m_YCord;
	}
	
	public MoveResult getMoveResult(){
		return m_Result;
	}
	
	public GameStatus getGameStatus(){
		return m_Status;
	}
}

