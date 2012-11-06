package com.fightfleet.fightfleetclient.Activity;

import com.fightfleet.fightfleetclient.R;
import com.fightfleet.fightfleetclient.R.layout;
import com.fightfleet.fightfleetclient.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

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
    
    public void conceptButtonClickHandler(View view) {
    	try {
    		Intent intent = new Intent(this, GameActivity.class);
    		startActivity(intent);
    	}
    	catch (Exception ex){
    	    		
    	}
    }
    
    public void loginButtonClickHandler(View view){
    	try	{
    		Intent intent = new Intent(this, LoginActivity.class);
    		startActivity(intent);
    	}
    	catch (Exception ex){
    		
    	}
    }
    
    public void createUserButtonClickHandler(View view){
    	
    }
}
