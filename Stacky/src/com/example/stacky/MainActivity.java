package com.example.stacky;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	public String DATE_yyyy_MM_dd_hh_mm_ss = "yyyy-MM-dd hh:mm:ss";
	public String DATE_yyyy_MM_dd_HH_mm_ss  = "yyyy-MM-dd HH:mm:ss";  // 1970-01-01 00:00
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_new);
        
        Date bootDate = new Date();
        
        TextView tv=(TextView)findViewById(R.id.textView);
        
        tv.append("\n in 12-hour format: "+getDateFormatted(bootDate));
        
        //tv.append("\n in 24-hour format: "+getDateFormatted2(bootDate));
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String  sd= sdf.format(new Date());
        
        tv.append("\n in 24-hour format: "+sd);
        
       /* tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
        */
       // showdialog();
    }

    public String getDateFormatted(Date date){
    	return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_hh_mm_ss, date));
    }
    
    public String getDateFormatted2(Date date){
    	return String.valueOf(DateFormat.format(DATE_yyyy_MM_dd_HH_mm_ss, date));
    }
    
   /* public void showtoast(View v)
    {
        Button b = (Button)v;
        String text = b.getText().toString();
        Toast.makeText(getApplicationContext(), "button clicked is" + text, Toast.LENGTH_SHORT).show();
    }*/

}