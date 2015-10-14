/*
 * Copyright (C) 2014 GlobeTech Ltd.
 *
 * Unauthorised usage or distribution strictly prohibited.
 * You may not use this file except in compliance with the License.
 * Portions copyright 2012, Google, Inc.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.stacky;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**
 * Do some regular maintenance jobs, e.g. remove stale photos, re-try failed uploads, etc.
 */
public class ScheduledMaintenanceService extends IntentService {

    public static final int PENDING_REQUEST_CODE = 54321;
    private AlarmManager alarmManager;
    private PendingIntent startAlarmPendingIntent;
    private NotificationManager myNotificationManager;
    private int notificationIdOne = 111;
    private int numMessagesOne = 0;
    long lastTime=0;
    
    public ScheduledMaintenanceService() {
        super("ScheduledMaintenanceService");
    }

    public ScheduledMaintenanceService(String name) {
        super(name);
    }

    @Override
    public void onCreate() {
        super.onCreate();

      
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
       
        // Setup the pending action to fire when the alarm fires
        Intent intentToFire = new Intent(ScheduledMaintenanceReceiver.ACTION_SCHEDULED_MAINTENANCE_ALARM);
        startAlarmPendingIntent = PendingIntent.getBroadcast(this, PENDING_REQUEST_CODE, intentToFire, 0);
    }

    protected void displayNotificationOne() {

    	  System.out.println("timing is: "+(System.currentTimeMillis()-lastTime)/1000+" Sec..");
    	  lastTime=System.currentTimeMillis();
	      // Invoking the default notification service
	      NotificationCompat.Builder  mBuilder = new NotificationCompat.Builder(this);	
	 
	      mBuilder.setContentTitle("New Message with Alarm Manager");
	      mBuilder.setContentText("ScheduledMaintenanceService run!!!");
	      mBuilder.setTicker("New Message Received!");
	      mBuilder.setSmallIcon(R.drawable.ic_launcher);

	      // Increase notification number every time a new notification arrives 
	      mBuilder.setNumber(++numMessagesOne);
	      
	      // Creates an explicit intent for an Activity in your app 
	      Intent resultIntent = new Intent(this, NotificationOne.class);
	      resultIntent.putExtra("notificationId", notificationIdOne);

	      //This ensures that navigating backward from the Activity leads out of the app to Home page
	      TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	      // Adds the back stack for the Intent
	      stackBuilder.addParentStack(NotificationOne.class);

	      // Adds the Intent that starts the Activity to the top of the stack
	      stackBuilder.addNextIntent(resultIntent);
	      PendingIntent resultPendingIntent =
	         stackBuilder.getPendingIntent(
	            0,
	            PendingIntent.FLAG_ONE_SHOT //can only be used once
	         );
	      // start the activity when the user clicks the notification text
	      mBuilder.setContentIntent(resultPendingIntent);

	      myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

	      // pass the Notification object to the system 
	      myNotificationManager.notify(notificationIdOne, mBuilder.build());
	   }
    /**
     * Reduce battery impact by waiting until the phone is no longer in standby mode before the alarm triggers
     */
    @Override
    protected void onHandleIntent(Intent intent) {
    	  System.out.println("got Intent ");
    	  
         alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime() +25000,
                25000,
                startAlarmPendingIntent);
         
        //lastTime=System.currentTimeMillis();
        
        displayNotificationOne();

    }

}
