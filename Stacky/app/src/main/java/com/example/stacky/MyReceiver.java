package com.example.stacky;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
 
public class MyReceiver extends BroadcastReceiver
{
      
    @Override
    public void onReceive(Context context, Intent intent)
    {
       System.out.println("get in MyReceiver: ");	 
       Intent service1 = new Intent(context, MyAlarmService.class);
      // String pushNotification = intent.getExtras().getString("test");
      // service1.putExtra("test", "My Value:");
       context.startService(service1);
        
    }   
}