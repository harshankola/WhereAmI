package com.paad.whereami;

import android.location.Location;
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
		
		String svcName=Context.LOCATION_SERVICE;
		LocationManager locationManager = (LocationManager) getSystemService(svcName);
		
		String provider=LocationManager.GPS_PROVIDER;
		Location l = locationManager.getLastKnownLocation(provider);
		
		updateWithNewLocation(l);
	}

	/**
	 * @param l
	 */
	private void updateWithNewLocation(Location location) {
		// TODO Auto-generated method stub
		TextView myLocationText;
		myLocationText=(TextView) findViewById(R.id.myLocationText);
		
		String latLongString="No location found";
		if (location!=null) {
			double lat=location.getLatitude();
			double lng=location.getLongitude();
			latLongString="Lat:"+lat+"\nLong:"+lng;
		}
		
		myLocationText.setText("Your Current Position is:\n"+latLongString);
	}

	

}
