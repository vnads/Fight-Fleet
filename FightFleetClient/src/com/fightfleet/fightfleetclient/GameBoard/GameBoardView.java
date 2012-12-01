package com.fightfleet.fightfleetclient.GameBoard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.fightfleet.fightfleetclient.Lib.CellState;

public class GameBoardView extends View implements OnTouchListener {

	final Paint paint = new Paint();

	private GameBoardListener gameListener = null;

	private List<GameBoardTile> tiles = null;

	private CellState[][] boardPlayer = null;
	private CellState[][] boardOpponent = null;

	private String statusMessage = "";

	private int screenW = 0;
	private int screenH = 0;

	private int tileW = 0;
	private int tileH = 0;

	private int statusH = 30;

	private int border = 1;

	private int left, top, right, bottom = 0;

	public GameBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);

		tiles = new ArrayList<GameBoardTile>();

		setOnTouchListener(this);
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;

		tileW = (int) Math.floor((screenW - 2 * statusH) / 10);
		tileH = (int) Math.floor((screenH / 2 - 1.5 * statusH) / 10);
	}

	public static Point getTextCenterToDraw(String text, Rect region, Paint paint) {
		Rect textBounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), textBounds);
		float x = region.centerX() - textBounds.width() * 0.5f;
		float y = region.centerY() + textBounds.height() * 0.5f;
		return new Point((int) x, (int) y);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);

		paint.setTextSize(24);
		paint.setColor(Color.WHITE);
		// canvas.drawLine(0, screenH/2, screenW, screenH/2, paint);

		left = 0;
		top = 0;
		right = left + screenW;
		bottom = top + statusH;
		Rect rTop = new Rect(left, top, right, bottom);
		Point p = getTextCenterToDraw("OPPONENT", rTop, paint);
		canvas.drawText("OPPONENT", p.x, p.y, paint);

		top = (int) (screenH / 2 - 0.5 * statusH);
		bottom = (int) (screenH / 2 + 0.5 * statusH);
		Rect rMid = new Rect(left, top, right, bottom);
		p = getTextCenterToDraw("PLAYER", rMid, paint);
		canvas.drawText("PLAYER", p.x, p.y, paint);

		top = (int) (screenH - statusH);
		bottom = screenH;
		Rect rBot = new Rect(left, top, right, bottom);
		p = getTextCenterToDraw(statusMessage, rBot, paint);
		canvas.drawText(statusMessage, p.x, p.y, paint);

		left = statusH;
		top = (int) ((screenH / 2) - (0.5 * statusH) - (tileH * 10));
		right = left + (tileW * 10);
		bottom = top + (tileH * 10);
		Rect rOpponent = new Rect(left, top, right, bottom);

		left = statusH;
		top = (int) ((screenH / 2) + (0.5 * statusH));
		right = left + (tileW * 10);
		bottom = top + (tileH * 10);
		Rect rPlayer = new Rect(left, top, right, bottom);

		if (boardPlayer != null && boardOpponent != null) {
			tiles.clear();
			drawBoard(boardPlayer, canvas, rPlayer, true);
			drawBoard(boardOpponent, canvas, rOpponent, false);
		}
	}

	public void drawBoard(CellState[][] board, Canvas canvas, Rect dimensions,
			boolean isPlayerBoard) {
		Random rn = new Random();

		int x1, y1, x2, y2 = 0;

		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {

				x1 = dimensions.left + x * tileW;
				y1 = dimensions.top + y * tileH;
				x2 = x1 + tileW;
				y2 = y1 + tileH;

				Rect rWithBorder = new Rect(x1, y1, x2, y2);
				paint.setColor(Color.rgb(64, 64, 192));
				canvas.drawRect(rWithBorder, paint);

				Rect rWithoutBorder = new Rect(x1 + border, y1 + border, x2 - border, y2 - border);
				// if (isPlayerBoard) paint.setColor(Color.rgb(255-25*y,
				// 255-25*y, 255));
				// else paint.setColor(Color.rgb(25*y, 25*y, 255));
				paint.setColor(Color.rgb(rn.nextInt(64), rn.nextInt(64) + 32, 192));

				switch (board[y][x]) {
				case DamagedShip:
					paint.setColor(Color.GREEN);
					break;
				case Empty:
					if (!isPlayerBoard)	tiles.add(new GameBoardTile(x, y, rWithBorder));
					break;
				case Ship:
					if (!isPlayerBoard) tiles.add(new GameBoardTile(x, y, rWithBorder));
					if (isPlayerBoard) paint.setColor(Color.GRAY);
					break;
				case Miss:
					paint.setColor(Color.RED);
					break;
				}

				canvas.drawRect(rWithoutBorder, paint);			
			}
		}
	}

	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			for (GameBoardTile tile : tiles) {
				if (tile.rect.contains(x, y)) {
					if (gameListener != null)
						gameListener.onGameBoardClick(this, tile.x, tile.y);
					return false;
				}
			}
		}

		return false;
	}

	public void setGameBoardListener(GameBoardListener listener) {
		this.gameListener = listener;
	}

	public void setBoardPlayer(CellState[][] board) {
		this.boardPlayer = board;
	}

	public void setBoardOpponent(CellState[][] board) {
		this.boardOpponent = board;
	}

	public void setStatusMessage(String message) {
		this.statusMessage = message;
	}
}