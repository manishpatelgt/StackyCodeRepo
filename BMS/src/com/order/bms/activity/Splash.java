package com.order.bms.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.order.Utils.Util;
import com.order.application.Consts;
import com.order.bms.R;
import com.order.data.DBAdapter;
import com.order.data.DataBaseHelper;
import com.order.data.ImportDatabase;
import com.order.data.PreferencesHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class Splash extends Activity{

    private final int SPLASH_DISPLAY_LENGHT = 2000;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        
        DataBaseHelper dbHelper=new DataBaseHelper(Splash.this);
		dbHelper.createDataBase();
		
        
        /*if(PreferencesHelper.getFirstTime()){
        	 try {
     			File f = new File(Consts.APPLICATION_DATABASES_PATH+"order");

     			if (!f.exists()) {
     				InputStream databaseInputStream1;
     				databaseInputStream1 = getAssets().open("order");

     				DBAdapter db = new DBAdapter(this);
     				db.open();
     				db.close();

     				ImportDatabase ipd = new ImportDatabase(databaseInputStream1);
     				ipd.copyDataBase();
     				System.out.println("Database copied");

     			} else {
     				System.out.println("Database file already exist");
     			}
     		} catch (Exception e) {
     			// TODO: handle exception
     			e.printStackTrace();
     		}
        	 PreferencesHelper.setFirstTime(false);
        }*/
       
	
        pd = new ProgressDialog(Splash.this, R.style.MyTheme);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.setCancelable(false);
        pd.show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    pd.dismiss();
                    Intent i = new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                    finish();
                    Util.setActivityAnimation(Splash.this,
                            R.anim.fade_in, R.anim.fade_out);

                }
            }, SPLASH_DISPLAY_LENGHT);


    }
    @Override
    public void onStop(){
        if(pd!=null && pd.isShowing()){
            pd.dismiss();
            pd=null;
        }
        super.onStop();
    }

}
