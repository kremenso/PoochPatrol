package com.example.poochpatrol.loader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;

import com.example.poochpatrol.loader.RESTLoader.RESTResponse;
import com.example.poochpatrol.model.Dog;

public class ImageLoader extends FragmentActivity implements
LoaderCallbacks<com.example.poochpatrol.loader.RESTLoader.RESTResponse> {
	
	private static final int LOADER = 0x2;
	private static final String URI = "URI";
	private Dog dog;

	
	public ImageLoader(Dog dog) {
		this.dog = dog;

		
		Bundle args = new Bundle();
		Uri uri = Uri.parse(dog.getImage());
		args.putParcelable(URI, uri);
		Log.v("DOG in ImageLoader", dog.toString());
		Bitmap bm = this.loadImageFromUrl(dog.getImage());
		
	
     
		
//		getSupportLoaderManager().initLoader(LOADER, args, this);
	}

	public Bitmap loadImageFromUrl(String url) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy); 
		
        URL m;
        InputStream i = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream out =null;
        try {
            m = new URL(url);
            i = (InputStream) m.getContent();
            bis = new BufferedInputStream(i,1024 * 8);
            out = new ByteArrayOutputStream();
            int len=0;
            byte[] buffer = new byte[1024];
            while((len = bis.read(buffer)) != -1){
                out.write(buffer, 0, len);
            }
            out.close();
            bis.close();
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        byte[] data = out.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        //Drawable d = Drawable.createFromStream(i, "src");
        return bitmap;
    }
	
	public Loader<RESTResponse> onCreateLoader(int arg0, Bundle arg1) {
		if (arg1 != null && arg1.containsKey(URI)) {
			Uri action = arg1.getParcelable(URI);
			
			
			
			Log.v("ACTION", action.toString());
//			return new RESTLoader(this, RESTLoader.HTTPVerb.GET, action);
		}
		return null;
	}

	public void onLoadFinished(Loader<RESTLoader.RESTResponse> loader,
			RESTLoader.RESTResponse data)  {
//			Log.v("RES", data.getData());
		
	}

	public void onLoaderReset(Loader<RESTResponse> arg0) {
		// TODO Auto-generated method stub
		
	}

}
