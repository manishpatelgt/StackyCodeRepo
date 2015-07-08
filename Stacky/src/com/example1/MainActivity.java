package com.example1;


import java.io.IOException;

import com.example.stacky.R;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity{

    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mPagerAdapter;
    //the images to display
    Integer[] imageIDs = {
    R.drawable.img00,
    R.drawable.img01,
    R.drawable.img0,
    R.drawable.img1,
    R.drawable.img2,
    R.drawable.img3,
    R.drawable.img4,
    R.drawable.img5,
    R.drawable.img6,
    R.drawable.img7,
    R.drawable.img8,
    R.drawable.img9,
    R.drawable.img10,
    R.drawable.img11,
    R.drawable.img12,
    R.drawable.img13,
    R.drawable.img14,
    R.drawable.img15,
    R.drawable.img16,
    R.drawable.img17,
    R.drawable.img18,
    R.drawable.img19,
    R.drawable.img20,
    R.drawable.img22,
    R.drawable.img21
    };
    int height,width;
    WallpaperManager wallpaperManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        mViewPager = (ViewPager) findViewById(R.id.Viewpager);
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        
        final Toast toast = Toast.makeText(getApplicationContext(), "Swipe left and Right!!", Toast.LENGTH_SHORT);
	    toast.show();

	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {
	           @Override
	           public void run() {
	               toast.cancel(); 
	           }
	    }, 3000);
	    
	    DisplayMetrics metrics = new DisplayMetrics(); 
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        height = metrics.heightPixels; 
        width = metrics.widthPixels;
        wallpaperManager = WallpaperManager.getInstance(MainActivity.this); 
        
    }
    
    @Override
    public void onBackPressed(){
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("Exit")
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int id) {
								dialog.dismiss();
								MainActivity.this.finish();
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
		alert.setMessage("Are you sure want to exit?");
	    alert.setIcon(android.R.drawable.ic_dialog_alert);
		alert.show();
		//super.onBackPressed();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.credit:
            	 Intent intent = new Intent(MainActivity.this, Credit.class);
                 startActivity(intent);
                  break;
            case R.id.setAs_new:
            	System.out.println("set as page: "+mPagerAdapter.getpageNumber());
            	
            	/*WallpaperManager myWallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            	try {
            		if(mPagerAdapter.getpageNumber()==1){
            			
            			myWallpaperManager.setResource(R.drawable.img00);
            			
            		}else if(mPagerAdapter.getpageNumber()==2){
            			
            			myWallpaperManager.setResource(R.drawable.img01);
            			
            		}else{
            			myWallpaperManager.setResource(imageIDs[mPagerAdapter.getpageNumber()]);
            		}*/
            	
            	try {
            		
                
                 /*if(mPagerAdapter.getpageNumber()==1){
         			
                	 Bitmap tempbitMap = BitmapFactory.decodeResource(getResources(), R.drawable.img00);
                     Bitmap bitmap = Bitmap.createScaledBitmap(tempbitMap,width,height, true);
                     wallpaperManager.setWallpaperOffsetSteps(1, 1);
                     wallpaperManager.suggestDesiredDimensions(width, height);
                	 wallpaperManager.setBitmap(bitmap);
         			
         		}else if(mPagerAdapter.getpageNumber()==2){
         			Bitmap tempbitMap = BitmapFactory.decodeResource(getResources(), R.drawable.img01);
                    Bitmap bitmap = Bitmap.createScaledBitmap(tempbitMap,width,height, true);
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this); 
                    wallpaperManager.setWallpaperOffsetSteps(1, 1);
                    wallpaperManager.suggestDesiredDimensions(width, height);
         			wallpaperManager.setBitmap(bitmap);
         			
         		}else{
         			Bitmap tempbitMap = BitmapFactory.decodeResource(getResources(),imageIDs[mPagerAdapter.getpageNumber()]);
                    Bitmap bitmap = Bitmap.createScaledBitmap(tempbitMap,width,height, true);
                    WallpaperManager wallpaperManager = WallpaperManager.getInstance(MainActivity.this); 
                    wallpaperManager.setWallpaperOffsetSteps(1, 1);
                    wallpaperManager.suggestDesiredDimensions(width, height);
         			wallpaperManager.setBitmap(bitmap);
         		}*/
                  
                 new SetAsWall(mPagerAdapter.getpageNumber()).execute();
            		
            	} catch (Exception e) {
            	    // TODO Auto-generated catch block
            	    e.printStackTrace();
            	}
            	break;
            case R.id.share:
            	Uri imageUri=null;
            	Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            	sharingIntent.setType("image/jpeg");
            	
            	if(mPagerAdapter.getpageNumber()==1){
            		imageUri = Uri.parse("android.resource://com.example.stacky/"+R.drawable.img00);
                    
            	}else if(mPagerAdapter.getpageNumber()==2){
            		imageUri = Uri.parse("android.resource://com.example.stacky/"+R.drawable.img01);
                    
            	}else{
            		imageUri = Uri.parse("android.resource://com.example.stacky/"+imageIDs[mPagerAdapter.getpageNumber()]);
                    
            	}
            	 Log.i("imageUri",""+imageUri);
            	
            	sharingIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            	startActivity(Intent.createChooser(sharingIntent, "Share photo....."));
            	
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    ProgressDialog progressDialog;
    
    class SetAsWall extends AsyncTask<Void, Void, Bitmap> {
    	 
    	  int Page;
    	  public SetAsWall(int pag){
    		  this.Page=pag;
    	  }
    	  @Override
    	    protected void onPreExecute() {
    	        super.onPreExecute();
    	        progressDialog = new ProgressDialog(MainActivity.this);
    	        progressDialog.setCancelable(true);
    	        progressDialog.setMessage("Setting as wallpaper...");
    	        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	        progressDialog.setProgress(0);
    	        progressDialog.show();
    	    }


        @Override
        protected Bitmap doInBackground(Void... params) {
        	 Bitmap bitmap=null;
        	 if(mPagerAdapter.getpageNumber()==1){
      			
            	 Bitmap tempbitMap = BitmapFactory.decodeResource(getResources(), R.drawable.img00);
                 bitmap = Bitmap.createScaledBitmap(tempbitMap,width,height, true);   	
     		}else if(mPagerAdapter.getpageNumber()==2){
     			Bitmap tempbitMap = BitmapFactory.decodeResource(getResources(), R.drawable.img01);
                bitmap = Bitmap.createScaledBitmap(tempbitMap,width,height, true);
     		}else{
     			Bitmap tempbitMap = BitmapFactory.decodeResource(getResources(),imageIDs[mPagerAdapter.getpageNumber()]);
                 bitmap = Bitmap.createScaledBitmap(tempbitMap,width,height, true);
     		}
        
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressDialog.dismiss();
            final Toast toast = Toast.makeText(getApplicationContext(), "Successfully set wallpaper!!", Toast.LENGTH_SHORT);
    	    toast.show();

    	    Handler handler = new Handler();
    	    handler.postDelayed(new Runnable() {
    	           @Override
    	           public void run() {
    	               toast.cancel(); 
    	           }
    	    }, 3000);
    	    
    	    
           if(bitmap!=null){
        	   try {
        		wallpaperManager.setWallpaperOffsetSteps(1, 1);
                wallpaperManager.suggestDesiredDimensions(width, height);
				wallpaperManager.setBitmap(bitmap);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
           }
          
        }
    }
}
