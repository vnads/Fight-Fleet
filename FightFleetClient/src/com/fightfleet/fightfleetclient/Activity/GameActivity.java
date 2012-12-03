package com.fightfleet.fightfleetclient.Activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fightfleet.fightfleetclient.R;
import com.fightfleet.fightfleetclient.Domain.DefaultServiceInterface;
import com.fightfleet.fightfleetclient.Domain.GameDataRequest;
import com.fightfleet.fightfleetclient.Domain.GameDataResponse;
import com.fightfleet.fightfleetclient.Domain.GameListRequest;
import com.fightfleet.fightfleetclient.Domain.GameListResponse;
import com.fightfleet.fightfleetclient.Domain.MoveRequest;
import com.fightfleet.fightfleetclient.Domain.MoveResponse;
import com.fightfleet.fightfleetclient.GameBoard.GameBoardListener;
import com.fightfleet.fightfleetclient.GameBoard.GameBoardView;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.CellState;
import com.fightfleet.fightfleetclient.Lib.GameInformation;
import com.fightfleet.fightfleetclient.Lib.GameStatus;
import com.fightfleet.fightfleetclient.Lib.MoveResult;
import com.fightfleet.fightfleetclient.Lib.UserData;

public class GameActivity extends Activity implements GameBoardListener {
	private UserData m_UserData = null;
	private ServiceInterface m_ServiceInterface = null;
	private Integer m_GameID = null;
	private GameInformation m_GameInformation = null;
	
	// Interval for polling server
	private int m_interval = 30000;
	private Handler m_handler;
	
