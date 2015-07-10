package me.dm7.barcodescanner.zxing.sample;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.barcodescannerdemo.R;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class SimpleScannerActivity extends ActionBarActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private LinearLayout layout;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        //setContentView(R.layout.activity_mine);
        //layout=(LinearLayout)findViewById(R.id.layout);
      
        mScannerView= new ZXingScannerView(this);
       // layout.addView(mScannerView);
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        mScannerView.startCamera();
    }
}
