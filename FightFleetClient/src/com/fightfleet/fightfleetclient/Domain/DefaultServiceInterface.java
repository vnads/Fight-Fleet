package com.fightfleet.fightfleetclient.Domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.fightfleet.fightfleetclient.Interface.Request;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.CellState;
import com.fightfleet.fightfleetclient.Lib.GameInformation;
import com.fightfleet.fightfleetclient.Lib.GameStatus;
import com.fightfleet.fightfleetclient.Lib.JSONDeserializer;
import com.fightfleet.fightfleetclient.Lib.MoveResult;
import com.fightfleet.fightfleetclient.Lib.URLSerializer;

public class DefaultServiceInterface implements ServiceInterface {

	public LoginResponse requestCreateUser(CreateUserRequest request) {
	    String result = sendGet(request);
        HashMap<String, Object> data = JSONDeserializer.getData(result);
        
        Object userNameKey = new String("UserName");
        Object userIDKey = new String("UserId");
        Object uuidKey = new String("AccessToken");
        
        String userName = data.get(userNameKey).toString();
        Integer userID = (Integer)data.get(userIDKey);
        UUID uuid = UUID.fromString(data.get(uuidKey).toString());
        
        LoginResponse response = new LoginResponse(userName, userID, uuid);
        return response;
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
        //Object currentPlayerIdKey = new String("CurrentPlayerId");
        Object lastMoveByKey = new String("LastMoveBy");
        
        Integer gameId = (Integer)data.get(gameIdKey);
        GameStatus status = convertGameStatus(data.get(gameStatusKey).toString());
        Integer userId = (Integer)data.get(userIdKey);
        Integer opponentUserId = (Integer)data.get(opponentUserIdKey);
        //Integer currentPlayerId = (Integer)data.get(currentPlayerIdKey);
        Integer lastMoveBy = (Integer)data.get(lastMoveByKey);
                
        Integer[] rawDataUserBoard = JSONDeserializer.getIntegerCollection(data.get(userBoardDataKey).toString());
        Integer[] rawDataOpponentBoard = JSONDeserializer.getIntegerCollection(data.get(opponentBoardDataKey).toString());
        
        CellState[][] opponentBoardData = convertBoard(rawDataOpponentBoard);
        CellState[][] userBoardData = convertBoard(rawDataUserBoard);
        
        GameDataResponse response = new GameDataResponse(gameId, opponentBoardData, userBoardData, opponentUserId, userId, status, lastMoveBy);
        return response;
    }

	public LoginResponse requestLogin(LoginRequest request) {	 	
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

	public GameListResponse requestGameList(GameListRequest request) {
		
        String result = sendGet(request);
        HashMap<String, Object>[] data = JSONDeserializer.getComplexCollection(result);
               
        Object gameIdKey = new String("GameId");
        Object opponentUserIdKey = new String("OpponentUserId");
        Object opponentUserNameKey = new String("OpponentUserName");
        Object createdOnKey = new String("CreatedOn");               
        Object gameStatusKey = new String("GameStatus");
        Object lastMoveOnKey = new String("LastMoveOn");        
        Object lastMoveByKey = new String("LastMoveBy");
                        
        ArrayList<GameInformation> gameInfo = new ArrayList<GameInformation>();
                        
        for (HashMap<String, Object> element : data ){
        	Integer gameId = (Integer)element.get(gameIdKey);
        	Integer opponentUserId = (Integer)element.get(opponentUserIdKey);
        	String opponentUserName = element.get(opponentUserNameKey) != null ?element.get(opponentUserNameKey).toString() : "Waiting For Opponent"; 
        	String createdOn = element.get(createdOnKey).toString();
        	String lastMoveOn = element.get(lastMoveOnKey).toString();
        	GameStatus status = convertGameStatus(element.get(gameStatusKey).toString());    	        	
        	Integer lastMoveBy = (Integer)element.get(lastMoveByKey);
        	        	        	       
        	GameInformation gi = new GameInformation(gameId, opponentUserId, opponentUserName, createdOn, status, lastMoveOn, lastMoveBy);
        	gameInfo.add(gi);
        }
        
        GameListResponse response = new GameListResponse(gameInfo);
        return response;
	}

	public MoveResponse requestMove(MoveRequest request) {
	    String result = sendGet(request);
        HashMap<String, Object> data = JSONDeserializer.getData(result);
                
        Object gameStatusKey = new String("GameStatus");
        Object moveResultKey = new String("MoveResult");
        Object xCordKey = new String("Xcoord");	
        Object yCordKey = new String("Ycoord");
        
        Integer xCord = (Integer)data.get(xCordKey);
        Integer yCord = (Integer)data.get(yCordKey);
        GameStatus status = convertGameStatus(data.get(gameStatusKey).toString());
        MoveResult moveResult = convertMoveResult(data.get(moveResultKey).toString());
        
        MoveResponse response = new MoveResponse(status, moveResult, xCord, yCord);
        return response;        
    }
	
	public GameDataResponse requestCreateGame(CreateGameRequest request) {
	    String result = sendGet(request);
        HashMap<String, Object> data = JSONDeserializer.getData(result);
                
        Object gameIdKey = new String("GameId");
        Object gameStatusKey = new String("GameStatus");
        Object userIdKey = new String("UserId");
        Object opponentUserIdKey = new String("OpponentUserId");
        Object userBoardDataKey = new String("UserBoardData");
        Object opponentBoardDataKey = new String("OpponentBoardData");        
        Object lastMoveByKey = new String("LastMoveBy");
        
        Integer gameId = (Integer)data.get(gameIdKey);
        GameStatus status = convertGameStatus(data.get(gameStatusKey).toString());
        Integer userId = (Integer)data.get(userIdKey);
        Integer opponentUserId = (Integer)data.get(opponentUserIdKey);      
        Integer lastMoveBy = (Integer)data.get(lastMoveByKey);
                              
        Integer[] rawDataUserBoard = JSONDeserializer.getIntegerCollection(data.get(userBoardDataKey).toString());
        Integer[] rawDataOpponentBoard = JSONDeserializer.getIntegerCollection(data.get(opponentBoardDataKey).toString());
        
        CellState[][] opponentBoardData = convertBoard(rawDataOpponentBoard);
        CellState[][] userBoardData = convertBoard(rawDataUserBoard);
        
        GameDataResponse response = new GameDataResponse(gameId, opponentBoardData, userBoardData, opponentUserId, userId, status, lastMoveBy);
        return response;
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
	
	CellState[][] convertBoard(Integer[] rawData){
		CellState[][] board = new CellState[10][10];
		int x=0;
		int y=0;
		
		if (rawData == null){
			for (int i=0; i < 100; i++){
				if (x==10){
					x = 0;
					y= y+1;
				}
				
			    board[x][y] = CellState.Empty;
			    x++;
			}
		}
		else{
			for (int i=0; i < rawData.length; i++){
				if (x==10){
					x = 0;
					y= y+1;
				}
				
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
				x++;
			}
		}
		return board;
	}
	
	GameStatus convertGameStatus(String str){
		GameStatus gs = GameStatus.Finished;
				
		if (str.equals("Pending"))
			gs = GameStatus.Pending;			
		else if (str.equals("InProgress"))
			gs = GameStatus.InProgress;		
		else if (str.equals("Finished"))
			gs = GameStatus.Finished;					
		return gs;
	}
	
	MoveResult convertMoveResult(String str){
		MoveResult result = MoveResult.Miss;
	     if (str.equals("Hit"))
	    	result = MoveResult.Hit;
	     else if (str.equals("Miss"))
	    	 result = MoveResult.Miss;		
	    return result;
	}
	
}
