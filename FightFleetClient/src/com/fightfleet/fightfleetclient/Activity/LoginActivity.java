package com.fightfleet.fightfleetclient.Activity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;


import com.fightfleet.fightfleetclient.R;
import com.fightfleet.fightfleetclient.Domain.CreateUserRequest;
import com.fightfleet.fightfleetclient.Domain.DefaultServiceInterface;
import com.fightfleet.fightfleetclient.Domain.LoginRequest;
import com.fightfleet.fightfleetclient.Domain.LoginResponse;
import com.fightfleet.fightfleetclient.Interface.ServiceInterface;
import com.fightfleet.fightfleetclient.Lib.UserData;
import com.fightfleet.fightfleetclient.Domain.Constants;


public class LoginActivity extends Activity {
	static final String PREFS_NAME = "MyPrefsFile";
	// added log activity (Nageena) 
		static final String TAG ="LoginActivity";
	//
	ServiceInterface m_ServiceInterface = new DefaultServiceInterface();
	UserData m_UserData;
	Boolean m_IsCreateUser = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        Intent intent = getIntent();
        if (intent!=null){
        	m_IsCreateUser = intent.getBooleanExtra("IsCreateUser", false);        	
        }
        //boolean switch decides whether or not to draw the confirmation password editbox or hide it.
        EditText confirmText = (EditText)this.findViewById(R.id.editTextConfirm);
       
        if (m_IsCreateUser){
        	confirmText.setVisibility(View.VISIBLE);        	
        }	
        else{
        	confirmText.setVisibility(View.GONE);
        }
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
    	try{    	
	    	EditText emailText = (EditText) this.findViewById(R.id.editTextLoginEmail);
	    	EditText passwordText = (EditText)this.findViewById(R.id.editTextLoginPassword);
	    	EditText confirmText = (EditText)this.findViewById(R.id.editTextConfirm);
	    	
	    	String email = emailText.getText().toString();
	    	String password = passwordText.getText().toString();
	    	String confirm = confirmText.getText().toString();
	    	if (m_IsCreateUser){
	    		if (!email.equals("") && !password.equals("") && !confirm.equals("") && password.equals(confirm)){
	    			m_UserData = new UserData(email,password,-1,null);
	    			CreateUserTask createUserTask = new CreateUserTask();
	    			createUserTask.execute(m_UserData);
	    			Log.d(Constants.TAG, "onClicked"); // added by Nageena 
	    		}
	    		else SetStatus("Invalid Entries");
	    	}
	    	else{
	    		if (!email.equals("") && !password.equals("")){
			    	m_UserData = new UserData(email, password, -1, null);
			    	LoginTask loginTask = new LoginTask();	    	
			    	loginTask.execute(m_UserData);
			    	Log.d(Constants.TAG, "onClicked"); // added by Nageena
	    		}
	    		else{
	    			SetStatus("Enter a username and password.");
	    		}
	    	}
    	}
    	catch (Exception ex){
    		Log.e(Constants.TAG, ex.toString()); // added by Nageena
    		SetStatus("Login Failed.");
    		try { 
    		      final String ERRORMSG = new String("Error LoginActivity<>.onSubmitLoginClick(), " +
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
    
    void SetStatus(String status){
    	 TextView statusTextView = (TextView)this.findViewById(R.id.textViewStatus);
    	 statusTextView.setText(status);
    }
    
    private class LoginTask extends AsyncTask<UserData, Void, UserData> {
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
				// added by Nageena
				Log.e(Constants.TAG, e.toString()); 
				e.printStackTrace();
				//
				try { 
	    		      final String ERRORMSG = new String("Error LoginTask<>.doInBackground(), " +
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
        		Intent intent = new Intent(LoginActivity.this, GameListActivity.class);
        		intent.putExtra("UserData", m_UserData);	        		
        		//this is a horribly insecure way of saving credentials.
	    	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    	    SharedPreferences.Editor editor = settings.edit();
	            editor.putString("username", result.getUserName());
	            editor.putString("password", result.getPassword());
	    	    editor.commit();	        	              	     
        		startActivity(intent);
        	}
        	catch (Exception ex){
        		Log.e(Constants.TAG, "Login Failed :" +ex);  // added by Nageena
        		SetStatus("Login Failed.");
        		try { 
      		      final String ERRORMSG = new String("Error LoginActivity<>.onPostExecute(), " +
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
    
    private class CreateUserTask extends AsyncTask<UserData, Void, UserData> {
    	@Override
    	protected UserData doInBackground(UserData... params) {
			try {
				if (params.length >0){
					UserData d = params[0];
					CreateUserRequest rq = new CreateUserRequest(d.getUserName(), d.getPassword(), getText(R.string.createUserEndPoint).toString());
					LoginResponse response = m_ServiceInterface.requestCreateUser(rq);
					return new UserData(response.getUserName(), d.getPassword(), response.getUserID(), response.getUUID());
				}
				else return new UserData("", "", -1, null);
			} catch (Exception e) {
				Log.e(Constants.TAG,e.toString()); // added by nageena 
				SetStatus("Create User Failed.");
				try { 
	    		      final String ERRORMSG = new String("Error CreateUserTask<>.doInBackground(), " +
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
        		Intent intent = new Intent(LoginActivity.this, GameListActivity.class);
        		intent.putExtra("UserData", m_UserData);	        		
        		//this is a horribly insecure way of saving credentials.
	    	    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
	    	    SharedPreferences.Editor editor = settings.edit();
	            editor.putString("username", result.getUserName());
	            editor.putString("password", result.getPassword());
	    	    editor.commit();	        	              	     
        		startActivity(intent);
        	}
        	catch (Exception ex){
        		Log.e(Constants.TAG, ex.toString()); // added by nageena
        		SetStatus("Create User Failed."); 
        		try { 
      		      final String ERRORMSG = new String("Error LoginActivity<>.onPostExecute(), " +
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
