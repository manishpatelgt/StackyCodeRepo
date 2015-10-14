package com.example.stacky;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class WebView2 extends Activity {
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		FrTabDescriptionWebView ft=new FrTabDescriptionWebView();	
	    android.app.FragmentTransaction fr = getFragmentManager().beginTransaction();
	    fr.add(R.id.frame, ft);
	    fr.commit();
	    //Intent i=new Intent(WebView2.this,LocService.class);
	    //startService(i);
	    
	    Intent i2=new Intent(WebView2.this,MyService.class);
	    startService(i2);
	}
}
