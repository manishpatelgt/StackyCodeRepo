package com.example.stacky;


import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler.Callback;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.Toast;

public class LocService2 extends Service  {
	private Timer taskSendMapMovements;
	private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);
	private Location CurrentLocation=null;
	protected LocationManager locationManager;
    
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		 //HandlerThread thread = new HandlerThread("ServiceStartArguments",Process.THREAD_PRIORITY_BACKGROUND);
		 //thread.start();
		 
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
        	
        	
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
   
    final Handler hToast = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            Toast.makeText(LocService2.this,
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

}
