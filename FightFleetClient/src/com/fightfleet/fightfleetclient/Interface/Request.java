package com.fightfleet.fightfleetclient.Interface;

import java.util.HashMap;

public interface Request {
	String getEndPoint();
	HashMap<String, Object> getParameters();
}
