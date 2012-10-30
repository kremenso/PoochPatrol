package com.example.poochpatrol;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class DogActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dog);
        
        Intent addDogIntent = new Intent(this, AddDogActivity.class);
        startActivity(addDogIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_dog, menu);
        return true;
    }
}
