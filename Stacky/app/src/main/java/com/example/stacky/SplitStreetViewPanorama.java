
package com.example.stacky;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * This shows how to create a simple activity with streetview and a map
 */
public class SplitStreetViewPanorama extends FragmentActivity
    implements OnMarkerDragListener, OnStreetViewPanoramaChangeListener {

    private StreetViewPanorama svp;
    private GoogleMap mMap;
    private Marker marker;

    // George St, Sydney
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);
   // private static final LatLng SYDNEY = new LatLng(51.5072, 0.1275);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setUpStreetViewPanoramaIfNeeded(savedInstanceState);
    }

    private void setUpStreetViewPanoramaIfNeeded(Bundle savedInstanceState) {
        if (svp == null) {
            //svp = ((SupportStreetViewPanoramaFragment)getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama)).getStreetViewPanorama(); 
        	svp=((SupportStreetViewPanoramaFragment)getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama)).getStreetViewPanorama();
        	if (svp != null) {
                if (savedInstanceState == null) {
                    svp.setPosition(SYDNEY);
                }
                svp.setOnStreetViewPanoramaChangeListener(this);
            }
        }
    }

    @Override
    public void onStreetViewPanoramaChange(StreetViewPanoramaLocation location) {
        if (location != null) {
          //marker.setPosition(location.position);
        }
    }

    private void setUpMap() {
        mMap.setOnMarkerDragListener(this);
        // Creates a draggable marker. Long press to drag.
        marker = mMap.addMarker(new MarkerOptions()
                .position(SYDNEY)
                .draggable(true));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        svp.setPosition(marker.getPosition(), 150);
    }

    @Override
    public void onMarkerDrag(Marker marker) {
    }
}