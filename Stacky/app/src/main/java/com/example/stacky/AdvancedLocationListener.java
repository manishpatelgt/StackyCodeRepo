package com.example.stacky;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.Looper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

public class AdvancedLocationListener implements LocationListener {

    private static final Logger logger = LoggerFactory.getLogger(AdvancedLocationListener.class);
    private OnLocationChangeListener locationListener;
    private String bestProvider = LocationManager.NETWORK_PROVIDER, bestAvailableProvider = null;
    private LocationManager locationManager;
    private Location currentLocation;
    private Looper looper;
    private Context context;
    public  int INTERVAL_THIRTY_SECONDS = 1000 * 30;

    public AdvancedLocationListener(Context context) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.context = context;
    }

    public void startTracking() {
        // Cleaning up...
        locationManager.removeUpdates(this);

        // Request location updates on the background thread
        HandlerThread thread = new HandlerThread("AdvancedLocationListener");
        thread.start();
        looper = thread.getLooper();

        getBestProvider();
        getBestAvailableProvider();

        requestLocationUpdates();
    }

   public void stopTracking() {
        if (locationManager != null)
            locationManager.removeUpdates(this);

        if (looper != null) {
            looper.quit();
            looper = null;
        }

        logger.debug("Stopped location tracking");
    }

    public void getBestProvider() {
        Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);

        bestProvider = locationManager.getBestProvider(criteria, false);
        logger.debug("Best location provider: " + getProviderDetails(locationManager, bestProvider));
    }

    private void getBestAvailableProvider() {
        Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_LOW);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);

        bestAvailableProvider = locationManager.getBestProvider(criteria, true);
        logger.debug("Using this best available Location Provider: " + getProviderDetails(locationManager, bestProvider));
    }

    private void requestLocationUpdates() {

        if (bestProvider == null) {
            logger.info("No Location Providers exist on the device.");
        } else if (bestProvider.equals(bestAvailableProvider)) {
            locationManager.requestLocationUpdates(bestAvailableProvider, INTERVAL_THIRTY_SECONDS, 0, this, looper);
        } else {
            locationManager.requestLocationUpdates(bestProvider, INTERVAL_THIRTY_SECONDS, 0, this, looper);

            if (bestAvailableProvider != null) {
                locationManager.requestLocationUpdates(bestAvailableProvider, INTERVAL_THIRTY_SECONDS, 0, this, looper);
            } else {
                // We could not find the best provider quickly enough, so scan the enabled providers
                final List<String> providers = locationManager.getAllProviders();

                for (String provider : providers) {
                    locationManager.requestLocationUpdates(provider, INTERVAL_THIRTY_SECONDS, 0, this, looper);
                }
            }
        }

    }

    public Location getLastKnownLocation() {
        return locationManager.getLastKnownLocation(bestProvider);
    }

    public Location getRecentLocation() {

        currentLocation = getLastKnownLocation();

        // Create a dummy manual location if GPS was turned off
        if (currentLocation == null) {
            currentLocation.setTime(new Date().getTime());

            // GlobeTech office coordinates
            currentLocation.setLatitude(51.851116);
            currentLocation.setLongitude(-8.482436);
        }

        return currentLocation;
    }

    public Location getLastKnownLocationByProvider(LocationProvider provider) {
        Location location = null;
        String providerName = provider.getName();

        try {
            if (locationManager.isProviderEnabled(providerName)) {
                location = locationManager.getLastKnownLocation(providerName);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Cannot access LocationProvider: " + provider);
        }

        return location;
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;

        if (locationListener != null)
            locationListener.onLocationChanged(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        logger.info("OnStatusChanged: " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        logger.info("onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        logger.info("onProviderDisabled: " + provider);
    }

    public void setOnLocationChangeListener(OnLocationChangeListener listener) {
        this.locationListener = listener;
    }

    public static interface OnLocationChangeListener {
        public void onLocationChanged(Location location);
    }
    
    public static String getProviderDetails(LocationManager locationManager, String provider) {
        LocationProvider info = locationManager.getProvider(provider);
        StringBuilder builder = new StringBuilder();

        builder.append("LocationProvider[")
                .append("name=").append(info.getName())
                .append(",enabled=").append(locationManager.isProviderEnabled(provider))
                .append(",getAccuracy=").append(info.getAccuracy())
                .append(",getPowerRequirement=").append(info.getPowerRequirement())
                .append(",hasMonetaryCost=").append(info.hasMonetaryCost())
                .append(",requiresCell=").append(info.requiresCell())
                .append(",requiresNetwork=").append(info.requiresNetwork())
                .append(",requiresSatellite=").append(info.requiresSatellite())
                .append(",supportsAltitude=").append(info.supportsAltitude())
                .append(",supportsBearing=").append(info.supportsBearing())
                .append(",supportsSpeed=").append(info.supportsSpeed())
                .append("]");

        return builder.toString();
    }

}