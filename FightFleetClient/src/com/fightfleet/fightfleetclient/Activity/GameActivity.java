package com.fightfleet.fightfleetclient.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fightfleet.fightfleetclient.R;
import com.fightfleet.fightfleetclient.Domain.DefaultServiceInterface;
import com.fightfleet.fightfleetclient.Domain.GameDataRequest;
import com.fightfleet.fightfleetclient.Domain.GameDataResponse;
import com.fightfleet.fightfleetclient.Domain.MoveRequest;
import com.fightfleet.fightfleetclient.Domain.MoveResponse;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.CellState;
import com.fightfleet.fightfleetclient.Lib.GameStatus;
import com.fightfleet.fightfleetclient.Lib.MoveResult;
import com.fightfleet.fightfleetclient.Lib.UserData;

public class GameActivity extends Activity {
	UserData m_UserData;
	ServiceInterface m_ServiceInterface;
	Integer m_GameID;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        //Get the userdata from the intent
        Intent intent = getIntent();
        if (intent!=null){
        	m_UserData = intent.getParcelableExtra("UserData");	
        	m_GameID = intent.getIntExtra("GameID", 1);
        }
        m_ServiceInterface = new DefaultServiceInterface();
        buildBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }
    
    @Override
    public void onBackPressed() {
 		Intent intent = new Intent(this, GameListActivity.class);
		intent.putExtra("UserData", m_UserData);	
		startActivity(intent);
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
    
    
    public void onFireButtonClick(View view){
    	//Get the values of the text boxes.
    	EditText txtXCord = (EditText) this.findViewById(R.id.editTextXCoordinate);
    	EditText txtYCord = (EditText)this.findViewById(R.id.editTextYCoordinate);
    	
    	try{
	    	Integer xCord = Integer.parseInt(txtXCord.getText().toString());
	    	Integer yCord = Integer.parseInt(txtYCord.getText().toString());
	    	
	    	if (xCord < 0 || xCord > 9){	    		
	    		displayError("X must be between 0 and 9");
	    		return;
	    	}
	    	if (yCord< 0 || yCord> 9){	    		
	    		displayError("Y must be between 0 and 9");
	    		return;
	    	}
	    	
	    	MoveRequest request = new MoveRequest(xCord, yCord, m_UserData.getUserID(), m_GameID,
	    									m_UserData.getUUID(), getText(R.string.makeMoveEndPoint).toString() );
	    	SendMoveTask task = new SendMoveTask();
	    	task.execute(request);
    	}
    	catch (Exception ex){    		
    		//TODO: Add logging
    		displayError("Enter X&Y Values");
    	}
    }
    
    void displayError(String message){
        View v= findViewById(R.id.textViewStatus);
        TextView txtVw = (TextView)v;
        txtVw.setText(message);
    }
    
    /**
     * An overloaded method.
     * This method updates the status label with whatever the game status is. 
     * There is another method that updates the status label with a MoveResult.
     * @param status The Game Status to update
     * @param lastMoveBy LastMoveBy is used to determine whether or not to enable the fire button.
     */
    void adjustGameStatus(GameStatus status, int lastMoveBy){
        View v= findViewById(R.id.textViewStatus);
        TextView txtVw = (TextView)v;
        String label = new String(); //stores the text to update the status label with
        Button b;
    	switch (status){
    	case Finished:
    		label = "Game Over!";
    		b = (Button)findViewById(R.id.btnFire);
			b.setEnabled(false);
    		break;
    	case InProgress:
    		if (lastMoveBy == m_UserData.getUserID()){
    			label = "Awaiting Opponent Move";
    			b = (Button)findViewById(R.id.btnFire);
    			b.setEnabled(false);
    		}
    		else{
    			label = "Awaiting Your Move!";
    			b = (Button)findViewById(R.id.btnFire);
    			b.setEnabled(true);
    		}
    		break;
    	case Pending:
    		label = "Waiting for Opponent.";
    		b = (Button)findViewById(R.id.btnFire);
    		b.setEnabled(false);
    		break;
    	}    	
    	txtVw.setText(label);
    }
    
    /**
     * Overloaded method.
     * This method edits the game status label after a Move has been made.
     * @param r
     * @param xCord
     * @param yCord
     */
    void adjustGameStatus(MoveResult r, int xCord, int yCord, GameStatus gameStatus){
        View v= findViewById(R.id.textViewStatus);
        TextView txtVw = (TextView)v;
        String label = new String(); //stores the text to update the status label with
        
        //If the game is over, set the label to game over.
        if (gameStatus == GameStatus.Finished){
        	label = "Game Over!";
        }
        else{//otherwise, draw the game result.
		    switch (r){
		    case Hit:
		    	label = "Hit!";
		    	break;
		    case Miss:
		    	label = "Miss!";
		    	break;
		    }
        }
        txtVw.setText(label);
    }   
    
    void buildBoard(){
       DrawBoardTask task = new	DrawBoardTask();
       task.execute(m_UserData);
    }
    
    String generateBoardString(CellState[][] board, Boolean isUserBoard){
    	StringBuilder sb = new StringBuilder();
    	for (int i =0; i < board.length; i++){
    		for (int j = 0; j< board[i].length;j++){
    			switch (board[i][j]){
    			case DamagedShip:
    				sb.append("H");
    				break;
    			case Empty:
    				sb.append("_");
    				break;
    			case Ship:
    				if (isUserBoard)
    					sb.append("X");
    				else sb.append("_");
    				break;
    			case Miss:
    				sb.append("M");
    			}
    			if (j == board[i].length-1){
    				sb.append("\n");
    			}
    			else sb.append(" ");
    		}
    	}
    	
    	return sb.toString();
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
	           	 String userBoard = generateBoardString(result.getUserBoardData(), true);
	        	 String opponentBoard = generateBoardString(result.getOpponentBoardData(), false);
	        	 
	     	     View v= findViewById(R.id.textViewUserWater);
	             TextView txtVw = (TextView)v;
	             txtVw.setText(userBoard);
	            
	    	     v= findViewById(R.id.textViewOpponentWater);
	             txtVw = (TextView)v;
	             txtVw.setText(opponentBoard);
	             
	             
	             adjustGameStatus(result.getGameStatus(), result.getLastMoveBy());
        	}
        	catch (Exception ex){
        		System.out.println("Broken");
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
	        		 int xCord = result.getXCord();
	        		 int yCord = result.getYCord();
	        		 
	        		 MoveResult moveResult = result.getMoveResult();
	        		 GameStatus gameStatus =  result.getGameStatus();
	        		     		        	 
	        		 adjustGameStatus(moveResult, xCord, yCord, gameStatus);
	        	}
	        	catch (Exception ex){
	        		System.out.println("Broken");
	        	}
	    	}
	 }
}
