package com.fightfleet.fightfleetclient.Domain;

import com.fightfleet.fightfleetclient.Lib.CellState;

public class GameDataResponse {
	Integer m_GameID;
	CellState[][] m_BoardData;
	
	public GameDataResponse(Integer gameID, CellState[][] boardData){
		m_GameID=gameID;
		m_BoardData = boardData;
	}
	
	public Integer getGameID(){
		return m_GameID;
	}
	
	public CellState[][] getBoardData(){
		return m_BoardData;
	}
}
