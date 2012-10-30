package com.example.poochpatrol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.poochpatrol.loader.RESTLoader;
import com.example.poochpatrol.loader.RESTLoader.RESTResponse;
import com.example.poochpatrol.model.Dog;
import com.example.poochpatrol.model.DogsList;
import com.example.poochpatrol.setting.ServerUrl;
import com.facebook.android.Util;

public class AuthorizeActivity extends FragmentActivity implements
		LoaderCallbacks<com.example.poochpatrol.loader.RESTLoader.RESTResponse> {

	private static final int LOADER = 0x1;
	private static final String URI = "URI";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authorize);

		showProgressAlert();

		Bundle bundle = getIntent().getExtras();
		String facebook_id = bundle.getString("facebook_id");

		Bundle args = new Bundle();
		Uri uri = Uri.parse("http://" + ServerUrl.SERVER_URL + "/"
				+ ServerUrl.USERS + "/" + facebook_id + "/"
				+ ServerUrl.BY_FACEBOOK);
		args.putParcelable(URI, uri);

		getSupportLoaderManager().initLoader(LOADER, args, this);

	}

	public Loader<RESTLoader.RESTResponse> onCreateLoader(int arg0, Bundle arg1) {
		if (arg1 != null && arg1.containsKey(URI)) {
			Uri action = arg1.getParcelable(URI);
			return new RESTLoader(this, RESTLoader.HTTPVerb.GET, action);
		}
		return null;
	}

	public void onLoadFinished(Loader<RESTLoader.RESTResponse> loader,
			RESTLoader.RESTResponse data) {
		int code = data.getCode();
		String json = data.getData();

		if (code == 200 && !json.equals("")) {
			try {
				JSONObject result = Util.parseJson(json);
				JSONArray dogs_json_array = result.getJSONArray("cared_dogs");
				DogsList dogs = new DogsList();

				for (int i = 0; i < dogs_json_array.length(); i++) {
					JSONObject row = dogs_json_array.getJSONObject(i);
					dogs.add(new Dog(row.getString("guid"), row
							.getString("name"), row.getString("breed"), row
							.getInt("age"), row.getString("owner_uid")));
				}
				Bundle b = new Bundle();
				b.putParcelable("dogs", dogs);

				Intent intent = new Intent(this, DogsListActivity.class);
				intent.putExtras(b);
				startActivity(intent);

			} catch (JSONException e) {
				Log.v("ERROR", "Error.dog_list");
				Intent intent = new Intent(this, AddDogActivity.class);
				startActivity(intent);
			}

		} else {
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
	}

	public void onLoaderReset(Loader<RESTResponse> arg0) {
	}

	public void showProgressAlert() {
		AlertDialog dialog = new AlertDialog.Builder(this).create();

		LayoutInflater inflater = getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.progress_dialog_layout,
				(ViewGroup) getCurrentFocus());
		dialog.setView(dialoglayout);
		TextView text = (TextView) dialoglayout
				.findViewById(R.id.textViewDialog);
		text.setText("Connecting with server");
		dialog.show();
	}

}
