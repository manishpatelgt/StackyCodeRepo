package com.order.parsing;

import org.json.JSONObject;
import android.text.TextUtils;
import android.widget.Toast;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.order.application.Consts;
import com.order.bms.activity.Login;
import com.order.dialogs.ProgressDialogHelper;
import com.order.models.MobileUser;

public class CustomerData {
	RequestParams params;
	int errorCode;
	
	public CustomerData(RequestParams params1){
		this.params=params1;
	}
	
	 public int invokeWS(){
	        
	        // Make RESTful webservice call using AsyncHttpClient object
	         AsyncHttpClient client = new AsyncHttpClient();
	         
	         client.get(Consts.WEBSERVICE_URL2,params ,new AsyncHttpResponseHandler() {
	             // When the response returned by REST has Http response code '200'
	             @Override
	             public void onSuccess(String response) {
	                 try {
	                	 System.out.println("response: "+response);
	                	 
	                	 if(TextUtils.isEmpty(response)){
	                		
	                		
	                          
	                	 }else{
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                     	 }
	                	
	                	
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     errorCode=100;
	                 }
	             }
	             
	             // When the response returned by REST has Http response code other than '200'
	             @Override
	             public void onFailure(int statusCode, Throwable error,String content) {
	                
	            	 
	                 // When Http response code is '404'
	                 if(statusCode == 404){
	                	 errorCode=404;
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	 errorCode=500;
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 errorCode=200;
	                 }
	             }
	         });
	         
	     return errorCode;    
	    }
	
}
