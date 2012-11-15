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
import com.fightfleet.fightfleetclient.Domain.DefaultServiceInterface;
import com.fightfleet.fightfleetclient.Domain.GameListRequest;
import com.fightfleet.fightfleetclient.Domain.GameListResponse;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.GameInformation;
import com.fightfleet.fightfleetclient.Lib.UserData;

public class GameListActivity extends Activity {

	UserData m_UserData;
	ArrayList<GameInformation> m_GameInformation;
	ServiceInterface m_ServiceInterface = new DefaultServiceInterface();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        getActionBar().setDisplayHomeAsUpEnabled(true);
       
        Intent intent = getIntent();
        if (intent!=null){
        	m_UserData = intent.getParcelableExtra("UserData");
        }
		GameInformationTask task = new GameInformationTask();
		task.execute(m_UserData);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_game_list, menu);
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
    
    private class GameInformationTask extends AsyncTask<UserData, Void, ArrayList<GameInformation>> {
    	@Override
    	protected ArrayList<GameInformation> doInBackground(UserData... params) {
			try {
				if (params.length >0){
					UserData d = params[0];
					GameListRequest rq = new GameListRequest(d.getUserID(), d.getUUID(), getText(R.string.getGameListEndPoint).toString());
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
            	final ListView listView = (ListView)GameListActivity.this.findViewById(R.id.listViewGames);
            	listView.setAdapter(adapter);
            	
            	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           
                    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                        GameInformation gameInfo = (GameInformation)listView.getItemAtPosition(position);
                        //String str=(String)o;//As you are using Default String Adapter
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
