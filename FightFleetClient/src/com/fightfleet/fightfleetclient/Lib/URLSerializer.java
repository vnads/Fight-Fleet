package com.fightfleet.fightfleetclient.Lib;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * @author shamidi
 * Converts requests to URL's.
 */
public class URLSerializer {
	static String m_BaseURL = "http://fightfleetapi.apphb.com";
	public static String createURL(String endPoint, HashMap<String, Object> parameters){
		StringBuilder sb = new StringBuilder();
		sb.append(m_BaseURL);
		sb.append(endPoint);

	    Iterator it = parameters.entrySet().iterator();
	    sb.append("?");
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        sb.append(pairs.getKey() + "=" + pairs.getValue() + "&");
	    }
	    sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
}
