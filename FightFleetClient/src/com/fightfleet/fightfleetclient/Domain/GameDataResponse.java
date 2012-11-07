package com.fightfleet.fightfleetclient.Domain;

import com.fightfleet.fightfleetclient.Lib.CellState;
import com.fightfleet.fightfleetclient.Lib.GameStatus;

public class GameDataResponse {
	Integer m_GameID;
	CellState[][] m_OpponentBoardData;
	CellState[][] m_UserBoardData;
	Integer m_UserId;
	Integer m_OpponentUserId;
	GameStatus m_GameStatus;
	Integer m_LastMoveBy;
	
	public GameDataResponse(Integer gameID, CellState[][] opponentBoardData, CellState[][] userBoardData,
								int opponentUserId, int userId, GameStatus status, Integer lastMoveBy){
		m_GameID=gameID;
		m_UserBoardData = userBoardData;
		m_OpponentBoardData = opponentBoardData;
		m_GameID =gameID;
		m_UserId = userId;
		m_OpponentUserId = opponentUserId;
		m_GameStatus= status;
		m_LastMoveBy = lastMoveBy;
	}
	
	public Integer getGameID(){
		return m_GameID;
	}
	
	public CellState[][] getUserBoardData(){
		return m_UserBoardData;
	}
	
	public CellState[][] getOpponentBoardData(){
		return m_OpponentBoardData;
	}
	
	public Integer getUserID(){
		return m_UserId;
	}
	
	public Integer getOpponentID(){
		return m_OpponentUserId;
	}
	
	public GameStatus getGameStatus(){
		return m_GameStatus;
	}
	
	public Integer getLastMoveBy(){
		return m_LastMoveBy;
	}
}
