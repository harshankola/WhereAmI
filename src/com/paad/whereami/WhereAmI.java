package com.paad.whereami;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;

import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class WhereAmI extends MapActivity {

	private MapController mapController;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// Get a reference to the MapView
		MapView myMapView = (MapView) findViewById(R.id.myMapView);

		// Get the Map View's controller
		mapController = myMapView.getController();

		// Configure the map display options
		myMapView.setSatellite(true);
		myMapView.setBuiltInZoomControls(true);

		// Zoom in
		mapController.setZoom(17);

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
		String addressString = "No address found";
		if (location != null) {
			// Update the map location.
			Double geoLat = location.getLatitude() * 1E6;
			Double geoLng = location.getLongitude() * 1E6;
			GeoPoint point = new GeoPoint(geoLat.intValue(), geoLng.intValue());
			mapController.animateTo(point);

			double lat = location.getLatitude();
			double lng = location.getLongitude();
			latLongString = "Lat:" + lat + "\nLong:" + lng;

			double latitude = location.getLatitude();
			double longitude = location.getLongitude();

			Geocoder gc = new Geocoder(this, Locale.getDefault());

			if (!Geocoder.isPresent()) {
				addressString = "No geocoder available";
			} else {
				try {
					List<Address> addresses = gc.getFromLocation(latitude,
							longitude, 1);
					StringBuilder sb = new StringBuilder();
					if (addresses.size() > 0) {
						Address address = addresses.get(0);

						for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
							sb.append(address.getAddressLine(i)).append("\n");
						}

						sb.append(address.getLocality()).append("\n");
						sb.append(address.getPostalCode()).append("\n");
						sb.append(address.getCountryName());
					}
					addressString = sb.toString();
				} catch (IOException e) {
					// TODO: handle exception
					Log.d("WHEREAMI", "IO Exception", e);
				}
			}
		}

		myLocationText.setText("Your Current Position is:\n" + latLongString
				+ "\n\n" + addressString);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.android.maps.MapActivity#isRouteDisplayed()
	 */
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
