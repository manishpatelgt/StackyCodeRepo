package com.example.stacky;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	
	private static final String TAG = "BOOMBOOMTESTGPS";
	private LocationManager mLocationManager = null;
	private static final int LOCATION_INTERVAL = 1000;
	private static final float LOCATION_DISTANCE = 1f;
	public static final String TIMESTAMP_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss";
	private  SimpleDateFormat timeAndDateIsoFormatter = new SimpleDateFormat(TIMESTAMP_FORMAT_ISO8601);
    public static final String TIMESTAMP_UTC_ZONE = "UTC";
	public TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(TIMESTAMP_UTC_ZONE);

	private class LocationListener implements android.location.LocationListener {
		Location mLastLocation;

		public LocationListener(String provider) {
			Log.e(TAG, "LocationListener " + provider);
			mLastLocation = new Location(provider);
		}

		@Override
		public void onLocationChanged(Location location) {
			Log.e(TAG, "onLocationChanged: " + location);
			mLastLocation.set(location);
			Log.e(TAG, "get Location: " +formatLocation(location));
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			Log.e(TAG, "onProviderDisabled: " + provider);
		}

		@Override
		public void onProviderEnabled(String provider) {
			Log.e(TAG, "onProviderEnabled: " + provider);
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			Log.e(TAG, "onStatusChanged: " + provider);
		}
	}

	LocationListener[] mLocationListeners = new LocationListener[] {
			new LocationListener(LocationManager.GPS_PROVIDER),
			new LocationListener(LocationManager.NETWORK_PROVIDER) };

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.e(TAG, "onStartCommand");
		super.onStartCommand(intent, flags, startId);
		return START_STICKY;
	}

	@Override
	public void onCreate() {
		Log.e(TAG, "onCreate");
		initializeLocationManager();
		try {
			mLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL,
					LOCATION_DISTANCE, mLocationListeners[1]);
		} catch (java.lang.SecurityException ex) {
			Log.i(TAG, "fail to request location update, ignore", ex);
		} catch (IllegalArgumentException ex) {
			Log.d(TAG, "network provider does not exist, " + ex.getMessage());
		}
		try {
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, LOCATION_INTERVAL,
					LOCATION_DISTANCE, mLocationListeners[0]);
		} catch (java.lang.SecurityException ex) {
			Log.i(TAG, "fail to request location update, ignore", ex);
		} catch (IllegalArgumentException ex) {
			Log.d(TAG, "gps provider does not exist " + ex.getMessage());
		}
	}

	@Override
	public void onDestroy() {
		Log.e(TAG, "onDestroy");
		super.onDestroy();
		if (mLocationManager != null) {
			for (int i = 0; i < mLocationListeners.length; i++) {
				try {
					mLocationManager.removeUpdates(mLocationListeners[i]);
				} catch (Exception ex) {
					Log.i(TAG, "fail to remove location listners, ignore", ex);
				}
			}
		}
	}

	private void initializeLocationManager() {
		Log.e(TAG, "initializeLocationManager");
		if (mLocationManager == null) {
			mLocationManager = (LocationManager) getApplicationContext()
					.getSystemService(Context.LOCATION_SERVICE);
		}
	}
	
	public  String formatLocation(Location location) {
	    if (location == null)
	        return new String();

	    String provider = location.getProvider();
	    double latitude = location.getLatitude();
	    double longitude = location.getLongitude();
	    float accuracy = location.getAccuracy();
	    long time = location.getTime();

	    return formatLocation(provider, latitude, longitude, accuracy, time);
	}

	/**
	 * Converts location data to a formatted string.
	 */
	public  String formatLocation(String provider, double latitude, double longitude, float accuracy, long time) {
	    String timestamp = getUtcString(time);
	    return String.format("%s | lat/lng=%f/%f | accuracy=%f | Time=%s", provider, latitude, longitude, accuracy, timestamp);
	}



	public  String getUtcString(long time) {
	    timeAndDateIsoFormatter.setTimeZone(TIMEZONE_UTC);
	    return timeAndDateIsoFormatter.format(time);
	}
}