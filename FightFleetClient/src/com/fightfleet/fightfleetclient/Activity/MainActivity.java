package com.fightfleet.fightfleetclient.Activity;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.fightfleet.fightfleetclient.Domain.Constants;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.util.Log;

import com.fightfleet.fightfleetclient.R;
import com.fightfleet.fightfleetclient.Domain.DefaultServiceInterface;
import com.fightfleet.fightfleetclient.Domain.LoginRequest;
import com.fightfleet.fightfleetclient.Domain.LoginResponse;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.UserData;

public class MainActivity extends Activity {
	static final String TAG ="MainActivity"; //added by Chris
   static final String PREFS_NAME = "MyPrefsFile";
   UserData m_UserData;
    ServiceInterface m_ServiceInterface = new DefaultServiceInterface();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void loginButtonClickHandler(View view){
    	try{
    		Intent intent = new Intent(this, LoginActivity.class);
    		startActivity(intent);
    	}
    	catch (Exception ex){
    		Log.e(Constants.TAG, ex.toString()); // added by Chris
    		ex.printStackTrace();  //added by Chris
    		try { 
  		      final String ERRORMSG = new String("Error MainActivity<>.loginButtonClickHandler(), " +
  		      		"    		                        check logcat for error code");
  		      FileOutputStream fOut = openFileOutput("androidlog.txt",
  		                                                            MODE_APPEND);
  		       OutputStreamWriter osw = new OutputStreamWriter(fOut); 

  		       osw.write(ERRORMSG);  //write out msg

  		       osw.flush(); //clear buffer
  		       osw.close(); //close file
  		 } catch (IOException ioe) //catch io error
  	      {ioe.printStackTrace();}
    	}
    }
    
    public void gamesButtonClickHandler(View view){
    	try	{
    		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
    		
    		if (settings.contains("username") && settings.contains("password")){
    			String usr = settings.getString("username", "");
    			String password = settings.getString("password", "");
    			
    			UserData usrData = new UserData(usr, password, 0, null);
    			AutomaticLoginTask task = new AutomaticLoginTask();
    			task.execute(usrData);
    		}
    		else{    	
	    		Intent intent = new Intent(this, LoginActivity.class);
	    		startActivity(intent);
    		}
    	}
    	catch (Exception ex){
    		Log.e(Constants.TAG, ex.toString()); // added by Chris
    		ex.printStackTrace();  //added by Chris
    		try { 
  		      final String ERRORMSG = new String("Error MainActivity<>.gamesButtonClickHandler(), " +
  		      		"    		                        check logcat for error code");
  		      FileOutputStream fOut = openFileOutput("androidlog.txt",
  		                                                            MODE_APPEND);
  		       OutputStreamWriter osw = new OutputStreamWriter(fOut); 

  		       osw.write(ERRORMSG);  //write out msg

  		       osw.flush(); //clear buffer
  		       osw.close(); //close file
  		 } catch (IOException ioe) //catch io error
  	      {ioe.printStackTrace();}
    	}
    }
    
    public void createUserButtonClickHandler(View view){
    	Intent intent = new Intent(this, LoginActivity.class);       	
		intent.putExtra("IsCreateUser",  true);				    
		startActivity(intent);
    }
    
    private class AutomaticLoginTask extends AsyncTask<UserData, Void, UserData> {
    	@Override
    	protected UserData doInBackground(UserData... params) {
			try {
				if (params.length >0){
					UserData d = params[0];
					LoginRequest rq = new LoginRequest(d.getUserName(), d.getPassword(), getText(R.string.loginEndPoint).toString());
					LoginResponse response = m_ServiceInterface.requestLogin(rq);
					return new UserData(response.getUserName(), d.getPassword(), response.getUserID(), response.getUUID());
				}
				else return new UserData("", "", -1, null);
			} catch (Exception e) {
				Log.e(Constants.TAG, e.toString()); // added by Chris
	    		e.printStackTrace();  //added by Chris
	    		try { 
	    		      final String ERRORMSG = new String("Error AutomaticLoginTask<>.doInBackground(), " +
	    		      		"    		                        check logcat for error code");
	    		      FileOutputStream fOut = openFileOutput("androidlog.txt",
	    		                                                            MODE_APPEND);
	    		       OutputStreamWriter osw = new OutputStreamWriter(fOut); 

	    		       osw.write(ERRORMSG);  //write out msg

	    		       osw.flush(); //clear buffer
	    		       osw.close(); //close file
	    		 } catch (IOException ioe) //catch io error
	    	      {ioe.printStackTrace();}
				return new UserData("", "", -1, null);
			}
    	}
    	
    	@Override
    	protected void onPostExecute(UserData result){
        	try	{
        		m_UserData = result;
        		Intent intent = new Intent(MainActivity.this, GameListActivity.class);
        		intent.putExtra("UserData", m_UserData);	        		        		
        		startActivity(intent);
        	}
        	catch (Exception ex){
        		Log.e(Constants.TAG, ex.toString()); // added by Chris
        		ex.printStackTrace();  //added by Chris
        		System.out.println("Broken");
        		try { 
      		      final String ERRORMSG = new String("Error AutomaticLoginTask<>.onPostExecute(), " +
      		      		"    		                        check logcat for error code");
      		      FileOutputStream fOut = openFileOutput("androidlog.txt",
      		                                                            MODE_APPEND);
      		       OutputStreamWriter osw = new OutputStreamWriter(fOut); 

      		       osw.write(ERRORMSG);  //write out msg

      		       osw.flush(); //clear buffer
      		       osw.close(); //close file
      		 } catch (IOException ioe) //catch io error
      	      {ioe.printStackTrace();}
        	}
    	}
    }
}
