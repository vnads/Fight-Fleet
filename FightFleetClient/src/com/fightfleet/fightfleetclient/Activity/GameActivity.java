package com.fightfleet.fightfleetclient.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fightfleet.fightfleetclient.R;
import com.fightfleet.fightfleetclient.Domain.GameDataRequest;
import com.fightfleet.fightfleetclient.Domain.GameDataResponse;
import com.fightfleet.fightfleetclient.Domain.LoginRequest;
import com.fightfleet.fightfleetclient.Domain.LoginResponse;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.CellState;
import com.fightfleet.fightfleetclient.Lib.UserData;
import com.fightfleet.fightfleetclient.Test.TestServiceInterface;

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
        m_ServiceInterface = new TestServiceInterface();
        BuildBoard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    public void onFireButtonClick(View view){
    
    }
    
    private void BuildBoard(){
       DrawBoardTask task = new	DrawBoardTask();
       task.execute(m_UserData);
    }
    
    private String GenerateBoardString(CellState[][] board, Boolean isUserBoard){
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
    				sb.append("X");
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
				GameDataRequest request = new GameDataRequest(d.getUserID(), m_GameID, d.getUUID());
		        GameDataResponse response =  m_ServiceInterface.requestGameData(request);
		    	return response;
			} catch (Exception e) {
				return new GameDataResponse(-1, null);
			}
    	}
    	
    	@Override
    	protected void onPostExecute(GameDataResponse result){
        	try	{
	           	 String userBoard = GenerateBoardString(result.getBoardData(), true);
	        	 String opponentBoard = GenerateBoardString(result.getBoardData(), false);
	        	 
	     	     View v= findViewById(R.id.textViewResult);
	             TextView txtVw = (TextView)v;
	             txtVw.setText(userBoard);
	            
	    	     v= findViewById(R.id.textViewUserWater);
	             txtVw = (TextView)v;
	             txtVw.setText(opponentBoard);
        	}
        	catch (Exception ex){
        		System.out.println("Broken");
        	}
    	}
    }
}
