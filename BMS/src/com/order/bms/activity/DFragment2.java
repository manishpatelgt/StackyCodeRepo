package com.order.bms.activity;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.order.bms.R;
 
public class DFragment2 extends DialogFragment {
	
	 private Button buttonOk;
	 private TextView detailsTextView;
	 
	 public static DFragment2 newInstance(String uName,String fName,String role,String phoneNumber) {
		    DFragment2 fragment = new DFragment2();

	        Bundle bundle = new Bundle();
	        bundle.putString("uName", uName);
	        bundle.putString("fName", fName);
	        bundle.putString("role", role);
	        bundle.putString("phoneNumber", phoneNumber);
	        fragment.setArguments(bundle);

	        return fragment;
	    }
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.details, container,
				false);
		getDialog().setTitle("Customer Details");
		
		 //message.setText(getArguments().getString("message"));
		
		buttonOk=(Button)rootView.findViewById(R.id.buttonOk);
		detailsTextView = (TextView) rootView.findViewById(R.id.detailTextView);
		
		SpannableStringBuilder summary = new SpannableStringBuilder();
        SpannableString uNameSpan, fNameSpan, uRoleSpan,phoneNoSpan;
        
        String uNametxt = " "+getArguments().getString("uName");
        uNameSpan = new SpannableString(uNametxt);
        uNameSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, uNametxt.length(), 0);
        uNameSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, uNametxt.length(), 0);
        uNameSpan.setSpan(new RelativeSizeSpan(1.5f), 0, uNametxt.length(), 0);
        
        String fNametxt = " "+getArguments().getString("fName");
        fNameSpan = new SpannableString(fNametxt);
        fNameSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, fNametxt.length(), 0);
        fNameSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, fNametxt.length(), 0);
        fNameSpan.setSpan(new RelativeSizeSpan(1.5f), 0, fNametxt.length(), 0);
        
        String uRoletxt = " "+getArguments().getString("role");
        uRoleSpan = new SpannableString(uRoletxt);
        uRoleSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, uRoletxt.length(), 0);
        uRoleSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, uRoletxt.length(), 0);
        uRoleSpan.setSpan(new RelativeSizeSpan(1.5f), 0, uRoletxt.length(), 0);
        
        String phoneNoxt = " "+getArguments().getString("phoneNumber");
        phoneNoSpan = new SpannableString(phoneNoxt);
        phoneNoSpan.setSpan(new StyleSpan(Typeface.BOLD), 0, phoneNoxt.length(), 0);
        phoneNoSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, phoneNoxt.length(), 0);
        phoneNoSpan.setSpan(new RelativeSizeSpan(1.5f), 0, phoneNoxt.length(), 0);
        
        summary.append("UserName: ").append(uNameSpan).append("\n\n")
        .append("FirstName: ").append(fNameSpan).append("\n\n")
        .append("User Role: ").append(uRoleSpan).append("\n\n")
        .append("Phone No: ").append(phoneNoSpan).append("\n");
        
         detailsTextView.setText(summary, TextView.BufferType.SPANNABLE);    
		 buttonOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			dismiss();	
			}
		});
		
				
	
		return rootView;
	}
}