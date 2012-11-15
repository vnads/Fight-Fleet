package com.fightfleet.fightfleetclient.Lib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    
    public static HashMap<String, Object>[] getComplexCollection(String JSON){
    	try{
    		return new ObjectMapper().readValue(JSON, new TypeReference<HashMap<String, Object>[]>() {});
    	}
    	catch (Exception ex){
    		System.out.println("Broken");
    		//TODO: Add Logger
    	}
    	return null;    	    	
    }
    
    public static Integer[] getIntegerCollection(String JSON){
        ObjectMapper mapper = new ObjectMapper();

        Integer[] collection = null;
       	try{
       		collection = mapper.readValue(JSON, new TypeReference<Integer[]>() {});
    	}
    	catch (Exception ex ){
    		System.out.println("Broken");
    		//TODO: Add Logger
    	}
       	return collection;
    }
}

    