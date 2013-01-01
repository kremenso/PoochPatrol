package com.example.poochpatrol;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class FileBrowserActivity extends ListActivity {
	private List<String> item = null;
	private List<String> path = null;
	private String root="/";
	private TextView myPath;
	private String requestClassName;
	private File currentLocation;

	@Override

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        myPath = (TextView)findViewById(R.id.path);
        getDir(root);
        
        Bundle bundle = getIntent().getExtras();
        requestClassName = (String) bundle.getString("activity");
    }

	private void getDir(String dirPath) {
		
		myPath.setText("Location: " + dirPath);
	    item = new ArrayList<String>();
	    path = new ArrayList<String>();

	    File f = new File(dirPath);
	    File[] files = f.listFiles();
	    currentLocation = f;
	     if(!dirPath.equals(root)) {
	    	 item.add(root);
	    	 path.add(root);

	    	 item.add("../");
	    	 path.add(f.getParent());
	     }

	     for(int i=0; i < files.length; i++) {
	    	 File file = files[i];
	    	
	    	 if (file.canRead()) {
	    		 
	    		 if(file.isDirectory()) {
		    		 item.add(file.getName() + " /");
		    		 path.add(file.getPath());
	    		 } else {
		    		String filenameArray[] = file.getName().split("\\.");
			        String extension = filenameArray[filenameArray.length-1];
			        if (  extension.toLowerCase().equals("jpeg") || extension.toLowerCase().equals("jpg")  ) {
			        	item.add(file.getName());
			        	path.add(file.getPath());
			        }
	    		 }
	    	 } 
	     }
	     ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, R.layout.file_explorer_row, item);
	     setListAdapter(fileList);
 
	     	
	   }



	 	@Override

	 	protected void onListItemClick(ListView l, View v, int position, long id) {
	 		File file = new File(path.get(position));

	 	if (file.isDirectory()) {
	 		getDir(path.get(position));
	 	} else {
	 		Intent intent;
			try {
				intent = new Intent(this, Class.forName(requestClassName));
				Bundle b = new Bundle();
				b.putString("photo_src", file.getPath());
				intent.putExtras(b);
				startActivity(intent);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
	 	}
	}
	 	
	 public void onBackPressed() {
		 if (!currentLocation.getPath().equals(root)) {
			 getDir(currentLocation.getParentFile().getPath());
		 } else {
			 try {
				Intent intent = new Intent(this, Class.forName(requestClassName));
				startActivity(intent);
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
		 }
	 } 	
}
