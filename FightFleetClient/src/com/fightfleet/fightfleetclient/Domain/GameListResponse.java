package com.fightfleet.fightfleetclient.Domain;
import java.util.ArrayList;

import com.fightfleet.fightfleetclient.Lib.*;

public class GameListResponse {
	
	ArrayList<GameInformation> m_GameInformation = new ArrayList<GameInformation>();
	
	public GameListResponse(ArrayList<GameInformation> gameInformation){
	   	m_GameInformation = gameInformation;
	}
	
	public ArrayList<GameInformation> getGameInformation(){
		return m_GameInformation;
	}
}
