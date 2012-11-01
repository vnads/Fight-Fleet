package com.fightfleet.fightfleetclient.Lib;

public enum GameStatus {
	Pending(1),
    InProgress(2),
    Finished(3);
	
    private int value;

    private GameStatus(int value) {
            this.value = value;
    }
}
