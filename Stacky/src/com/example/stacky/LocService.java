package com.example.stacky;


import android.net.Uri;
import android.os.Process;


import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler.Callback;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

public class LocService extends Service implements AdvancedLocationListener.OnLocationChangeListener  {
	private Timer taskSendMapMovements;
	private GPSTracker gps;
	 private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);
	private Location CurrentLocation=null;
	// Declaring a Location Manager
	private AdvancedLocationListener locationListener;
    protected LocationManager locationManager;
    private OnLocationFixListener listener;
    
    public static final String TIMESTAMP_FORMAT_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss";
    private  SimpleDateFormat timeAndDateIsoFormatter = new SimpleDateFormat(TIMESTAMP_FORMAT_ISO8601);
    public static final String TIMESTAMP_UTC_ZONE = "UTC";
    public TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(TIMESTAMP_UTC_ZONE);
    
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		 //HandlerThread thread = new HandlerThread("ServiceStartArguments",Process.THREAD_PRIORITY_BACKGROUND);
		 //thread.start();
		 
		 locationListener = new AdvancedLocationListener(getApplicationContext());
	     locationListener.setOnLocationChangeListener(this);

	        // Get a dirty quick read
	     locationListener.getLastKnownLocation();
	     
	     locationListener.startTracking();
	     
	    // listener = (OnLocationFixListener) getApplicationContext();
	     //locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
			
		 //CheckTowerAndGpsStatus();
		 //gps=new GPSTracker(getApplicationContext());
	}
	
	 private void CheckTowerAndGpsStatus() {
	        MyApplication.setTowerEnabled(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
	        MyApplication.setGpsEnabled(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
	    }

	@Override
    public int onStartCommand(Intent intent, int flags,int startId) {
        try {
        	taskSendMapMovements = new Timer();


        	taskSendMapMovements.schedule(new taskSendMapMovements(),
                                1000,
                                10*1000);

        }  catch (Exception e) {
            Toast.makeText(this, "error running service: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        return START_NOT_STICKY;
    }
	
	
	 @Override
	    public void onDestroy() {
		 if (taskSendMapMovements!=null){
			 taskSendMapMovements.cancel();
			 taskSendMapMovements = null;
		    }
	    }
	
	public class taskSendMapMovements extends TimerTask {
        @Override
        public void run() {
            hhSendMapMovements.sendEmptyMessage(0);
        }
    };

    // /////////////////////

    final Runnable rSendMapMovements = new Runnable()
    {
        public void run()
        {
        	//CheckTowerAndGpsStatus();
        	//for location update
        	//locationListener.startTracking();
            procSendMapMovements();
        }
    };
    
    final Handler hhSendMapMovements = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            performOnBackgroundThread(rSendMapMovements);

            return false;
        }
    });

    // /////////////////////

    public void procSendMapMovements() {
        try {    

        	/*CurrentLocation=gps.getLocation();
        	
        	if(CurrentLocation!=null){/
        		showToast(CurrentLocation.toString(),CurrentLocation.getProvider());
        	}*/
        	
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
   
    final Handler hToast = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            Toast.makeText(LocService.this,
                    msg.getData().getString("msg")+" AND provider is: "+msg.getData().getString("msg2"),
                    Toast.LENGTH_LONG).show();
            return false;
        }
    });

private void showToast(String strMessage,String provider) {
    Message msg = new Message();
    Bundle b = new Bundle();
    b.putString("msg", strMessage);
    b.putString("msg2", provider);
    msg.setData(b);
    hToast.sendMessage(msg);
}

public static Thread performOnBackgroundThread(final Runnable runnable) {
        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    runnable.run();
                } finally {

                }
            }
        };
        t.start();
        return t;
    }

@Override
public void onLocationChanged(Location location) {
	// TODO Auto-generated method stub
	  logger.debug(formatLocation(location));
	
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
