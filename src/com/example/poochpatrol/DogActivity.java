package com.example.poochpatrol;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.poochpatrol.database.DogDatabaseHandler;
import com.example.poochpatrol.model.Dog;

public class DogActivity extends Activity {
	
	private Dog dog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);

        Bundle bundle = getIntent().getExtras();
        String guid = bundle.getString("guid");
        
        DogDatabaseHandler db = new DogDatabaseHandler(this);
        dog = db.getDog(guid);
        
        prepareDogView(dog);    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dog, menu);
        return true;
    }
    
    public void prepareDogView(Dog dog) {
    	TextView dogName = (TextView) findViewById(R.id.textViewName);
    	dogName.setText(dog.getName());
    	
    	TextView dogAge = (TextView) findViewById(R.id.textViewAge);
    	dogAge.setText("Age: " + String.valueOf(dog.getAge()));
    	
    	TextView dogBreed = (TextView) findViewById(R.id.textViewBreed);
    	dogBreed.setText(dog.getBreed());
    	
    	ImageView image = (ImageView) findViewById(R.id.imageViewImage);	  
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
            case R.id.itemDogList:
            	Intent dogsListIntent = new Intent(this, DogsListActivity.class);
            	startActivity(dogsListIntent);
                return true;
            case R.id.showWalk:
            	Intent routeIntent = new Intent(this, RouteActivity.class);
            	startActivity(routeIntent);
            	return true;
            default:
            	Log.v("MENU", "DEFAULT");
                return super.onOptionsItemSelected(item);
        }
    }
    
}
