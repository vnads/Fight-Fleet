package com.fightfleet.fightfleetclient.GameBoard;

import android.graphics.Rect;

public class GameBoardTile {
	public int x = 0;
	public int y = 0;
	public Rect rect = null;

	public GameBoardTile(int x, int y, Rect rect) {
		this.x = x;
		this.y = y;
		this.rect = rect;
	}
}
