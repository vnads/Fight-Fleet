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

/*
 * GameBoardView - 2D render engine for game board
 * 
 * @author Lars Lindgren <chrono@eeky.net>
 */
public class GameBoardView extends View implements OnTouchListener {

	final Paint paint = new Paint();

	private GameBoardListener gameListener = null;

	private List<GameBoardTile> tiles = null;

	private CellState[][] boardPlayer = null;
	private CellState[][] boardOpponent = null;
	
	private CellState[][] oldBoardPlayer = null;
	private CellState[][] oldBoardOpponent = null;
	
	private int screenW = 0;
	private int screenH = 0;

	private int tileW = 0;
	private int tileH = 0;
	private int tileBorder = 1;

	private int border = 30;
	private int fontH = 18;
	
	private Rect rTopStrip = null;
	private Rect rMidStrip = null;
	private Rect rBottomStrip = null;
	
	private Rect rOpponent = null;
	private Rect rPlayer = null;
	
	private String nameOpponent = "OPPONENT";
	private String namePlayer = "PLAYER";
	private String statusMessage = "";

	public GameBoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		tiles = new ArrayList<GameBoardTile>();
		setOnTouchListener(this);
		paint.setAntiAlias(true);
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

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		int left, top, right, bottom = 0;

		super.onSizeChanged(w, h, oldw, oldh);
		screenW = w;
		screenH = h;
		
		// portrait
		if (h > w) {	
			tileW = (int) Math.floor((screenW - 2 * border) / 10);
			tileH = (int) Math.floor((screenH / 2 - 1.5 * border) / 10);
			
			// regions for the three strips showing text
			rTopStrip = new Rect(0, 0, screenW, border);	
			rMidStrip = new Rect(0, (int) (screenH / 2 - 0.5 * border), screenW, (int) (screenH / 2 + 0.5 * border));
			rBottomStrip = new Rect(0, (int) (screenH - border), screenW, screenH);

			// regions / dimensions for opponent and player game boards
			left = border;
			right = left + (tileW * 10);

			top = (int) ((screenH / 2) - (0.5 * border) - (tileH * 10));	
			bottom = top + (tileH * 10);
			rOpponent = new Rect(left, top, right, bottom);

			top = (int) ((screenH / 2) + (0.5 * border));
			bottom = top + (tileH * 10);
			rPlayer = new Rect(left, top, right, bottom);
		} else {
		// landscape			
			tileW = (int) Math.floor((screenW / 2 - 1.5 * border) / 10);
			tileH = (int) Math.floor((screenH - 2 * border) / 10);
			
			rTopStrip = new Rect(0, 0, screenW/2, border);
			rMidStrip = new Rect(screenW/2, 0, screenW, border);
			rBottomStrip = new Rect(0, (int) (screenH - border), screenW, screenH);
			
			left = border;
			top = border;
			bottom = top + (tileH * 10);
			
			right = left + (tileW * 10) - (border / 2);
			rOpponent = new Rect(left, top, right, bottom);

			left = tileW * 10 + 2 * border;
			right = left + tileW * 10;
			rPlayer = new Rect(left, top, right, bottom);
		}
		
		// text size
		paint.setTextSize(fontH);
	}

	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		
		paint.setColor(Color.rgb(125,52,67));
		canvas.drawRect(rBottomStrip, paint);

		// text color
		paint.setColor(Color.WHITE);

		Point p = getTextCenterToDraw(nameOpponent, rTopStrip, paint);
		canvas.drawText(nameOpponent, p.x, p.y, paint);

		p = getTextCenterToDraw(namePlayer, rMidStrip, paint);
		canvas.drawText(namePlayer, p.x, p.y, paint);

		p = getTextCenterToDraw(statusMessage, rBottomStrip, paint);
		canvas.drawText(statusMessage, p.x, p.y, paint);

		if (boardPlayer != null && boardOpponent != null) {
			tiles.clear();
			drawBoard(boardPlayer, oldBoardPlayer, canvas, rPlayer, true);
			drawBoard(boardOpponent, oldBoardOpponent, canvas, rOpponent, false);
		}
	}

	public void drawBoard(CellState[][] board, CellState[][] boardOld, Canvas canvas, Rect dimensions, boolean isPlayerBoard) {
		final Random rn = new Random();
		int x1, y1, x2, y2 = 0;
		int border = 0;
		
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {

				x1 = dimensions.left + x * tileW;
				y1 = dimensions.top + y * tileH;
				x2 = x1 + tileW;
				y2 = y1 + tileH;

				Rect rWithBorder = new Rect(x1, y1, x2, y2);
				
				// highlight latest move
				if (boardOld != null && board[y][x] != boardOld[y][x]) {
					paint.setColor(Color.YELLOW);
					border = 3 * tileBorder;
				} else {
					paint.setColor(Color.rgb(64, 64, 192));
					border = tileBorder;
				}
				
				canvas.drawRect(rWithBorder, paint);

				Rect rWithoutBorder = new Rect(x1 + border, y1 + border, x2 - border, y2 - border);
				
				// use some cool random blue colors for water
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

	public static Point getTextCenterToDraw(String text, Rect region, Paint paint) {
		Rect textBounds = new Rect();
		paint.getTextBounds(text, 0, text.length(), textBounds);
		float x = region.centerX() - textBounds.width() * 0.5f;
		float y = region.centerY() + textBounds.height() * 0.4f;
		return new Point((int) x, (int) y);
	}
	
	public void setGameBoardListener(GameBoardListener listener) {
		this.gameListener = listener;
	}

	public CellState[][] getBoardPlayer() {
		return this.boardPlayer;
	}
	
	public void setBoardPlayer(CellState[][] board) {
		this.boardPlayer = board;
	}
	
	public CellState[][] getBoardOpponent() {
		return this.boardOpponent;
	}
	
	public void setBoardOpponent(CellState[][] board) {
		this.boardOpponent = board;
	}
	
	public void setOldBoardPlayer(CellState[][] board) {
		this.oldBoardPlayer = board;
	}

	public CellState[][] getOldBoardPlayer() {
		return this.oldBoardPlayer;
	}
	
	public CellState[][] getOldBoardOpponent() {
		return this.oldBoardOpponent;
	}
	
	
	public void setOldBoardOpponent(CellState[][] board) {
		this.oldBoardOpponent = board;
	}
		
	public void setStatusMessage(String message) {
		this.statusMessage = message;
	}
	
	public void setNamePlayer(String name) {
		this.namePlayer = name;
	}
	
	public void setNameOpponent(String name) {
		this.nameOpponent = name;
	}
}