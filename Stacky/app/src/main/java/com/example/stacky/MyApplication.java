package com.example.stacky;




import android.app.Application;

public class MyApplication  extends Application {
	
	private static boolean towerEnabled;
	private static boolean gpsEnabled;
	private static boolean isUsingGps;
	
	  /**
     * @return whether GPS (tower) is enabled
     */
    public static boolean isTowerEnabled() {
        return towerEnabled;
    }

    /**
     * @param towerEnabled set whether GPS (tower) is enabled
     */
    public static void setTowerEnabled(boolean towerEnabled) {
    	MyApplication.towerEnabled = towerEnabled;
    }

    /**
     * @return whether GPS (satellite) is enabled
     */
    public static boolean isGpsEnabled() {
        return gpsEnabled;
    }

    /**
     * @param gpsEnabled set whether GPS (satellite) is enabled
     */
    public static void setGpsEnabled(boolean gpsEnabled) {
    	MyApplication.gpsEnabled = gpsEnabled;
    }

    /**
     * @return the isUsingGps
     */
    public static boolean isUsingGps() {
        return isUsingGps;
    }
    
    /**
     * @param isUsingGps the isUsingGps to set
     */
    public static void setUsingGps(boolean isUsingGps) {
    	MyApplication.isUsingGps = isUsingGps;
    }


}
