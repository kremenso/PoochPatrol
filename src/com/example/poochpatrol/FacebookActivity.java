package com.example.poochpatrol;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.poochpatrol.loader.RESTLoader;
import com.example.poochpatrol.loader.RESTLoader.RESTResponse;
import com.example.poochpatrol.model.FacebookFriend;
import com.example.poochpatrol.model.FacebookFriendsList;
import com.example.poochpatrol.setting.ServerUrl;
import com.example.poochpatrol.setting.Setting;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;


public class FacebookActivity extends Activity {

	Intent carrerIntent;
	Intent authorizeIntent;
	
	static String facebook_id;
	static String facebook_name;	
	static FacebookFriendsList friends;
	static Facebook facebook = new Facebook(Setting.FACEBOOK_API_ID);
	static AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(facebook);
	  

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        
        authorizeIntent = new Intent(this, AuthorizeActivity.class);
        carrerIntent = new Intent(this, CarrerActivity.class);
        
        facebook.authorize(this, new authorizeFacebookDialogListener());
    }
    
    
    public void got_to_authotrize(){
    	Bundle b = new Bundle();
		b.putString("facebook_id", facebook_id);
		authorizeIntent.putExtras(b);
    	startActivity(authorizeIntent);
    }
        
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebook.authorizeCallback(requestCode, resultCode, data);
    }
    
    
    public class authorizeFacebookDialogListener implements DialogListener {

    	public void onComplete(Bundle values) {
    		getUserInfo();
    		getFriends();
    	}

    	public void onFacebookError(FacebookError e) {}

    	public void onError(DialogError e) {}

    	public void onCancel() {}
    	
    	
    }
    
    public void getUserInfo() {
		mAsyncRunner.request("me", new meFacebokRequestListener());
	}
	
	public void getFriends() {
		mAsyncRunner.request("me/friends", new friendsRequestListener());
	}
    
    public class meFacebokRequestListener implements RequestListener {

    	public void onComplete(String response, Object state) {
    		try {
    			JSONObject json = Util.parseJson(response);

                facebook_id = json.getString("id");
                facebook_name = json.getString("name");
                
                got_to_authotrize();
       
              } catch (JSONException e) {
                      Log.d("JSON-ERROR", "JSONException: " + e.getMessage());
              } catch (FacebookError e) {
                      Log.d("Facebook-ERROR", "FacebookError: " + e.getMessage());
              }
      }

    	public void onIOException(IOException e, Object state) {}

    	public void onFileNotFoundException(FileNotFoundException e, Object state) {}

    	public void onMalformedURLException(MalformedURLException e, Object state) {}

    	public void onFacebookError(FacebookError e, Object state) {}

    }
    
    public class friendsRequestListener implements RequestListener {
    	
		public void onComplete(String response, Object state) {
    		friends = new FacebookFriendsList();
    		
    		try {
    			final JSONObject json = new JSONObject(response);
    			JSONArray data = json.getJSONArray("data");
    			for (int i = 0; i<data.length(); i++) {
    				JSONObject row = data.getJSONObject(i);
    				friends.add(new FacebookFriend(row.getInt("id"), row.getString("name")));
    			}
   			
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
    	}

    	public void onIOException(IOException e, Object state) {}

    	public void onFileNotFoundException(FileNotFoundException e, Object state) {}

    	public void onMalformedURLException(MalformedURLException e, Object state) {}

    	public void onFacebookError(FacebookError e, Object state) {}

    }

 
    
}
