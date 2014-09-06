package com.tiarsoft.googleMap.android;

import android.app.Activity;

import com.google.android.gms.maps.MapFragment;

public class GoogleMapFragment extends MapFragment {
	public static interface onReady {
		public void isReady();
	}

	public GoogleMapFragment() {
		super();

	}

	public static GoogleMapFragment newInstance() {
		GoogleMapFragment fragment = new GoogleMapFragment();
		return fragment;
	}

	onReady readyListener;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			this.readyListener = (onReady) activity;
		}
		catch (final ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		readyListener.isReady();
	}

}
