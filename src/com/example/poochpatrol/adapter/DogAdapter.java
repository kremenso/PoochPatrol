package com.example.poochpatrol.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.poochpatrol.DogActivity;
import com.example.poochpatrol.R;
import com.example.poochpatrol.model.Dog;

public class DogAdapter extends ArrayAdapter<Dog> {

	private final List<Dog> list;
	private final Activity context;

	public DogAdapter(Activity context, List<Dog> list) {
		super(context, R.layout.facebook_friend, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView name, age, breed;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.dog, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) view
					.findViewById(R.id.textViewDogName);
			viewHolder.breed = (TextView) view
					.findViewById(R.id.textViewDogBreed);
			viewHolder.age = (TextView) view.findViewById(R.id.textViewDogAge);

			view.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
				
					
					Log.v("CLCIK", list.get(position).getGuid());
				}
			});
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.name.setText("ID:" + list.get(position).getName());
		holder.age.setText("" + list.get(position).getAge());
		holder.breed.setText(list.get(position).getBreed());
		return view;
	}

}
