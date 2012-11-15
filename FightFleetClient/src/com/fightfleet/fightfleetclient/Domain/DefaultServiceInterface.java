package com.fightfleet.fightfleetclient.Domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.UUID;

import com.fightfleet.fightfleetclient.Interface.Request;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.CellState;
import com.fightfleet.fightfleetclient.Lib.GameStatus;
import com.fightfleet.fightfleetclient.Lib.JSONDeserializer;
import com.fightfleet.fightfleetclient.Lib.URLSerializer;

public class DefaultServiceInterface implements ServiceInterface {

	public LoginResponse requestCreateUser(CreateUserRequest request) {
	    String result = sendGet(request);
        HashMap<String, Object> data = JSONDeserializer.getData(result);
        
        Object userNameKey = new String("UserName");
        Object userIDKey = new String("UserId");
        Object uuidKey = new String("AccessToken");
        
        
	}

	public GameDataResponse requestGameData(GameDataRequest request) {
	    String result = sendGet(request);
        HashMap<String, Object> data = JSONDeserializer.getData(result);
        
        Object gameIdKey = new String("GameId");
        Object gameStatusKey = new String("GameStatus");
        Object userIdKey = new String("UserId");
        Object opponentUserIdKey = new String("OpponentUserId");
        Object userBoardDataKey = new String("UserBoardData");
        Object opponentBoardDataKey = new String("OpponentBoardData");
        Object currentPlayerIdKey = new String("CurrentPlayerId");
        
        Integer gameId = (Integer)data.get(gameIdKey);
        GameStatus gameStatus = (GameStatus)data.get(gameStatusKey);
        Integer userId = (Integer)data.get(userIdKey);
        Integer opponentUserId = (Integer)data.get(opponentUserIdKey);
        Integer currentPlayerId = (Integer)data.get(currentPlayerIdKey);
        
        //TODO: need to convert the game data from int[] to CellState[][]
	}

	public LoginResponse requestLogin(LoginRequest request) {
	 
	    try{
	
        String result = sendGet(request);
        HashMap<String, Object> data = JSONDeserializer.getData(result);
        
        Object userNameKey = new String("UserName");
        Object userIDKey = new String("UserId");
        Object uuidKey = new String("AccessToken");
        
        String userName = data.get(userNameKey).toString();
        Integer userID = (Integer)data.get(userIDKey);
        UUID accessToken = UUID.fromString(data.get(uuidKey).toString());
        
        LoginResponse response = new LoginResponse(userName, userID,accessToken);
        return response;
	    }
	    catch (Exception ex){
	    	System.out.println("Broken");
	    	//TODO: add logger
	    }
	    return null;
	}

	public GameListResponse requestGameList(GameListRequest request) {
		
        String result = sendGet(request);
        HashMap<String, Object> data = JSONDeserializer.getData(result);
        
        Object userNameKey = new String("UserName");
        Object userIDKey = new String("UserId");
        Object uuidKey = new String("AccessToken");
        
	}

	public MoveResponse requestMove(MoveRequest request) {
	    String result = sendGet(request);
        HashMap<String, Object> data = JSONDeserializer.getData(result);
        
        Object userNameKey = new String("UserName");
        Object userIDKey = new String("UserId");
        Object uuidKey = new String("AccessToken");	}

	public GameDataResponse requestCreateGame(CreateGameRequest request) {
	    String result = sendGet(request);
        HashMap<String, Object> data = JSONDeserializer.getData(result);
        
        Object userNameKey = new String("UserName");
        Object userIDKey = new String("UserId");
        Object uuidKey = new String("AccessToken");
	}
	
	String sendGet(Request request){
		StringBuilder sb=new StringBuilder();
		try{
		    String urlAsString = URLSerializer.createURL(request.getEndPoint(), request.getParameters());
			
		    URL url = new URL(urlAsString);
	        URLConnection yc = url.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(
	                                    yc.getInputStream()));
	        String inputLine;
	        while ((inputLine = in.readLine()) != null) 
	            sb.append(inputLine);
	        in.close();
		}
		catch (Exception ex){
			System.out.println("Broken in the get send");
		}
		return sb.toString();
	}
	
	CellState[][] ConvertBoard(int[] rawData){
		CellState[][] board = new CellState[10][10];
		int x=0;
		int y=0;
		
		for (int i=0; i < rawData.length; i++){
			if (x==10){
				x = 0;
				y= y+1;
			}
			CellState state;
			switch (rawData[i]){
			case 0:
				board[x][y]= CellState.Empty;
				break;
			case 1:
				board[x][y] = CellState.Ship;
				break;
			case 2:
				board[x][y] = CellState.DamagedShip;
				break;				
			case 3:
				board[x][y] = CellState.Miss;
				break;
			}
		}
		 
		return board;
	}
	
}
