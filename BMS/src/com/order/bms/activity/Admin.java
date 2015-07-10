package com.order.bms.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.order.bms.R;
import com.order.data.PreferencesHelper;

public class Admin extends Activity {

	  private TextView textUsername;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.admin);
	        setupui();
	    }

	    private void setupui(){
	      textUsername=(TextView)findViewById(R.id.textuserName);
	      textUsername.setText(PreferencesHelper.getUserName());
	    }
	    
	    @Override
	    public void onBackPressed(){
	        //super.onBackPressed();
	    }
	    
	    public void mainMenuButtonClick(View v) {
	        switch (v.getId()) {
	            case R.id.gotoOrderButton:
	            Intent i=new Intent(Admin.this,PlaceOrder2.class);
	            startActivity(i);
	                break;
	            case R.id.gototodayorderebutton:
	           	 Intent i2=new Intent(Admin.this,TodayOrderListActivity.class);
            	 startActivity(i2);
	                break;
	            case R.id.gotopendingorderebutton:
	            	 Intent i23=new Intent(Admin.this,PendingOrderListActivity.class);
	            	 startActivity(i23);
	                break;
	            case R.id.allorderbutton:
	            Intent i22=new Intent(Admin.this,AllOrderListActivity.class);
	            startActivity(i22);
	                break;
	            case R.id.logoutbutton:
	                resetSystem();
	                break;
	        }
	        overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
	    }
	  
	    private void resetSystem() {

	        AlertDialog.Builder builder = new AlertDialog.Builder(Admin.this);
	        builder.setTitle(getResources().getString(R.string.sign_out_caps))
	                .setPositiveButton("Yes",
	                        new DialogInterface.OnClickListener() {
	                            @Override
	                            public void onClick(DialogInterface dialog,
	                                                int id) {
	                                resetsystem();
	                                dialog.dismiss();
	                                startActivity(new Intent(Admin.this, Login.class));
	                                finish();
	                                overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
	                            }
	                        });
	        builder.setNegativeButton("No",
	                new DialogInterface.OnClickListener() {

	                    @Override
	                    public void onClick(DialogInterface dialog, int which) {
	                        dialog.dismiss();
	                    }
	                });

	        AlertDialog alert = builder.create();
	        alert.setMessage("Are you sure want to logout?");
	        alert.setIcon(android.R.drawable.ic_dialog_alert);
	        alert.show();
	    }

	    private void resetsystem(){
	        PreferencesHelper.setLoginCheck(false);
	        PreferencesHelper.setUserID(-1);
	        PreferencesHelper.setRollId(-1);
	        PreferencesHelper.setUserRole(null);
	        PreferencesHelper.setUserName(null);
	    }
	}
