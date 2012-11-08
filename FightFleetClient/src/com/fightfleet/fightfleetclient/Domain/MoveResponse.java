package com.fightfleet.fightfleetclient.Domain;

import com.fightfleet.fightfleetclient.Lib.GameStatus;
import com.fightfleet.fightfleetclient.Lib.MoveResult;

public class MoveResponse {
	MoveResult m_Result;
	GameStatus m_Status;
	
	public MoveResponse(GameStatus status, MoveResult result){
		m_Result = result;
		m_Status= status;
	}
	
	public MoveResult getMoveResult(){
		return m_Result;
	}
	
	public GameStatus getGameStatus(){
		return m_Status;
	}
}

