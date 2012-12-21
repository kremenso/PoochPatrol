package com.example.poochpatrol;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.poochpatrol.overlay.MapOverlay;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class RouteActivity extends MapActivity {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private MapOverlay itemizedoverlay;
	private MyLocationOverlay myLocationOverlay;

	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_route); // bind the layout to the activity
		Log.v("STEP", "1");
		// Configure the Map
		mapView = (MapView) findViewById(R.id.mapview);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapController = mapView.getController();
		mapController.setZoom(14); // Zoon 1 is world view
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GeoUpdateHandler());
		myLocationOverlay = new MyLocationOverlay(this, mapView);
		
		mapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				Log.v("MY LOCATION", ""+ myLocationOverlay.getMyLocation());
				mapView.getController().animateTo(myLocationOverlay.getMyLocation());
			}
		});

		
		Drawable drawable = this.getResources().getDrawable(R.drawable.close);
		itemizedoverlay = new MapOverlay(this, drawable);
		createMarker();
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public class GeoUpdateHandler implements LocationListener {

		
		public void onLocationChanged(Location location) {
			if (location != null) {
				double lat = location.getLatitude();
				double lng = location.getLongitude();
				
				GeoPoint point= new GeoPoint((int) lat * 1000000, (int) lng * 1000000);
				mapController.animateTo(point);
			}
		}

	
		public void onProviderDisabled(String provider) {
			  Toast.makeText(getApplicationContext(), "Gps Disabled",
		                Toast.LENGTH_SHORT).show();
		}


		public void onProviderEnabled(String provider) {
			  Toast.makeText(getApplicationContext(), "Gps Enabled",
		                Toast.LENGTH_SHORT).show();
		}

		
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	private void createMarker() {
		GeoPoint p = mapView.getMapCenter();
		OverlayItem overlayitem = new OverlayItem(p, "", "");
		itemizedoverlay.addOverlay(overlayitem);
		if (itemizedoverlay.size() > 0) {
			mapView.getOverlays().add(itemizedoverlay);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
	}

	@Override
	protected void onPause() {
		super.onPause();
		myLocationOverlay.disableMyLocation();
		myLocationOverlay.disableCompass();
	}
}