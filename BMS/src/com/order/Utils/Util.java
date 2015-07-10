package com.order.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Manish on 2/28/2015.
 */
public class Util {

    public static String DB_NAME="DB.db";
    public static String DB_PATH="/data/data/com.order.bms/databases/";
    public static final String DATE_yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd hh:mm:ss";
    public static final String DATE_yyyy_MM = "yyyy-MM-dd";
    public static final String DATE_YYYY_MM_DD = "yyyy-MlM-dd"; //1970-01-01
    public static final String DATE_yyyy_MM_dd_HH_mm_ss  = "yyyy-MM-dd HH:mm:ss";  // 1970-01-01 00:00
    public static final String DATE_yyyy_MM_dd_HH_mmZ  = "yyyy-MM-dd HH:mmZ";  //  1970-01-01 00:00+0000
    public static final String DATE_yyyy_MM_dd_HH_mm_ss_SSSZ  = "yyyy-MM-dd HH:mm:ss.SSSZ"; // 1970-01-01 00:00:00.000+0000
    public static final String DATE_yyyy_MM_dd_T_HH_mm_ss_SSSZ  = "yyyy-MM-dd'T'HH:mm:ss.SSS"; // 1970-01-01T00:00:00.000+0000

    public static final int INTERVAL_ONE_SECOND = 1000;
    public static final int INTERVAL_TWENTY_SECOND = 20000;
    public static final int INTERVAL_ONE_MINUTE = INTERVAL_ONE_SECOND * 60;
    public static final int INTERVAL_FIFTEEN_SECONDS = INTERVAL_ONE_SECOND * 15;
    public static final int INTERVAL_THIRTY_SECONDS = INTERVAL_ONE_SECOND * 30;
    
    public static boolean is_finish=false;
    public static boolean is_finish2=false;

    // How often to request location updates
    public static final int UPDATE_INTERVAL_IN_SECONDS = 30;
    // A fast interval ceiling
    public static final int FAST_CEILING_IN_SECONDS = 10;
    // The rate in milliseconds at which your app prefers to receive location updates
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = INTERVAL_ONE_SECOND * UPDATE_INTERVAL_IN_SECONDS;
    // A fast ceiling of update intervals, used when the app is visible
    public static final long FAST_INTERVAL_CEILING_IN_MILLISECONDS = INTERVAL_ONE_SECOND * FAST_CEILING_IN_SECONDS;


    public static String getDateFormatted(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
        return sdf.format(date);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }
	
    public static String getDateFormatted2(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM);
        return sdf.format(date);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }

    public static String getDateFormatted(long miliseconds){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_yyyy_MM_dd_hh_mm_ss);
        return sdf.format(miliseconds);
        //return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_HH_mm_ss, new Date(miliseconds)));
    }

	 public static Timestamp getCurrentTimestamp() {
        Date now = Calendar.getInstance().getTime();
        return new Timestamp(now.getTime());
    }

	   public static String getHumanReadableDifference(long startTime, long endTime) {
        long timeDiff = endTime - startTime;

        return String.format("%02d:%02d:%02d",
                TimeUnit.MICROSECONDS.toHours(timeDiff),
                TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)),
                TimeUnit.MILLISECONDS.toSeconds(timeDiff) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeDiff)));
    }


    static public void setActivityAnimation(Activity activity, int in, int out) {
        try {

            Method method = Activity.class.getMethod(
                    "overridePendingTransition", new Class[] { int.class,
                            int.class });
            method.invoke(activity, in, out);

        } catch (Exception e) {
            // Can't change animation, so do nothing
        }
    }
   
}
