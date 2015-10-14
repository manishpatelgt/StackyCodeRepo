package com.example.stacky;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Receiver for scheduled maintenance actions, e.g. retry failed operations, do regular updates etc.
 */
public class ScheduledMaintenanceReceiver extends BroadcastReceiver {

    public static final String ACTION_SCHEDULED_MAINTENANCE_ALARM = "com.example.stacky.ACTION_SCHEDULED_MAINTENANCE_ALARM";

    public void onReceive(Context context, Intent intent) {
       // if (NetworkHelper.connectedToWiFiOrMobileNetwork(context)) {
            Intent startIntent = new Intent(context, ScheduledMaintenanceService.class);
            context.startService(startIntent);
       // }
    }
}
