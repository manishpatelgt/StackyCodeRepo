package com.order.bms.activity;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.order.Utils.Util;
import com.order.application.BMSAapplication;
import com.order.application.Consts;
import com.order.bms.R;
import com.order.data.DataBaseHelper;
import com.order.data.PreferencesHelper;
import com.order.dialogs.ProgressDialogHelper;
import com.order.models.CustomItem;
import com.order.network.NetworkHelper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ConfirmOrderActivity extends Activity {
	
	 private Button buttonOrderNow;
	 private EditText remarkText;
	 private List<CustomItem> items2;
	 private JSONObject finalobject,jsonUser;
	 private DataBaseHelper dbHelper;
	 private String totalAmount,OrderNumber;
	 private int OrderMasterId;
	
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        DisplayMetrics metrics = getResources().getDisplayMetrics();
	        int screenWidth = (int) (metrics.widthPixels*0.98);
	        setContentView(R.layout.dialogfragment);
	        getWindow().setLayout(screenWidth, android.view.WindowManager.LayoutParams.WRAP_CONTENT); 
	        Bundle bundle = getIntent().getExtras();
	        OrderMasterId=bundle.getInt("OrderId");
	    	System.out.println("OrderId: "+OrderMasterId);
	    	OrderNumber=bundle.getString("OrderNumber");
	    	System.out.println("OrderNumber: "+OrderNumber);
	        totalAmount=bundle.getString("totalAmount", totalAmount);
	        setupui();
	    }
	  
	  private void setupui(){
	  
		 	buttonOrderNow=(Button)findViewById(R.id.buttonPlaceOrder);
			remarkText=(EditText)findViewById(R.id.remarkText);
			remarkText.setImeOptions(remarkText.getImeOptions()| EditorInfo.IME_ACTION_DONE);
			items2=BMSAapplication.getInstance().getOrderItems();
			dbHelper=new DataBaseHelper(ConfirmOrderActivity.this);
			
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
					
					try{
						
						  if (!NetworkHelper.connectedToWiFiOrMobileNetwork(ConfirmOrderActivity.this)) {
					          final Toast toast = Toast.makeText(ConfirmOrderActivity.this, ConfirmOrderActivity.this.getResources().getString(R.string.network_must_be_online), Toast.LENGTH_SHORT);
					          toast.show();

					          Handler handler = new Handler();
					          handler.postDelayed(new Runnable() {
					              @Override
					              public void run() {
					                  toast.cancel();
					              }
					          }, 1000);
					          
					      }else{
					    	  if(OrderMasterId!=0){

					    		    jsonUser=new JSONObject();
									jsonUser.put("ordernumber",  OrderNumber);
									jsonUser.put("orderId",  String.valueOf(OrderMasterId));
									jsonUser.put("userId",  String.valueOf(PreferencesHelper.getUserID()));
									jsonUser.put("remark", remarkText.getText().toString());
									jsonUser.put("totalAmount",totalAmount);
									Date now=new Date();
									jsonUser.put("OrderDateTime",Util.getDateFormatted(now));
									finalobject.put("User", jsonUser);

					    	  }else{
					    		  
					    		    jsonUser=new JSONObject();
					    		    jsonUser.put("orderId",  "0");
									jsonUser.put("userId",  String.valueOf(PreferencesHelper.getUserID()));
									jsonUser.put("remark", remarkText.getText().toString());
									jsonUser.put("totalAmount",totalAmount);
									Date now=new Date();
									jsonUser.put("OrderDateTime",Util.getDateFormatted(now));
									finalobject.put("User", jsonUser);
					    		 
					    	  }
					    	  
					    	  System.out.println("final JSON:"+finalobject.toString());
					    	   					    	   	 
					    	   ProgressDialogHelper.initialize(ConfirmOrderActivity.this, "Please wait posting your order...", R.layout.layout_progress_dailog);   
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
	                		
	                		  final Toast toast = Toast.makeText(ConfirmOrderActivity.this, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                		 
	                		 final Toast toast = Toast.makeText(ConfirmOrderActivity.this, "Your order is place successfully.", Toast.LENGTH_SHORT);
		                     toast.show();
		                     
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                         dbHelper.open();
	                         dbHelper.deletetbl_item();
	                         dbHelper.close();
	                         Util.is_finish=true;
	                         finish();
	                       
	                	 }
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(ConfirmOrderActivity.this, "Reponse failed.", Toast.LENGTH_SHORT);
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
	                	  final Toast toast = Toast.makeText(ConfirmOrderActivity.this, "Requested resource not found!!!", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(ConfirmOrderActivity.this, "Something went wrong at server side!!!", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(ConfirmOrderActivity.this, "Unexpected Error occcured!!!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
}
