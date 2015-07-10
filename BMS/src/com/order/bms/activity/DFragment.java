package com.order.bms.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.*;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.order.Utils.Util;
import com.order.adapters.CustomerAdapter;
import com.order.application.BMSAapplication;
import com.order.application.Consts;
import com.order.bms.R;
import com.order.data.DataBaseHelper;
import com.order.data.PreferencesHelper;
import com.order.dialogs.ProgressDialogHelper;
import com.order.models.CustomItem;
import com.order.models.Customer;
import com.order.network.NetworkHelper;
 
public class DFragment extends DialogFragment {
	
	 private Button buttonOrderNow;
	 private EditText remarkText;
	 private List<CustomItem> items2;
	 private JSONObject finalobject,jsonUser;
	 private Context mContext;
	 private DataBaseHelper dbHelper;
	 
	 public static DFragment newInstance(String totalAmount) {
		 DFragment fragment = new DFragment();

	        Bundle bundle = new Bundle();
	        bundle.putString("totalAmount", totalAmount);
	        //bundle.putParcelableArrayListExtra("cars", _list);
	        fragment.setArguments(bundle);

	        return fragment;
	    }
	 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.dialogfragment, container,
				false);
		getDialog().setTitle("Place your Order");
		mContext=getActivity().getApplicationContext();
		buttonOrderNow=(Button)rootView.findViewById(R.id.buttonPlaceOrder);
		remarkText=(EditText)rootView.findViewById(R.id.remarkText);
		remarkText.setImeOptions(remarkText.getImeOptions()| EditorInfo.IME_ACTION_DONE);
		items2=BMSAapplication.getInstance().getOrderItems();
		dbHelper=new DataBaseHelper(getActivity());
		try {
	    
	    JSONArray jsonArray = new JSONArray();
		
		for(int i=0;i<items2.size();i++){
			
			    JSONObject obj = new JSONObject();
				obj.put("itemid", String .valueOf(items2.get(i).getItemId()));
				obj.put("itemname",  items2.get(i).getItemName());
				obj.put("itemPrice", items2.get(i).getItemPrice());
				obj.put("itemQuntity", items2.get(i).getItemQuantity());
				obj.put("itemAmount", items2.get(i).getItemAmount());
				obj.put("addedDateTime", items2.get(i).getItemaddedDate());
				obj.put("createdBy", items2.get(i).getCreatedBy());
				obj.put("Pack", items2.get(i).getItemPack());
				obj.put("ColorCode", items2.get(i).getItemColorCode());
				jsonArray.put(obj);
		   }
		
		    finalobject = new JSONObject();
			finalobject.put("items", jsonArray);
			System.out.println("final JSON:"+finalobject.toString());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		buttonOrderNow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			dismiss();	
			
			try{
				
				  if (!NetworkHelper.connectedToWiFiOrMobileNetwork(getActivity())) {
			          final Toast toast = Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.network_must_be_online), Toast.LENGTH_SHORT);
			          toast.show();

			          Handler handler = new Handler();
			          handler.postDelayed(new Runnable() {
			              @Override
			              public void run() {
			                  toast.cancel();
			              }
			          }, 1000);
			          
			      }else{
			    	    jsonUser=new JSONObject();
						jsonUser.put("userId",  String.valueOf(PreferencesHelper.getUserID()));
						jsonUser.put("remark", remarkText.getText().toString());
						jsonUser.put("totalAmount", getArguments().getString("totalAmount"));
						Date now=new Date();
						jsonUser.put("OrderDateTime",Util.getDateFormatted(now));
						finalobject.put("User", jsonUser);
			    	   	 
			    	   ProgressDialogHelper.initialize(getActivity(), "Please wait posting your order...", R.layout.layout_progress_dailog);   
			           // Invoke RESTful Web Service with Http parameters
			    	   RequestParams params = new RequestParams();
			           params.put("data", String.valueOf(finalobject));
			           invokeWS(params);
			      }
				  
			}catch(Exception e){
				e.printStackTrace();
			}
			
			}
		});
		
		return rootView;
	}
	
	  public void invokeWS(RequestParams params){
	        
	        // Make RESTful webservice call using AsyncHttpClient object
	         AsyncHttpClient client = new AsyncHttpClient();
	         System.out.println("calling: "+Consts.WEBSERVICE_URL6+" params: "+params.toString());
	        
	         client.get(Consts.WEBSERVICE_URL6,params ,new AsyncHttpResponseHandler() {
	             // When the response returned by REST has Http response code '200'
	             @Override
	             public void onSuccess(int statusCode, String response) {
	                 // Hide Progress Dialog
	                ProgressDialogHelper.dismiss();
	                 try {
	                	 System.out.println("response: "+response);
	                	 
	                	 if(TextUtils.isEmpty(response)){
	                		
	                		  final Toast toast = Toast.makeText(mContext, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                		 
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                         dbHelper.open();
	                         dbHelper.deletetbl_item();
	                         dbHelper.close();
	                       
	                	 }
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(mContext, "Reponse failed.", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	             
	             // When the response returned by REST has Http response code other than '200'
	             @Override
	             public void onFailure(int statusCode, Throwable error,String content) {
	                
	            	 // Avoid leaking the progress dialog
	                 ProgressDialogHelper.dismiss();
	                 
	                 // When Http response code is '404'
	                 if(statusCode == 404){
	                	  final Toast toast = Toast.makeText(mContext, "Requested resource not found!!!", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(mContext, "Something went wrong at server side!!!", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(mContext, "Unexpected Error occcured!!!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
	    
	
	
}