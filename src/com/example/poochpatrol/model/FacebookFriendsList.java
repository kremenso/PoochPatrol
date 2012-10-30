package com.example.poochpatrol.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class FacebookFriendsList extends ArrayList<FacebookFriend> implements Parcelable {

	public FacebookFriendsList() {
		
	}
	
	public FacebookFriendsList(Parcel in) {
		this();
		readFromParcel(in);
	}
	
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		 
         public FacebookFriendsList createFromParcel(Parcel in) {
                 return new FacebookFriendsList(in);
         }

         public Object[] newArray(int arg0) {
                 return null;
         }
	};



	private void readFromParcel(Parcel in) {
		this.clear();
        int size = in.readInt();

        for (int i = 0; i < size; i++) {
        	FacebookFriend ff = new FacebookFriend(in.readInt(), in.readString());
            this.add(ff);
         }
	}



	public int describeContents() {
         return 0;
	}



	public void writeToParcel(Parcel dest, int flags) {
         int size = this.size();
         dest.writeInt(size);
         for (int i = 0; i < size; i++) {
        	 FacebookFriend ff = this.get(i);
             dest.writeInt(ff.getId());
             dest.writeString(ff.getName());
         }
	}
}
