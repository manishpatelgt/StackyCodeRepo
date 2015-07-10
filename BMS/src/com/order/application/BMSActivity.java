package com.order.application;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import com.order.bms.R;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

/**
 * Created by Manish on 2/28/2015.
 */
public class BMSActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            System.out.println("get key yeeeee!!!!!");
            finish();
            overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
            return true;
        }
        return false;
    }
    
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        // Use left swipe when user presses hardware back button
        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
    }
}