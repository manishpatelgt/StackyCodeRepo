package com.order.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.order.bms.R;

import java.sql.Timestamp;
import java.util.List;

import com.order.bms.BuildConfig;
import com.order.models.CustomItem;

/**
 * Created by Manish on 2/28/2015.
 */
public class BMSAapplication extends Application {

    private static String appName;
    private static final String TAG = "ekawach";
    private static BMSAapplication applicationInstance;
    private static final Object lockObject = new Object();
    private static Context applicationContext = null;
    private static List<CustomItem> items2;
 
    @Override
    public void onCreate() {
        super.onCreate();

       // Initialize the Application singleton
        applicationInstance = this;
        applicationContext = getApplicationContext();
        preferences = applicationContext.getSharedPreferences("AppPreferences", Activity.MODE_PRIVATE);

        appName=getResources().getString(R.string.app_name);
       }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (BuildConfig.DEBUG)
            Log.d(TAG, "onTerminate");
    }

   
    public static void setOrderItems(List<CustomItem> items){
    	items2=items;
    }
    
    public static List<CustomItem> getOrderItems(){
    	return items2;
    }
    
    public static synchronized BMSAapplication getInstance() {
        return applicationInstance;
    }

    public static Context getContext() {
        return applicationContext;
    }

    public static void setContext(Context newContext) {
        applicationContext = newContext;
    }
    
    private static SharedPreferences preferences;
    private static SharedPreferences worldPreferences;

    public static SharedPreferences getPreferences() {
        return preferences;
    }

    public static SharedPreferences getPublicPreferences() {
        return worldPreferences;
    }
}
