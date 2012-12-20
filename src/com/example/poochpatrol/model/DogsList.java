package com.example.poochpatrol.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

public class DogsList extends ArrayList<Dog> implements Parcelable {

	public DogsList(Parcel in) {
		this();
		readFromParcel(in);
	}
	
	public DogsList() { }

	public int describeContents() {
		return 0;
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		 
        public DogsList createFromParcel(Parcel in) {
                return new DogsList(in);
        }

        public Object[] newArray(int arg0) {
                return null;
        }
	};
	
	private void readFromParcel(Parcel in) {
		this.clear();
        int size = in.readInt();

        for (int i = 0; i < size; i++) {
        	Dog d = new Dog(in.readString(), in.readString(), 
        			in.readString(), in.readInt(), in.readString(), in.readString());
            this.add(d);
         }
	}
	
	public void writeToParcel(Parcel dest, int flags) {
        int size = this.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
       	 Dog dog = this.get(i);
            dest.writeString(dog.getGuid());
            dest.writeString(dog.getName());
            dest.writeString(dog.getBreed());
            dest.writeInt(dog.getAge());
            dest.writeString(dog.getOwnerUid());
            dest.writeString(dog.getImage());
        }
	}


}
