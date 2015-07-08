package com.example1;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stacky.R;
import com.google.android.gms.internal.po;

@SuppressLint("NewApi")
public class MyFragment extends Fragment{
   private static final String ARG_PAGE = "page";
   private int mPageNumber;
   private TextView mTextView;
   private ImageView img;
   
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

   public static MyFragment newInstance(int pageNumber) {
       MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        System.out.println("in newInstance: "+pageNumber);
        fragment.setArguments(args);
       return fragment;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
           Bundle savedInstanceState) {
	    View root= inflater.inflate(R.layout.calendar_month_grid, container, false);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        System.out.println("in onCreateView: "+mPageNumber);
       // mTextView = (TextView)root.findViewById(R.id.fragmentDataTextView);
       // mTextView.setText(String.valueOf(mPageNumber));
        
        img=(ImageView)root.findViewById(R.id.imageView1);
       
        
        //got Bitmap from Drawable
        
        Bitmap bm = ((BitmapDrawable) getResources().getDrawable(imageIDs[mPageNumber])).getBitmap();
        img.setImageBitmap(bm);
        
        /*int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
        	img.setBackgroundDrawable(getActivity().getResources().getDrawable(imageIDs[mPageNumber]) );
        } else {
        	img.setBackground(getResources().getDrawable(imageIDs[mPageNumber]));
        }*/
        
        return root;
   }

   @Override
   public void onActivityCreated(Bundle savedInstanceState) {
       super.onActivityCreated(savedInstanceState);
   }

   public int getPageNumber() {
       return mPageNumber;
   }   
   
   @Override
   public void onDestroyView(){
	  // if(img!=null){
		   Drawable drawable = img.getDrawable();
		   if (drawable instanceof BitmapDrawable) {
		       BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
		       Bitmap bitmap = bitmapDrawable.getBitmap();
		       bitmap.recycle();
		   }	   
	  // }
	
	   super.onDestroyView();
   }
}