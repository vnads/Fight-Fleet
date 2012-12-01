package com.fightfleet.fightfleetclient.GameBoard;

import android.view.View;

public interface GameBoardListener {
	boolean onGameBoardClick(final View view, int x, int y);
}
