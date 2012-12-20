package com.example.poochpatrol.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Dog implements Parcelable {

	private String guid;
	private String name;
	private String breed;
	private int age;
	private String owner_uid;
	private String image;

	public Dog(String guid, String name, String breed, int age, String owner_uid, String image) {
		this.guid = guid;
		this.name = name;
		this.breed = breed;
		this.age = age;
		this.owner_uid = owner_uid;
		this.image = image;
	}

	public Dog(Parcel in) {
		this();
		readFromParcel(in);
	}

	public Dog() {
	}

	private void readFromParcel(Parcel in) {
		this.guid = in.readString();
		this.name = in.readString();
		this.breed = in.readString();
		this.age = in.readInt();
		this.owner_uid = in.readString();
		this.image = in.readString();
	}

	public String getGuid() {
		return this.guid;
	}

	public String getName() {
		return this.name;
	}

	public String getOwnerUid() {
		return this.owner_uid;
	}

	public String getBreed() {
		return this.breed;
	}

	public int getAge() {
		return this.age;
	}
	
	public String getImage() {
		return this.image;
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.getGuid());
		dest.writeString(this.getName());
		dest.writeString(this.getBreed());
		dest.writeInt(this.getAge());
		dest.writeString(this.getOwnerUid());
		dest.writeString(this.getImage());

	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		public Dog createFromParcel(Parcel in) {
			return new Dog(in);
		}

		public Object[] newArray(int arg0) {
			return null;
		}
	};
	
	public String toString() {
		return "Name: " + this.name + ", Age:" 
				+ this.age + ", Breed: " + this.breed + ", Image: " + this.image;
	}

}
