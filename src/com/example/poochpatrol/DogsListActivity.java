package com.example.poochpatrol;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.poochpatrol.adapter.DogAdapter;
import com.example.poochpatrol.model.Dog;
import com.example.poochpatrol.model.DogsList;

public class DogsListActivity extends Activity {
	
	public static DogsList dogsList;
	ListView dogListView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);
        
        try {
        	 Bundle bundle = getIntent().getExtras();
             dogsList = (DogsList) bundle.getParcelable("dogs");
             Log.v("DOGS LIST", dogsList.toString());
        } catch (Exception e) {
        	Log.v("ERROR", "dogs bundle empty");
        }
        
        if(dogsList.size() > 0) {
        	dogListView = (ListView) findViewById(R.id.dogsListView);
        	 
             ArrayAdapter<Dog> adapter = new DogAdapter(this,  dogsList);
                   
             dogListView.setAdapter(adapter);
        }
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dogs_list, menu);
        return true;
    }
    
    public void onClick(View v) {
    	Log.v("ON", "CLICK");
    }


}
