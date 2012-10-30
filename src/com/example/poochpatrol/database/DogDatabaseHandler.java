package com.example.poochpatrol.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.poochpatrol.model.Dog;

public class DogDatabaseHandler extends SQLiteOpenHelper{

	private static final String TABLE_DOGS = "dogs";
	
	private static final String KEY_GUID = "guid";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_BREED = "breed";
    private static final String KEY_OWNER_UID = "owner_uid";
	
	public DogDatabaseHandler(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		 String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DOGS + "("
	                + KEY_GUID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
	                + KEY_NAME + " TEXT, "
	                + KEY_AGE + " INTEGER, "
	                + KEY_BREED + " TEXT, "
	                + KEY_OWNER_UID + " TEXT" + ")";
	        db.execSQL(CREATE_CONTACTS_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOGS);
	     onCreate(db);
	}
	
	public void addDog(Dog dog) {
		
	}

}
