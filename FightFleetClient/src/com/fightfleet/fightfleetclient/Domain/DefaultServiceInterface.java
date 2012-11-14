package com.fightfleet.fightfleetclient.Domain;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.UUID;

import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.JSONDeserializer;
import com.fightfleet.fightfleetclient.Lib.URLSerializer;

public class DefaultServiceInterface implements ServiceInterface {

	public LoginResponse requestCreateUser(CreateUserRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public GameDataResponse requestGameData(GameDataRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public LoginResponse requestLogin(LoginRequest request) {
	    String url = URLSerializer.createURL(request.getEndPoint(), request.getParameters());
	    try{
		StringBuilder sb = new StringBuilder();
	    URL createUser = new URL(url);
        URLConnection yc = createUser.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(
                                    yc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) 
            sb.append(inputLine);
        in.close();
        
        HashMap<String, Object> data = JSONDeserializer.getData(sb.toString());
        
        Object userNameKey = new String("username");
        Object userIDKey = new String("userID");
        Object uuidKey = new String("accestoken");
        
        String userName = data.get(userNameKey).toString();
        Integer userID = (Integer)data.get(userIDKey);
        UUID accessToken = (UUID)data.get(uuidKey);
        
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
		// TODO Auto-generated method stub
		return null;
	}

	public MoveResponse requestMove(MoveRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	public GameDataResponse requestCreateGame(CreateGameRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
