package com.example.poochpatrol;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.poochpatrol.AddDogActivity;

import com.example.poochpatrol.handler.PhotoHandler;
import com.example.poochpatrol.model.FacebookFriendsList;

public class PhotoActivity extends Activity implements SurfaceHolder.Callback {
	private Camera camera;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private int cameraId = 0;
	private static boolean previewing = false;
	private String requestClassName;
	
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
       
        Bundle bundle = getIntent().getExtras();
        requestClassName = (String) bundle.getString("activity");

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        
        
        if (!getPackageManager()
        	.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
        	Log.v("Camera:", "No camera device");
        } else {
        	cameraId = findBackFacingCamera();
        	camera = Camera.open(cameraId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_photo, menu);
        return true;
    }
    
    public void setPreview() {
    	if(!previewing) {
    		try {
    			if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
    			{
    				Camera.Parameters parameters = camera.getParameters();
    				parameters.set("rotation",90);
					parameters.set("orientation", "portrait");
					camera.setParameters(parameters);
					camera.setDisplayOrientation(90);
    			}
    			
    				
    			camera.setPreviewDisplay(surfaceHolder); camera.startPreview();
    			camera.startPreview();
    			previewing = true;
    		} catch (IOException e) {
    			Log.v("ERROR", e.getMessage());
    			e.printStackTrace();
    		}
    	}
    }
    
    public void unsetPreview() {
    	if(previewing) {
			camera.stopPreview();
			camera.release();
			camera = null; 
			previewing = false;
		}
    }
    
    private int findBackFacingCamera() {
      int cameraId = -1;
      int numberOfCameras = Camera.getNumberOfCameras();
      Log.v("NUMBER OF CAMERAS", ""+numberOfCameras);
      for (int i = 0; i < numberOfCameras; i++) {
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(i, info);
        if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
          Log.d("CAMERA", "Camera found");
          cameraId = i;
          break;
        }
      }
      return cameraId;
    }

	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	public void surfaceCreated(SurfaceHolder holder) {
		setPreview();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		unsetPreview();
	}
	
	protected void onPause() {
		unsetPreview();
		 super.onPause();
	}
	
	public void onCameraPhotoClick(View view) {
		camera.takePicture(null, null, new PhotoHandler(this));
	}
	
	public void onBrowseClick(View view) {
		Intent fileBrowserIntent = new Intent(this, FileBrowserActivity.class);
		Bundle b = new Bundle();
		b.putString("activity", AddDogActivity.class.getName());
		fileBrowserIntent.putExtras(b);
    	startActivity(fileBrowserIntent);
        
	}
	
	public void backToRequestActvity(String photo_src){
		try {
			Intent intent = new Intent(this, Class.forName(requestClassName));
			Bundle b = new Bundle();
			b.putString("photo_src", photo_src);
			intent.putExtras(b);
			startActivity(intent);
		} catch (ClassNotFoundException e) {
			
		}	
	}

}
