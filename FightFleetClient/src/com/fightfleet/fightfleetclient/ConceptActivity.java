package com.fightfleet.fightfleetclient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;


public class ConceptActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concept);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_concept, menu);
        return true;
    }
    
    public void testGet(View view) {
    	try {
	    	GetTask getTask = new GetTask();
	    	URL createUser = new URL("http://fightfleetapi.apphb.com/user/createuser");
	    	getTask.execute(createUser);
    	}
    	catch (Exception ex){
    	    		
    	}
    }
    
    public void testPost(View view){
    	try{
    		PostTask postTask = new PostTask();
    		//URL url = new URL("http://fightfleetapi.apphb.com/user/testpost");
    		URL url = new URL("http://posttestserver.com/post.php");
    		postTask.execute(url);
    	}
    	catch (Exception ex){
    		
    	}
    }
    
    private class GetTask extends AsyncTask<URL, Void, String> {

    	@Override
    	protected String doInBackground(URL... params) {
    		try
    		{
    			StringBuilder sb = new StringBuilder();
    		    //URL createUser = new URL("http://fightfleetapi.apphb.com/user/createuser");
    			for (int i =0; i < params.length; i++){
    				URL createUser =params[i];
    		        URLConnection yc = createUser.openConnection();
    		        BufferedReader in = new BufferedReader(new InputStreamReader(
    		                                    yc.getInputStream()));
    		        String inputLine;
    		        while ((inputLine = in.readLine()) != null) 
    		            sb.append(inputLine);
    		        in.close();
    			}
    		    
    	        return sb.toString();
    		}
    		catch (Exception ex){
    			return "There was an error.\n" + ex.getMessage() + "\n";
    		}
    	}
    	
    	@Override
    	protected void onPostExecute(String result){
          View v= findViewById(R.id.textViewResult);
          TextView txtVw = (TextView)v;
          txtVw.setText(result);
    	}
    }
    private class PostTask extends AsyncTask<URL, Void, String> {
    	
    	@Override
    	protected String doInBackground(URL... params) {
			try {
				StringBuilder sb = new  StringBuilder();
			    // Construct data
			    String data = URLEncoder.encode("test", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
			    //data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");

			    // Send data
			    for (int i = 0; i < params.length;i++){
			    	URL url = params[i];
				    URLConnection conn = url.openConnection();
				    conn.setDoOutput(true);
				    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				    wr.write(data);
				    wr.flush();
	
				    // Get the response
				    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				    String line;
				    while ((line = rd.readLine()) != null) {
				        sb.append(line);
				    }
				    wr.close();
				    rd.close();
			    }
			    return sb.toString();
			} catch (Exception e) {
				return "There was an error.\n"+ e.getMessage() + "\n" + e.getStackTrace();
			}
    	}
    	
    	@Override
    	protected void onPostExecute(String result){
    	    View v= findViewById(R.id.textViewResult);
            TextView txtVw = (TextView)v;
            txtVw.setText(result);
    	}
    }
}