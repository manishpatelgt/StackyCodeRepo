package com.example.stacky;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Webview extends Activity {

	WebView webView;

	LinearLayout back_layout,back_border;
	Button btn_fwd, btn_back_web, btn_reload, btn_stop;
	boolean isProgress = false;
	RelativeLayout web_lay;
	ImageView img;
	String new_url;
	FrameLayout frame;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		webView = new WebView(getApplicationContext());
		frame = (FrameLayout) findViewById(R.id.frame);
		frame.addView(webView);

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);

		//new_url = "https://www.google.com";
		
		//new_url ="http://gastro.hero.ch";
		
		//new_url="http://jobsite.osiztechnologies.com/android/calendar.php";
		
		//new_url="http://www.overclock.net/t/1019761/overclock-net-app/30";
		
		//new_url="https://keep.google.com/";
		
		//new_url="http://m.mysite.com";
		
		//new_url="http://192.168.0.10/Baku/";
		
		new_url="http://www.darpankulkarni.in";
	
		webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setWebChromeClient(new WebChromeClient());
	    webView.loadUrl(new_url);
		
		//webView.loadUrl("file:///android_asset/test.html");
		
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				if (isInternetConnected()) {
					// view.loadUrl(url);
					System.out.println("url called:::" + url);
					if (url.startsWith("tel:")) {
						Intent intent = new Intent(Intent.ACTION_DIAL, Uri
								.parse(url));
						startActivity(intent);
					} else if(url.startsWith("sms:")){
						 String[] str=url.split("body=");
						 //sendSMSMessage(str[1]);
					}
					else if (url.startsWith("http:")
							|| url.startsWith("https:")) {

						/*
						 * Intent intent = new Intent(Intent.ACTION_VIEW, Uri
						 * .parse(url)); startActivity(intent);
						 */

						return false;
					} else if (url.startsWith("mailto:")) {

						String row[] = url.split("mailto:");
						String email_add = row[1];
						

					} else {

						view.loadUrl(url);
						return true;
					}

				} else {
					
				}
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				
			}

		});

	
		webView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {

				super.onProgressChanged(view, newProgress);

			}
		});
	}
	
	 protected void sendSMSMessage(String body) {
	      Log.i("Send SMS", "body: "+body);

	      String phoneNo = "7845854202";
	      
	      try {
	         SmsManager smsManager = SmsManager.getDefault();
	         smsManager.sendTextMessage(phoneNo, null, body, null, null);
	         Toast.makeText(getApplicationContext(), "SMS sent.",
	         Toast.LENGTH_LONG).show();
	      } catch (Exception e) {
	         Toast.makeText(getApplicationContext(),
	         "SMS faild, please try again.",
	         Toast.LENGTH_LONG).show();
	         e.printStackTrace();
	      }
	   }
	 
	public boolean isInternetConnected() {
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		boolean ret = true;
		if (conMgr != null) {
			NetworkInfo i = conMgr.getActiveNetworkInfo();

			if (i != null) {
				if (!i.isConnected()) {
					ret = false;
				}

				if (!i.isAvailable()) {
					ret = false;
				}
			}

			if (i == null)
				ret = false;
		} else
			ret = false;
		return ret;
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		System.gc();
		Runtime.getRuntime().gc();
		super.onDestroy();
	}

}
