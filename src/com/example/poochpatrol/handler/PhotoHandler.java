package com.example.poochpatrol.handler;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;

import com.example.poochpatrol.AddDogActivity;
import com.example.poochpatrol.PhotoActivity;
import com.example.poochpatrol.setting.Setting;

public class PhotoHandler implements PictureCallback{

	private Activity requester;
	
	public PhotoHandler(Activity photoActivity) {
		this.requester = photoActivity;
	}

	public void onPictureTaken(byte[] data, Camera camera) {
		File pictureFileDir = getDir();
		Log.v("TRY:", "GET DIR " + pictureFileDir.toString());
		if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
			Log.v("Error", "Can't create directory to save image.");
		    return;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String date = dateFormat.format(new Date());
		String photoFile = "Picture_" + date + ".jpg";

		String filename = pictureFileDir.getPath() + File.separator + photoFile;

		File pictureFile = new File(filename);

		try {
			FileOutputStream fos = new FileOutputStream(pictureFile);
		    fos.write(data);
		    fos.close();
		    ((PhotoActivity) requester).backToRequestActvity(filename);
		    
		} catch (Exception error) {
			Log.v("ERROR", "not saved: " + error.getMessage());
		}
	}
	  	
	private File getDir() {
		File sdDir = Environment
				.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		return new File(sdDir, Setting.PHOTO_DIR);
	}
}
