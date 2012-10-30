package com.example.poochpatrol;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;


public class AddDogActivity extends Activity {

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dog);
        
        Bundle bundle = getIntent().getExtras();
        try {
        	String photoSrc = (String) bundle.getString("photo_src");
        	ImageView dogImage = (ImageView) findViewById(R.id.imageViewDog);
        	
        	BitmapFactory.Options options = new BitmapFactory.Options();
        	options.inSampleSize = 2;
        	Bitmap bm = BitmapFactory.decodeFile(photoSrc, options);
        	dogImage.setImageBitmap(bm); 
		} catch (Exception e) {
			// TODO: handle exception
		}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_add_dog, menu);
    	getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_carrers_item:
            	FacebookActivity fa = new FacebookActivity();
            	Intent carrerIntent = new Intent(this, CarrerActivity.class);
            	Bundle b = new Bundle();
        		b.putParcelable("friends", fa.friends);
        		carrerIntent.putExtras(b);
        		startActivity(carrerIntent);
                return true;
            default:
            	Log.v("MENU", "DEFAULT");
                return super.onOptionsItemSelected(item);
        }
    }
    

   public void onPhotoClick(View view) {
	   Intent intent = new Intent(this, PhotoActivity.class);
	   Bundle b = new Bundle();
	   b.putString("activity", AddDogActivity.class.getName());
	   intent.putExtras(b);
	   startActivity(intent);	  
   }
   
   public void onBackPressed() {
	   Intent intent = new Intent(this, DogsListActivity.class);
	   startActivity(intent);
   }

}
