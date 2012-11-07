package com.fightfleet.fightfleetclient.Activity;

import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.fightfleet.fightfleetclient.R;
import com.fightfleet.fightfleetclient.Domain.GameListRequest;
import com.fightfleet.fightfleetclient.Domain.GameListResponse;
import com.fightfleet.fightfleetclient.Domain.LoginRequest;
import com.fightfleet.fightfleetclient.Domain.LoginResponse;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.GameInformation;
import com.fightfleet.fightfleetclient.Lib.UserData;
import com.fightfleet.fightfleetclient.Test.TestServiceInterface;

public class LoginActivity extends Activity {
	ServiceInterface m_ServiceInterface = new TestServiceInterface();
	UserData m_UserData;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        EditText emailText = (EditText) this.findViewById(R.id.editTextLoginEmail);
    	EditText passwordText = (EditText)this.findViewById(R.id.editTextLoginPassword);
    	
    	emailText.setText("frank.castle@gmail.com");
    	passwordText.setText("test");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
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
    
    public void onSubmitLoginClick(View view){
    	EditText emailText = (EditText) this.findViewById(R.id.editTextLoginEmail);
    	EditText passwordText = (EditText)this.findViewById(R.id.editTextLoginPassword);
    	
    	String email = emailText.getText().toString();
    	String password = passwordText.getText().toString();
    	
    	m_UserData = new UserData(email, password, -1, null);
    	LoginTask loginTask = new LoginTask();
    	
    	loginTask.execute(m_UserData);
    }
    
    private class LoginTask extends AsyncTask<UserData, Void, UserData> {
    	@Override
    	protected UserData doInBackground(UserData... params) {
			try {
				if (params.length >0){
					UserData d = params[0];
					LoginRequest rq = new LoginRequest(d.getUserName(), d.getPassword());
					LoginResponse response = m_ServiceInterface.requestLogin(rq);
					return new UserData(response.getUserName(), d.getPassword(), response.getUserID(), response.getUUID());
				}
				else return new UserData("", "", -1, null);
			} catch (Exception e) {
				return new UserData("", "", -1, null);
			}
    	}
    	
    	@Override
    	protected void onPostExecute(UserData result){
        	try	{
        		m_UserData = result;
        		Intent intent = new Intent(LoginActivity.this, GameListActivity.class);
        		intent.putExtra("UserData", m_UserData);
        		startActivity(intent);
        	}
        	catch (Exception ex){
        		System.out.println("Broken");
        	}
    	}
    }
}
