package com.example.stacky;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebChromeClient;

@SuppressLint("NewApi")
public class FrTabDescriptionWebView extends Fragment {
	
    public static final String TAG = FrTabDescriptionWebView.class.getName();

    private WebView wv;

    public static FrTabDescriptionWebView newInstance() {
        FrTabDescriptionWebView fragment = new FrTabDescriptionWebView();

        return fragment;
    }

    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public FrTabDescriptionWebView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.activity_prova_webview, null);
        wv = (WebView) v.findViewById(R.id.webview);
        WebSettings settings = wv.getSettings();
        wv.setWebChromeClient(new WebChromeClient() {
        });
        final String mimeType = "text/html";
        final String encoding = "UTF-8";
        String html = getHTML();
        settings.setJavaScriptEnabled(true);
        wv.loadDataWithBaseURL("", html, mimeType, encoding, "");
        
        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
      

    }

    public String getHTML() {
        String html = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/hEFqVV5q44E?rel=0&amp;controls=0&amp;showinfo=0\" frameborder=\"0\" allowfullscreen></iframe>";
        return html;
    }

}