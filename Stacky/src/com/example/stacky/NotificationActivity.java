package com.example.stacky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class NotificationActivity extends Activity {
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main2);
	        // Launch a scheduled maintenance service
            startService(new Intent(NotificationActivity.this, ScheduledMaintenanceService.class));

	    }
	 
	
}
