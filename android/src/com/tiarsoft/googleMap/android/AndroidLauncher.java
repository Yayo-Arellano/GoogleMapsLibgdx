package com.tiarsoft.googleMap.android;

import android.annotation.SuppressLint;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tiarsoft.googleMap.MainMap;
import com.tiarsoft.googleMap.RequestHandler;
import com.tiarsoft.googleMap.android.GoogleMapFragment.onReady;

public class AndroidLauncher extends AndroidApplication implements RequestHandler, onReady, LocationListener,
		GooglePlayServicesClient.ConnectionCallbacks {

	protected MainMap gdxApp;

	FrameLayout flGame;
	FrameLayout flMap;

	GoogleMap map;
	private GoogleMapFragment mMapFragment;
	private static final String MAP_FRAGMENT_TAG = "map";

	LocationClient locationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

		gdxApp = new MainMap(this);
		View gameView = initializeForView(gdxApp, cfg);

		flGame = (FrameLayout) findViewById(R.id.flGame);
		flGame.addView(gameView);

		flMap = (FrameLayout) findViewById(R.id.flMap);

		mMapFragment = (GoogleMapFragment) getFragmentManager().findFragmentByTag(MAP_FRAGMENT_TAG);
		if (mMapFragment == null) {
			mMapFragment = (GoogleMapFragment) GoogleMapFragment.newInstance();

			FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
			fragmentTransaction.replace(flMap.getId(), mMapFragment, MAP_FRAGMENT_TAG);
			fragmentTransaction.commit();

		}

		locationClient = new LocationClient(this, this, null);
		locationClient.connect();

	}

	@Override
	public void addMap(final int x, final int y, final int width, final int height) {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {

				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(width, height);
				lp.setMargins(x, 0, 0, y);
				lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
				lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
				flMap.setLayoutParams(lp);
				flMap.setVisibility(View.VISIBLE);

			}
		});

	}

	@Override
	public void removeMap() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				flMap.setVisibility(View.GONE);
			}
		});
	}

	@Override
	public void isReady() {
		map = mMapFragment.getMap();

		if (map != null) {
			map.setMyLocationEnabled(true);

			final LatLng markerPosition = new LatLng(26.9124857, -101.4180168);
			map.addMarker(new MarkerOptions().position(markerPosition)).showInfoWindow();

			map.setInfoWindowAdapter(new InfoWindowAdapter() {

				@Override
				public View getInfoWindow(Marker arg0) {
					return null;
				}

				@SuppressLint("InflateParams")
				@Override
				public View getInfoContents(Marker arg0) {
					View v = getLayoutInflater().inflate(R.layout.map_marker, null);
					return v;

				}
			});

			map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

				@Override
				public void onInfoWindowClick(Marker marker) {
					String geoUri = "http://maps.google.com/maps?q=loc:" + markerPosition.latitude + "," + markerPosition.longitude + " ("
							+ "LibGdx Test" + ")";
					Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(geoUri));
					startActivity(intent);

				}
			});

		}

	}

	@Override
	public void onLocationChanged(Location location) {
		gdxApp.setPosition((float) location.getLatitude(), (float) location.getLongitude());

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setInterval(5);
		request.setFastestInterval(1);
		locationClient.requestLocationUpdates(request, this);

	}

	@Override
	public void onDisconnected() {

	}

}
