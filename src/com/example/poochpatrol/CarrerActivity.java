package com.example.poochpatrol;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.poochpatrol.adapter.FacebookFriendAdapter;
import com.example.poochpatrol.model.FacebookFriend;
import com.example.poochpatrol.model.FacebookFriendsList;

public class CarrerActivity extends Activity {
	
	ListView carrerListView;
	FacebookFriendsList friends;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrer);
        carrerListView = (ListView) findViewById(R.id.carrerListView);
        Bundle bundle = getIntent().getExtras();
        
        friends = (FacebookFriendsList) bundle.getParcelable("friends");
        ArrayAdapter<FacebookFriend> adapter = new FacebookFriendAdapter(this,  friends);
              
        carrerListView.setAdapter(adapter);
    }
    

 

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_carrer, menu);
        return true;
    }
    
    
}
