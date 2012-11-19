package com.fightfleet.fightfleetclient.Activity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fightfleet.fightfleetclient.R;
import com.fightfleet.fightfleetclient.Domain.CreateGameRequest;
import com.fightfleet.fightfleetclient.Domain.DefaultServiceInterface;
import com.fightfleet.fightfleetclient.Domain.GameDataResponse;
import com.fightfleet.fightfleetclient.Domain.GameListRequest;
import com.fightfleet.fightfleetclient.Domain.GameListResponse;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.GameInformation;
import com.fightfleet.fightfleetclient.Lib.GameStatus;
import com.fightfleet.fightfleetclient.Lib.UserData;

public class GameListActivity extends Activity {

	UserData m_UserData;
	ArrayList<GameInformation> m_GameInformation;
	ServiceInterface m_ServiceInterface = new DefaultServiceInterface();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fightfleet.fightfleetclient.R.layout.activity_game_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
       
        Intent intent = getIntent();
        if (intent!=null){
        	m_UserData = intent.getParcelableExtra("UserData");
        }
        if (m_UserData == null && savedInstanceState != null){
           m_UserData = savedInstanceState.getParcelable("UserData");
        }
		GameInformationTask task = new GameInformationTask();
		task.execute(m_UserData);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.fightfleet.fightfleetclient.R.menu.option_menu, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
    	 super.onSaveInstanceState(savedInstanceState);    
  	     savedInstanceState.putParcelable("UserData", m_UserData);       
	}
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case com.fightfleet.fightfleetclient.R.id.NewGame:
            	startNewGame();
            break;
            	
            	
        }
        return super.onOptionsItemSelected(item);
    }
    
    void startNewGame(){
    	CreateGameRequest request = new CreateGameRequest(m_UserData.getUserID(), m_UserData.getUUID(),	
    												getText(com.fightfleet.fightfleetclient.R.string.createGameEndPoint).toString());
    	StartNewGameTask task = new  StartNewGameTask();
        task.execute(request);    	
    }
    
    private class StartNewGameTask extends AsyncTask<CreateGameRequest, Void, GameDataResponse> {
    	@Override
    	protected GameDataResponse doInBackground(CreateGameRequest... params) {
			try {								   
				CreateGameRequest rq = params[0];
				GameDataResponse response = m_ServiceInterface.requestCreateGame(rq);
				return response;				
				
			} catch (Exception e) {
				return new GameDataResponse(-1, null, null, -1, -1,  GameStatus.Finished, -1);
			}
    	}
    	
    	@Override
    	protected void onPostExecute(GameDataResponse result){
        	try	{    		                    
            	Intent intent = new Intent(GameListActivity.this, GameActivity.class);
        		intent.putExtra("UserData", m_UserData);
        		intent.putExtra("GameID", result.getGameID());
        		startActivity(intent);              
        	}
        	catch (Exception ex){        		
        		System.out.print(ex.getMessage());
        	}
    	}
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
        		m_GameInformation = result;
            	ArrayAdapter<GameInformation> adapter = new ArrayAdapter<GameInformation>(GameListActivity.this,
            											android.R.layout.simple_list_item_1,m_GameInformation);
            	final ListView listView = (ListView)GameListActivity.this.findViewById(com.fightfleet.fightfleetclient.R.id.listViewGames);
            	listView.setAdapter(adapter);
            	
            	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        GameInformation gameInfo = (GameInformation)listView.getItemAtPosition(position);
                        
                    	Intent intent = new Intent(GameListActivity.this, GameActivity.class);
                		intent.putExtra("UserData", m_UserData);
                		intent.putExtra("GameID", gameInfo.GetGameID());
                		startActivity(intent);
                    }
                });
        	}
        	catch (Exception ex){
        		
        		System.out.print(ex.getMessage());
        	}
    	}
    }
    


}
