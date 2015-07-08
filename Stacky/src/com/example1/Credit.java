package com.example1;


import com.example.stacky.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;


public class Credit extends Activity {

	WebView webView;
	FrameLayout frame;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

		webView = new WebView(getApplicationContext());
		frame = (FrameLayout) findViewById(R.id.frame);
		frame.addView(webView);

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setDomStorageEnabled(true);
		
		String sHtml="<html><head></head><body style=\"text-align:center\" bgcolor=\"#F5F5DC\"><h1>Credit goes to</h1><img src=\"file:///android_asset/md.png\"><h3>Manish Aka MD</h3></body></html>";
		webView.loadDataWithBaseURL(null,sHtml, "text/html", "UTF-8", null);
		//webView.loadUrl("file:///android_asset/about.html");
     }

}
