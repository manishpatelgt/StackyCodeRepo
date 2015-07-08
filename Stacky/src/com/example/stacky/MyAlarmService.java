package com.example.stacky;

import java.util.Random;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
                            
 
public class MyAlarmService extends Service 
{
      
   private NotificationManager mManager;
 
    @Override
    public IBinder onBind(Intent arg0)
    {
       // TODO Auto-generated method stub
        return null;
    }
 
    @Override
    public void onCreate() 
    {
       // TODO Auto-generated method stub  
       super.onCreate();
    }
 
  @Override
   public void onStart(Intent intent, int startId)
   {
       super.onStart(intent, startId);
      
       //String pushNotification = intent.getExtras().getString("test");
       /*mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
       Intent intent1 = new Intent(this.getApplicationContext(),MainActivity2.class);
     
       Notification notification = new Notification(R.drawable.ic_launcher,"This is a test message!", System.currentTimeMillis());
       intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
 
       PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
       notification.flags |= Notification.FLAG_AUTO_CANCEL;
       notification.setLatestEventInfo(this.getApplicationContext(), "AlarmManagerDemo", "This is a test message!", pendingNotificationIntent);
 
       Random randomGenerator = new Random();
       int randomInt = randomGenerator.nextInt(100);
       System.out.println("Generated is is: " + randomInt);
       mManager.notify(randomInt, notification);*/
       
       NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

       Intent intent1 = new Intent(this.getApplicationContext(),MainActivity2.class);
       Bundle bundle = new Bundle();
       bundle.putString("title", "My data from Notification.");
       
       //Notification notification = new Notification(R.drawable.ic_launcher,"", System.currentTimeMillis());
       //intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
       intent1.putExtras(bundle);
       
      // PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(),0, intent1,PendingIntent.FLAG_UPDATE_CURRENT);
       PendingIntent pendingNotificationIntent = PendingIntent.getActivity(this.getApplicationContext(),0, intent1,0);
       
       Random randomGenerator = new Random();
       int NOTIFICATION_ID = randomGenerator.nextInt(100);
       System.out.println("Generated id is: " + NOTIFICATION_ID);
       
       long[] pattern = {500,500,500,500,500,500,500,500,500};
       
      	NotificationCompat.Builder mBuilder = 
   		new NotificationCompat.Builder(getApplicationContext())
      	 .setContentIntent(pendingNotificationIntent)
         .setVibrate(pattern)
         .setContentTitle("This is a test message!")
         .setSmallIcon(R.drawable.ic_launcher)
         .setContentTitle("NotificationID is: "+NOTIFICATION_ID)
         .setWhen(System.currentTimeMillis())
         .setAutoCancel(true)
   		 .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
     
     	mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
     	
    }
 
    @Override
    public void onDestroy() 
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }
 
}