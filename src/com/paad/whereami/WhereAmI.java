package com.paad.whereami;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class WhereAmI extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		String svcName = Context.LOCATION_SERVICE;
		LocationManager locationManager = (LocationManager) getSystemService(svcName);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setSpeedRequired(false);
		criteria.setCostAllowed(true);
		String provider = locationManager.getBestProvider(criteria, true);

		Location l = locationManager.getLastKnownLocation(provider);

		updateWithNewLocation(l);

		locationManager.requestLocationUpdates(provider, 2000, 10,
				locationListener);
	}

	/**
	 * @param l
	 */
	private void updateWithNewLocation(Location location) {
		// TODO Auto-generated method stub
		TextView myLocationText;
		myLocationText = (TextView) findViewById(R.id.myLocationText);

		String latLongString = "No location found";
		if (location != null) {
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Lat:" + lat + "\nLong:" + lng;
		}

		myLocationText.setText("Your Current Position is:\n" + latLongString);
	}

	private final LocationListener locationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			updateWithNewLocation(location);
		}
	};
}
