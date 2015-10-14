package com.example.stacky;

import java.util.Calendar;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
 
public class MainActivity2 extends Activity 
{
 
    private PendingIntent pendingIntent;
     
    @Override
    public void onCreate(Bundle savedInstanceState) 
     {
       
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main_new);
     
      
      Bundle b=getIntent().getExtras();
      
      if(b!=null && !b.isEmpty()){
    	 System.out.println("Yuppyyyy i got notificaction data: "+b.getString("title")); 
      }
      
      Calendar calendar = Calendar.getInstance();
      calendar.set(Calendar.MONTH, 5);
      calendar.set(Calendar.YEAR, 2015);
      calendar.set(Calendar.DAY_OF_MONTH, 24);

      calendar.set(Calendar.HOUR_OF_DAY, 14);
      calendar.set(Calendar.MINUTE, 30);
      calendar.set(Calendar.SECOND, 0);
      calendar.set(Calendar.AM_PM, Calendar.PM);
      //calendar.set(2014, Calendar.JUNE, 24, 13, 05, 0);
     
      int id = (int) System.currentTimeMillis();
      Intent myIntent = new Intent(MainActivity2.this, MyReceiver.class);
      //myIntent.putExtra("test", "My Value:");
      //pendingIntent = PendingIntent.getBroadcast(MainActivity2.this, 0, myIntent,PendingIntent.FLAG_UPDATE_CURRENT);
      pendingIntent = PendingIntent.getBroadcast(MainActivity2.this, id, myIntent,0);
      
      AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
      alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
      
      /*Calendar calendar2 = Calendar.getInstance();
      
      calendar2.set(2014, Calendar.JUNE, 24, 13, 10, 0);
     
      Intent myIntent2 = new Intent(MainActivity2.this, MyReceiver.class);
      PendingIntent pendingIntent2 = PendingIntent.getBroadcast(MainActivity2.this, id, myIntent2,PendingIntent.FLAG_UPDATE_CURRENT);
     
      AlarmManager alarmManager2 = (AlarmManager)getSystemService(ALARM_SERVICE);
      alarmManager2.set(AlarmManager.RTC, calendar2.getTimeInMillis(), pendingIntent2);*/
    
    } //end onCreate
}