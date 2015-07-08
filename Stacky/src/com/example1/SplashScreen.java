package com.example1;

import java.lang.reflect.Method;

import com.example.stacky.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class SplashScreen extends Activity{

	 private final int SPLASH_DISPLAY_LENGHT = 3000;
	 ProgressDialog pd;
	 
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.splash);
	       
	        pd = new ProgressDialog(SplashScreen.this, R.style.MyTheme);
			pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
			pd.setCancelable(false);
			pd.show();
			
	    	new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					    pd.dismiss();
						Intent i = new Intent(getApplicationContext(),MainActivity.class);
						startActivity(i);
						finish();
						setActivityAnimation(SplashScreen.this,
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
	 
	 static public void setActivityAnimation(Activity activity, int in, int out) {
			try {

				Method method = Activity.class.getMethod(
						"overridePendingTransition", new Class[] { int.class,
								int.class });
				method.invoke(activity, in, out);

			} catch (Exception e) {
				// Can't change animation, so do nothing
			}
		}
	 
}
