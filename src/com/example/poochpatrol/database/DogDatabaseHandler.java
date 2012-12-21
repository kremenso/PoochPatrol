package com.example.poochpatrol.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.poochpatrol.model.Dog;

public class DogDatabaseHandler extends SQLiteOpenHelper {

	private static final String TABLE_DOGS = "dogs";

	private static final String KEY_GUID = "guid";
	private static final String KEY_NAME = "name";
	private static final String KEY_AGE = "age";
	private static final String KEY_BREED = "breed";
	private static final String KEY_OWNER_UID = "owner_uid";
	private static final String KEY_IMAGE = "image";

	public DogDatabaseHandler(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}
	
	public DogDatabaseHandler(Context context) {
		super(context, DatabaseConfig.DATABASE_NAME, null, DatabaseConfig.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DOGS + "("
				+ KEY_GUID + " STRING PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_AGE + " INTEGER, " + KEY_BREED + " TEXT, "
				+ KEY_OWNER_UID + " TEXT, " + KEY_IMAGE + " TEXT" + ")";
		db.execSQL(CREATE_CONTACTS_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOGS);
		onCreate(db);
	}

	public void addDog(Dog dog) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(KEY_GUID, dog.getGuid());
		values.put(KEY_NAME, dog.getName());
		values.put(KEY_AGE, dog.getAge());
		values.put(KEY_BREED, dog.getBreed());
		values.put(KEY_OWNER_UID, dog.getOwnerUid());
		values.put(KEY_IMAGE, dog.getImage());

		Log.v("VALUES", values.toString());

	
		db.insert(TABLE_DOGS, null, values);
		db.close();
	}

	public Dog getDog(String guid) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_DOGS, new String[] { KEY_GUID, KEY_NAME,
				KEY_AGE, KEY_BREED, KEY_OWNER_UID, KEY_IMAGE }, KEY_GUID + "=?",
				new String[] { guid }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		
		
		Dog dog = new Dog(cursor.getString(0), cursor.getString(1),
				cursor.getString(3), Integer.parseInt(cursor.getString(2)),
				cursor.getString(4), cursor.getString(5));

		return dog;
	}

	public List<Dog> getAllDogs() {
		List<Dog> dogList = new ArrayList<Dog>();

		String selectQuery = "SELECT  * FROM " + TABLE_DOGS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if (cursor.moveToFirst()) {
			do {
								
				Dog dog = new Dog(cursor.getString(0), cursor.getString(1),
						cursor.getString(3), Integer.parseInt(cursor.getString(2)), 
						cursor.getString(4), cursor.getString(5));
				dogList.add(dog);
			} while (cursor.moveToNext());
		}

		return dogList;
	}
	
	public int updateDog(Dog dog) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, dog.getName());
        values.put(KEY_BREED, dog.getBreed());
        values.put(KEY_AGE, dog.getAge());
        values.put(KEY_OWNER_UID, dog.getOwnerUid());
        values.put(KEY_IMAGE, dog.getImage());
        
        return db.update(TABLE_DOGS, values, KEY_GUID + " = ?",
                new String[] { String.valueOf(dog.getGuid()) });
    }
	
	public void deleteDog(Dog dog) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DOGS, KEY_GUID + " = ?",
                new String[] { String.valueOf(dog.getGuid()) });
        //db.close();
    }
	
	public int getCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DOGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 
        return cursor.getCount();
    }
	
	public boolean dogExist(int guid) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_DOGS, new String[] { KEY_GUID, KEY_NAME,
				KEY_AGE, KEY_BREED, KEY_OWNER_UID, KEY_IMAGE }, KEY_GUID + "=?",
				new String[] { String.valueOf(guid) }, null, null, null, null);
		if (cursor != null)
			return false;
		return true;
	}	
}
