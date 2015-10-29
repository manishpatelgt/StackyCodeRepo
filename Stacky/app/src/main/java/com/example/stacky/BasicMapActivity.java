package com.example.stacky;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v4.app.FragmentActivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BasicMapActivity extends FragmentActivity{
	/**
	 * Note that this may be null if the Google Play services APK is not
	 * available.
	 */
	private GoogleMap mMap;
	double latitude = 23.0509848;
	double longitude = 72.5189415;
	String title = "Hello World!!!", city = "Abad";
	Marker Contactloc;
	LatLng contactLoc;
	Button ib_back;
	//private String[] items = {getResources().getString(R.string.app_name)};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic_demo_new);
		setUpMapIfNeeded();
		
		// Setting a custom info window adapter for the google map		
		mMap.setInfoWindowAdapter(new InfoWindowAdapter() {
			
			// Use default InfoWindow frame
			@Override
			public View getInfoWindow(Marker marker) {				
				return null;
			}			
			
			// Defines the contents of the InfoWindow
			@Override
			public View getInfoContents(Marker marker) {
				
				   // Getting view from the layout file info_window_layout
				    View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);
				
					
					TextView tvLat = (TextView) v.findViewById(R.id.tv_title);
										
					String str=marker.getTitle();
					tvLat.setText(str);
				
				return v;
			}
			
		});
		
		mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker m1) {
				// TODO Auto-generated method stub
				try{
					
							Message mesg = new Message();
							Bundle b = new Bundle();
							b.putString("MSG", m1.getTitle());
							mesg.setData(b);
							//handler.sendMessage(mesg);
							
							 
				                AlertDialog.Builder alert = new AlertDialog.Builder(BasicMapActivity.this);
				                alert.setTitle(m1.getTitle());

				                WebView wv = new WebView(BasicMapActivity.this);
				                wv.loadUrl("https://www.google.com");
				                wv.setWebViewClient(new WebViewClient() {
				                    @Override
				                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
				                        view.loadUrl(url);

				                        return true;
				                    }
				                });

				                alert.setView(wv);
				                alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
				                    @Override
				                    public void onClick(DialogInterface dialog, int id) {
				                        dialog.dismiss();
				                    }
				                });
				                alert.show();
						
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}
		});
		
	}
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle b = msg.getData();
			String url="tel:"+b.getString("MSG");
			if (url.startsWith("tel:")) 
					{ 
					Intent intent = new Intent(Intent.ACTION_DIAL,
					Uri.parse(url)); 
					startActivity(intent);
					}
		     }
	};

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try{
			System.out.println("Map Pause state");
					if(mMap!=null){
						mMap.clear();
						mMap=null;
				}			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();

		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getApplicationContext());

		if (status == ConnectionResult.SUCCESS) {
		
			setUpMapIfNeeded();
			mMap.setMyLocationEnabled(true);
		} else {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getParent(),
					requestCode);
			dialog.show();
		}

	}

	private void setUpMapIfNeeded() {
		System.out.println("Setup If Needed");

		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {

			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map1)).getMap();
		
			mMap.setMyLocationEnabled(true);

			mMap.getUiSettings().setCompassEnabled(true);
			mMap.setTrafficEnabled(true);
			mMap.getUiSettings().setTiltGesturesEnabled(true);
			mMap.getUiSettings().setRotateGesturesEnabled(true);
			mMap.getUiSettings().setScrollGesturesEnabled(true);
			mMap.getUiSettings().setZoomControlsEnabled(true);
			mMap.getUiSettings().setZoomGesturesEnabled(true);
			setUpMap();
		} // Check if we were successful in obtaining the map.
		if (mMap != null) {
			mMap.setMyLocationEnabled(true);
			setUpMap();
		}
	}

	private void setUpMap() {
		System.out.println("Setup Map");
      try{
    	Contactloc=mMap.addMarker(new MarkerOptions()
  				.icon(BitmapDescriptorFactory.fromResource(R.drawable.mark_red))
  				.position(new LatLng(latitude, longitude)).title(title)
  				.snippet(city));
  		mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
  				latitude, longitude), 18.0f));
      }catch(Exception ew){
    	  ew.printStackTrace();
      }
	}

}
