package com.fightfleet.fightfleetclient.Lib;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONDeserializer {
    public static HashMap<String, Object> getData(String JSON){
    	HashMap<String, Object> data = new HashMap<String, Object>();
    	try{
    		data =  new ObjectMapper().readValue(JSON, HashMap.class);
    	}
    	catch (Exception ex){
    		System.out.println("Broken");
    		//TODO: Add Logger
    	}
    	return data;
    }
}

    