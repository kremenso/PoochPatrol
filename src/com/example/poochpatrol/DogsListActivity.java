package com.example.poochpatrol;



import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.poochpatrol.adapter.DogAdapter;
import com.example.poochpatrol.database.DogDatabaseHandler;
import com.example.poochpatrol.model.Dog;

public class DogsListActivity extends Activity {
	
	public static List<Dog> dogsList;
	ListView dogListView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dogs_list);
        
        try {
        	DogDatabaseHandler db = new DogDatabaseHandler(this);
//        	 Bundle bundle = getIntent().getExtras();
        	dogsList = db.getAllDogs();
        	for(int i = 0; i<dogsList.size(); i++) {
        		Log.v("DOG", dogsList.get(i).toString());
        	}
//             dogsList = (DogsList) bundle.getParcelable("dogs");
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
    
    public void goToDog(String guid) { 	
    	Bundle b = new Bundle();
		b.putString("guid", guid);
		Intent intent = new Intent(this, DogActivity.class);
		intent.putExtras(b);
		startActivity(intent);
    }

    public void onBackPressed() {
  	   Intent intent = new Intent(this, DogsListActivity.class);
  	   startActivity(intent);
     }
     
     public boolean onOptionsItemSelected(MenuItem item) {
         // Handle item selection
         switch (item.getItemId()) {
             case R.id.itemAddDog:
             	Intent addDogIntent = new Intent(this, AddDogActivity.class);
             	startActivity(addDogIntent);
                 return true;
             default:
             	Log.v("MENU", "DEFAULT");
                 return super.onOptionsItemSelected(item);
         }
     }

}
