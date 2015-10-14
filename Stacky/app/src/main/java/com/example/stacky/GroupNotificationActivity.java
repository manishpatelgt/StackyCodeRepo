package com.example.stacky;

import java.util.Random;

import android.app.Activity;
import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.View;
import android.widget.Button;

public class GroupNotificationActivity extends Activity{
	
	static String GROUP_KEY_EMAILS = "group_key_emails";
	
	private Button button1;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_grup);
	        
	        button1=(Button)findViewById(R.id.button1);
	        
	        button1.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					   createNotification();
				}
			});
	       
	 }
	 
	 private void createNotification(){
		 
		 Random randomGenerator = new Random();
	     int NOTIFICATION_ID = randomGenerator.nextInt(100);
	     System.out.println("Generated id is: " + NOTIFICATION_ID);
	       
		// Build the notification, setting the group appropriately
		Notification notif = new NotificationCompat.Builder(GroupNotificationActivity.this)
		         .setContentTitle("New mail from " +NOTIFICATION_ID)
		         .setContentText("Hi hello")
		         .setSmallIcon(R.drawable.ic_launcher)
		         //.setGroup(GROUP_KEY_EMAILS)
		         .build();

		// Issue the notification
		NotificationManagerCompat notificationManager =NotificationManagerCompat.from(GroupNotificationActivity.this);
		notificationManager.notify(NOTIFICATION_ID, notif);
		
	 }

}
