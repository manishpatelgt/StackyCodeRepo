package com.example1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{

    private static final int NUM_ITEMS = 25;
    private static int Position;
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment item = (Fragment)  MyFragment.newInstance(position);
        return item;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
    
    public int getpageNumber(){
    	return Position;
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
    	Position=position;
    	String str="My Favourite";
    	/*if(position==1){
    		return str;
    	}else{
    		return " "+(position+1);
    	}*/
    	
    	return " "+(position+1);
        
    }
}