	private boolean playersTurn = false;
	private GameBoardView viewGameBoard = null;
	private int xCord, yCord = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_game_board);
               
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Get UserData and GameInfo from the intent
        Intent intent = getIntent();
        if (intent!=null){
        	m_UserData = intent.getParcelableExtra("UserData");
        	m_GameID = intent.getIntExtra("GameID", 1);
        }
                
        m_ServiceInterface = new DefaultServiceInterface();

        // Initialize our 2d game board renderer
        viewGameBoard = (GameBoardView) findViewById(R.id.viewGameBoard);
        viewGameBoard.setGameBoardListener(this);       
        viewGameBoard.setNamePlayer(m_UserData.getUserName());
 
        // Populate m_GameInformation with info about current game.
        // This way we can get name of opponent etc...
        GameInformationTask task = new GameInformationTask();
		task.execute(m_UserData);
		
		// Used for polling the server
		m_handler = new Handler();
 		startPollingServer();
 	   
        updateBoard();
    }

    @Override
    public void onStop() {
    	super.onStop();
    	stopPollingServer();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }
    
    @Override
    public void onBackPressed() {
    	stopPollingServer();
    	finish();
 		//Intent intent = new Intent(this, GameListActivity.class);
		//intent.putExtra("UserData", m_UserData);	
		//startActivity(intent);
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
         		Intent intent = new Intent(this, GameListActivity.class);
        		intent.putExtra("UserData", m_UserData);	
        		startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    /**
     * An overloaded method.
     * This method updates the status label with whatever the game status is. 
     * There is another method that updates the status label with a MoveResult.
     * @param status The Game Status to update
     * @param lastMoveBy LastMoveBy is used to determine whether or not to enable the fire button.
     */
    void adjustGameStatus(GameStatus status, int lastMoveBy){
       
    	playersTurn = false;
    	
    	switch (status){
    	case Finished:
    		viewGameBoard.setStatusMessage("Game Over!");
    		break;
    	case InProgress:
    		if (lastMoveBy == m_UserData.getUserID()){
    			if (m_GameInformation == null)
    				viewGameBoard.setStatusMessage("Awaiting Opponents's Move!");
    			else
    				viewGameBoard.setStatusMessage("Awaiting "+m_GameInformation.GetOpponentUserName()+"'s Move!");
    		}
    		else{
    			viewGameBoard.setStatusMessage("Awaiting Your Move!");
    			playersTurn = true;
    		}
    		break;
    	case Pending:
    		viewGameBoard.setStatusMessage("Waiting for Opponent...");
    		break;
    	}
    	
    	viewGameBoard.invalidate();
    }
    
    /**
     * Overloaded method.
     * This method edits the game status label after a Move has been made.
     * @param r
     * @param xCord
     * @param yCord
     */
    /*
    void adjustGameStatus(MoveResult r, int xCord, int yCord, GameStatus gameStatus){
        
        //If the game is over, set the label to game over.
        if (gameStatus == GameStatus.Finished){
        	viewGameBoard.setStatusMessage("GAME OVER!");
        }
        else{//otherwise, draw the game result.
		    switch (r){
		    case Hit:
		    	viewGameBoard.setStatusMessage("HIT!");
		    	break;
		    case Miss:
		    	viewGameBoard.setStatusMessage("MISS!");
		    	break;
		    }
        }        
        viewGameBoard.invalidate();
    }   
    */
    
    void updateBoard() {
       final DrawBoardTask task = new DrawBoardTask();
       task.execute(m_UserData);
    }
    
    private class GameInformationTask extends AsyncTask<UserData, Void, ArrayList<GameInformation>> {
    	@Override
    	protected ArrayList<GameInformation> doInBackground(UserData... params) {
    		try {
    			if (params.length >0){
    				UserData d = params[0];
    				GameListRequest rq = new GameListRequest(d.getUserID(), d.getUUID(), getText(com.fightfleet.fightfleetclient.R.string.getGameListEndPoint).toString());
    				GameListResponse response = m_ServiceInterface.requestGameList(rq);
    				return response.getGameInformation();
    			}
    			else return new ArrayList<GameInformation>();
    		} catch (Exception e) {
    			return new ArrayList<GameInformation>();
    		}
    	}

    	@Override
    	protected void onPostExecute(ArrayList<GameInformation> result){
    		try	{
    			for (GameInformation g : result) {
    				if (g.GetGameID() == m_GameID)
    					m_GameInformation = g;
    			} 			
    			viewGameBoard.setNameOpponent(m_GameInformation.GetOpponentUserName());
    		}
    		catch (Exception ex){
    			System.out.print(ex.getMessage());
    		}
    	}
    }
    
    private class DrawBoardTask extends AsyncTask<UserData, Void, GameDataResponse> {
    	@Override
    	protected GameDataResponse doInBackground(UserData... params) {
			try {		
				UserData d = params[0];
				GameDataRequest request = new GameDataRequest(d.getUserID(), m_GameID, d.getUUID(), getText(R.string.getGameEndPoint).toString());
		        GameDataResponse response =  m_ServiceInterface.requestGameData(request);
		    	return response;
			} catch (Exception e) {
				return new GameDataResponse(-1, null, null, -1, -1, GameStatus.Finished, -1);
			}
    	}
    	
    	@Override
    	protected void onPostExecute(GameDataResponse result){
        	try	{  		
        		/*
        		 * Update game board
        		 */

        		// player
        		if (viewGameBoard.getBoardPlayer() == null)
					viewGameBoard.setBoardPlayer(result.getUserBoardData());

				if (!generateCellStateString(viewGameBoard.getBoardPlayer())
					.equals(generateCellStateString(result.getUserBoardData()))) {
					viewGameBoard.setBoardPlayerOld(viewGameBoard.getBoardPlayer());			
					viewGameBoard.setBoardPlayer(result.getUserBoardData());
				}

				// opponent
				if (viewGameBoard.getBoardOpponent() == null)
					viewGameBoard.setBoardOpponent(result.getOpponentBoardData());
					
				if (!generateCellStateString(viewGameBoard.getBoardOpponent())
						.equals(generateCellStateString(result.getOpponentBoardData()))) {
						viewGameBoard.setBoardOpponentOld(viewGameBoard.getBoardOpponent());			
						viewGameBoard.setBoardOpponent(result.getOpponentBoardData());
					}
				
        		adjustGameStatus(result.getGameStatus(), result.getLastMoveBy());
        	}
        	catch (Exception ex){
        		System.out.println("DrawBoardTask - Broken");     		
        		System.out.println(StackTraceToString(ex));
        	}
    	}
     }
    
	 private class SendMoveTask extends AsyncTask<MoveRequest, Void, MoveResponse> {
	    	@Override
	    	protected MoveResponse doInBackground(MoveRequest... params) {
				try {		
					MoveRequest request = params[0];
		
			        MoveResponse response = m_ServiceInterface.requestMove(request);
			    	return response;
				} catch (Exception e) {
					return new MoveResponse(GameStatus.Finished, MoveResult.Miss, 0, 0);
				}
	    	}
	    	
	    	@Override
	    	protected void onPostExecute(MoveResponse result){
	        	try	{
	        		updateBoard();
	        	}
	        	catch (Exception ex){
	        		System.out.println("SendMoveTask - Broken");
	        	}
	    	}
	 }

	public boolean onGameBoardClick(final View view, int x, int y) {
		System.out.println("onGameBoardClick: x: "+x+", y: "+y);		

		xCord = x;
		yCord = y;
		
		if (!playersTurn) return false;
		
		DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int which) {
		        switch (which){
		        case DialogInterface.BUTTON_POSITIVE:
		        	try {
		    	    	MoveRequest request = new MoveRequest(yCord, xCord, m_UserData.getUserID(), m_GameID, m_UserData.getUUID(),
		    	    			                              getText(R.string.makeMoveEndPoint).toString() );
		    	    	SendMoveTask task = new SendMoveTask();
		    	    	task.execute(request);
		        	}
		        	catch (Exception ex){    		
		        		//TODO: Add logging
		        	}
		        	break;
		        case DialogInterface.BUTTON_NEGATIVE:
		        	break;
		        }
		    }
		};

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Target Locked!").setMessage("Coordinates (x, y): "+x+", "+y+"")
		.setNegativeButton("Cancel", dialogClickListener).setPositiveButton("Fire!", dialogClickListener).show();

		return true;
	}
	
	// poll server
	Runnable m_statusChecker = new Runnable() {
	     public void run() {
	          updateBoard();
	          m_handler.postDelayed(m_statusChecker, m_interval);
	     }
	};
	
	void startPollingServer() {
	    m_statusChecker.run(); 
	}

	void stopPollingServer() {
	    m_handler.removeCallbacks(m_statusChecker);
	}
	
	String generateCellStateString(CellState[][] board) {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < board.length; i++) {
    		for (int j = 0; j < board[i].length; j++) {
    			switch (board[i][j]) {
    			case DamagedShip:
    				sb.append("H");
    				break;
    			case Empty:
    				sb.append("_");
    				break;
    			case Ship:
    				sb.append("X");
    				break;
    			case Miss:
    				sb.append("M");
    			}
    			if (j == board[i].length - 1) {
    				sb.append("\n");
    			} else
    				sb.append(" ");
    		}
    	}

    	return sb.toString();
    }
	
	// For debugging purposes
    public static String StackTraceToString(Exception ex) {
    	String result = ex.toString() + "\n";
    	StackTraceElement[] trace = ex.getStackTrace();
    	for (int i = 0; i < trace.length; i++) {
    		result += trace[i].toString() + "\n";
    	}
    	return result;
    }
}
