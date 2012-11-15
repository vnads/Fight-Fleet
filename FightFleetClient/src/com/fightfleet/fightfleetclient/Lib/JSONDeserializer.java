package com.fightfleet.fightfleetclient.Lib;

import java.util.HashMap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JSONDeserializer {
    public static HashMap<String, Object> getData(String JSON){
    	try{
    		return new ObjectMapper().readValue(JSON, new TypeReference<HashMap<String, Object>>() {});
    	}
    	catch (Exception ex){
    		System.out.println("Broken");
    		//TODO: Add Logger
    	}
    	return null;
    }
    
    public static HashMap<String, Object>[] getCollection(String JSON){
    	try{
    		return new ObjectMapper().readValue(JSON, new TypeReference<HashMap<String, Object>[]>() {});
    	}
    	catch (Exception ex){
    		System.out.println("Broken");
    		//TODO: Add Logger
    	}
    	return null;    	    	
    }
}

    