package com.order.bms.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.order.Utils.Util;
import com.order.adapters.MyCustomAdapter;
import com.order.adapters.OrderAdapter;
import com.order.application.Consts;
import com.order.bms.R;
import com.order.data.PreferencesHelper;
import com.order.dialogs.ProgressDialogHelper;
import com.order.models.Order;
import com.order.models.Stages;
import com.order.network.NetworkHelper;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
		
public class TodayOrderListActivity extends ActionBarActivity{
	
	 private ListView _list;
	 private List<Order> items;
	 private OrderAdapter  adapter;
	 private LayoutInflater inflater;
	 private List<Stages> items2;
	 private ListView listView;
	 private MyCustomAdapter dataAdapter;
	 
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_order_list);
	        setupui();
	         inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);   
	    }
	  
	  private void setupui(){
		  _list=(ListView)findViewById(R.id.list);
		   items=new ArrayList<Order>();
		  
		  if (!NetworkHelper.connectedToWiFiOrMobileNetwork(TodayOrderListActivity.this)) {
              final Toast toast = Toast.makeText(TodayOrderListActivity.this, getResources().getString(R.string.network_must_be_online), Toast.LENGTH_SHORT);
              toast.show();

              Handler handler = new Handler();
              handler.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      toast.cancel();
                  }
              }, 1000);
              
          }else{
        	  
        	   ProgressDialogHelper.initialize(TodayOrderListActivity.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
               // Invoke RESTful Web Service with Http parameters
        	   RequestParams params = new RequestParams();
        	   Date noew=new Date();
               params.put("userId", String.valueOf(PreferencesHelper.getUserID()));
               params.put("date", Util.getDateFormatted2(noew));
               invokeWS(params);
          }
	  }
	  
	
	  @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
	      getMenuInflater().inflate(R.menu.search_menu, menu);
	      return true;
	   }
	  
	  
	  @Override
	    public boolean onOptionsItemSelected(MenuItem item) {

		  if (item.getItemId() == R.id.action_search) {

			    items2=new ArrayList<Stages>();
		  	    ProgressDialogHelper.initialize(TodayOrderListActivity.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
		        // Invoke RESTful Web Service with Http parameters
			    RequestParams params = new RequestParams();
		        params.put("userId", String.valueOf(PreferencesHelper.getUserID()));
		        invokeWS2(params);
		       
		  	  // ShowDialog();
			  return true;
		  }
		  if (item.getItemId() == R.id.action_clear) {
			  items.clear();
			  if (!NetworkHelper.connectedToWiFiOrMobileNetwork(TodayOrderListActivity.this)) {
	              final Toast toast = Toast.makeText(TodayOrderListActivity.this, getResources().getString(R.string.network_must_be_online), Toast.LENGTH_SHORT);
	              toast.show();

	              Handler handler = new Handler();
	              handler.postDelayed(new Runnable() {
	                  @Override
	                  public void run() {
	                      toast.cancel();
	                  }
	              }, 1000);
	              
	          }else{
	        	  
	        	  ProgressDialogHelper.initialize(TodayOrderListActivity.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
	               // Invoke RESTful Web Service with Http parameters
	        	   RequestParams params = new RequestParams();
	        	   Date noew=new Date();
	               params.put("userId", String.valueOf(PreferencesHelper.getUserID()));
	               params.put("date", Util.getDateFormatted2(noew));
	               invokeWS(params);
	          }
			  return true;
		  }
	        if (item.getItemId() == android.R.id.home) {
	            System.out.println("get key yeeeee!!!!!");
	            finish();
	            overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
	            return true;
	        }
	        return false;
	    }
	  
	  
		JSONObject finalobject;
	    private void ShowDialog(){
	    	
	    	AlertDialog.Builder alertdia = new AlertDialog.Builder(TodayOrderListActivity.this);
	    	View rootView = inflater.inflate(R.layout.layout_stage, null,false);
	  	    alertdia.setView(rootView);
	  	    alertdia.setTitle("Search Order by");
	  	    
	  	    listView = (ListView) rootView.findViewById(R.id.list);
	  	  
	  	    dataAdapter = new MyCustomAdapter(TodayOrderListActivity.this,R.layout.layout_stages, items2);  
            // Assign adapter to ListView
            listView.setAdapter(dataAdapter);
          
	  	    alertdia.setPositiveButton("Search", new DialogInterface.OnClickListener() {
	  	    public void onClick(DialogInterface dialog, int whichButton) {
	  	      dialog.dismiss();
	  	          
	          try{
	        	  
	        	  finalobject = new JSONObject();
	              JSONArray jsonArray = new JSONArray();
	              
	      	      for(int i=0;i<items2.size();i++){
	                  Stages co_eng = items2.get(i);
	                  if(co_eng.isSelected()){
	                      JSONObject obj = new JSONObject();
	    				  obj.put("StageId", co_eng.getId());
	    				  jsonArray.put(obj);
	                  }
	              }
	      	      
	      	    finalobject.put("SelectedStage", jsonArray);
	      		System.out.println("final JSON:"+finalobject.toString());
	      		
	  	    	ProgressDialogHelper.initialize(TodayOrderListActivity.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
	  	        // Invoke RESTful Web Service with Http parameters
	  		    RequestParams params = new RequestParams();
	  	        params.put("data", finalobject.toString());
	  	        invokeWS3(params);
	      		
	          }catch(Exception e){
	        	  e.printStackTrace();
	          }
	         }
	  	    });

	  	    alertdia.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	  	      public void onClick(DialogInterface dialog, int whichButton) {
	  	        // what ever you want to do with No option.
	  	    	  dialog.dismiss();
	  	      }
	  	    });

	  	    alertdia.show();	
	    }
	  @Override
	    public void onBackPressed(){
	        super.onBackPressed();
	        // Use left swipe when user presses hardware back button
	        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
	    }
	  
	  public void invokeWS(RequestParams params){
	        
	        // Make RESTful webservice call using AsyncHttpClient object
	         AsyncHttpClient client = new AsyncHttpClient();
	         System.out.println("calling: "+Consts.WEBSERVICE_URL7+" params: "+params.toString());
	        
	         client.get(Consts.WEBSERVICE_URL7,params ,new AsyncHttpResponseHandler() {
	             // When the response returned by REST has Http response code '200'
	             @Override
	             public void onSuccess(String response) {
	                 // Hide Progress Dialog
	                ProgressDialogHelper.dismiss();
	                 try {
	                	 System.out.println("response: "+response);
	                	 
	                	 if(TextUtils.isEmpty(response)){
	                		
	                		  final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                		 
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                         JSONArray ja =(JSONArray) obj.getJSONArray("aaData");

	                         for (int i = 0; i < ja.length(); i++) {
	             				
	             				final JSONObject jo2 = (JSONObject) ja.get(i);
	             				items.add(new Order(
	             						jo2.getInt("orderMasterId"),
	             						jo2.getString("ordernumber"), 
	             						jo2.getString("orderAmount"), 
	             						jo2.getString("state"), 
	             						jo2.getString("remark"),
	             						"",
	             						//jo2.getString("customerName"), 
	             						jo2.getString("UpdatedBy"), 
	             						jo2.getString("UpdatedDTTM"), 
	             						jo2.getString("createdBy"), 
	             						"",
	             						//jo2.getString("orderstage"),
	             						jo2.getString("CreatedDate")));
	             	        }
	                	 }
	                	 
	                   adapter=new OrderAdapter(TodayOrderListActivity.this, R.layout.order_list_item, items);
	           	      _list.setAdapter(adapter);
	                	
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Reponse failed.", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	             
	             // When the response returned by REST has Http response code other than '200'
	             @Override
	             public void onFailure(int statusCode, Throwable error,String content) {
	                
	            	 // Avoid leaking the progress dialog
	                 ProgressDialogHelper.dismiss();
	                 System.out.println("statusCode: "+statusCode);
	                 
	                 // When Http response code is '404'
	                 if(statusCode == 404){
	                	  final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Requested resource not found", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
	  
	  public void invokeWS2(RequestParams params){
	        
	        // Make RESTful webservice call using AsyncHttpClient object
	         AsyncHttpClient client = new AsyncHttpClient();
	         System.out.println("calling: "+Consts.WEBSERVICE_URL8+" params: "+params.toString());
	        
	         client.get(Consts.WEBSERVICE_URL8,params ,new AsyncHttpResponseHandler() {
	             // When the response returned by REST has Http response code '200'
	             @Override
	             public void onSuccess(String response) {
	                 // Hide Progress Dialog
	                ProgressDialogHelper.dismiss();
	                 try {
	                	 System.out.println("response: "+response);
	                	 
	                	 if(TextUtils.isEmpty(response)){
	                		
	                		  final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                		 
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                         JSONArray ja =(JSONArray) obj.getJSONArray("aaData");
                       
	                         for (int i = 0; i < ja.length(); i++) {
	             				
	             				final JSONObject jo2 = (JSONObject) ja.get(i);
	             			
	             			    items2.add(new Stages(jo2.getInt("orderstagemasterid"),jo2.getString("orderstagename"), true));
	             				
	             	        }
	                	 }
	             
	                  ShowDialog();
	                	 
	                 	
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Reponse failed.", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	             
	             // When the response returned by REST has Http response code other than '200'
	             @Override
	             public void onFailure(int statusCode, Throwable error,String content) {
	                
	            	 // Avoid leaking the progress dialog
	                 ProgressDialogHelper.dismiss();
	                 System.out.println("statusCode: "+statusCode);
	                 
	                 // When Http response code is '404'
	                 if(statusCode == 404){
	                	  final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Requested resource not found", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
	  
	  public void invokeWS3(RequestParams params){
	        
	        // Make RESTful webservice call using AsyncHttpClient object
	         AsyncHttpClient client = new AsyncHttpClient();
	         System.out.println("calling: "+Consts.WEBSERVICE_URL11+" params: "+params.toString());
	        
	         client.get(Consts.WEBSERVICE_URL11,params ,new AsyncHttpResponseHandler() {
	             // When the response returned by REST has Http response code '200'
	             @Override
	             public void onSuccess(String response) {
	                 // Hide Progress Dialog
	                ProgressDialogHelper.dismiss();
	                 try {
	                	 System.out.println("response: "+response);
	                	 
	                	 if(TextUtils.isEmpty(response)){
	                		
	                		  final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                		 
	                		 items.clear();
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                         JSONArray ja =(JSONArray) obj.getJSONArray("aaData");
                       
	                         for (int i = 0; i < ja.length(); i++) {
	             				
	             				final JSONObject jo2 = (JSONObject) ja.get(i);
	             				System.out.println("cName: "+jo2.getString("customerName"));
	             				items.add(new Order(
	             						jo2.getInt("orderMasterId"),
	             						jo2.getString("ordernumber"), 
	             						jo2.getString("orderAmount"), 
	             						jo2.getString("state"), 
	             						jo2.getString("remark"),
	             						jo2.getString("customerName"), 
	             						//jo2.getString("UpdatedBy"), 
	             						"",
	             						jo2.getString("UpdatedDTTM"), 
	             						jo2.getString("createdBy"), 
	             						//jo2.getString("orderstage"),
	             						"",
	             						jo2.getString("CreatedDate")));
	             				
	             	        }
	                	 }
	             
	                	 if(items.size()==0){
	                		 
	                		final Toast toast = Toast.makeText(TodayOrderListActivity.this, "No data found", Toast.LENGTH_SHORT);
	 	                     toast.show();
	 	                     
	                	 }else{
	                		 
	                	    adapter=new OrderAdapter(TodayOrderListActivity.this, R.layout.order_list_item, items);
	   	           	       _list.setAdapter(adapter);
	                	 }
	                  
	                	
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Reponse failed.", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	             
	             // When the response returned by REST has Http response code other than '200'
	             @Override
	             public void onFailure(int statusCode, Throwable error,String content) {
	                
	            	 // Avoid leaking the progress dialog
	                 ProgressDialogHelper.dismiss();
	                 System.out.println("statusCode: "+statusCode);
	                 
	                 // When Http response code is '404'
	                 if(statusCode == 404){
	                	  final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Requested resource not found", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(TodayOrderListActivity.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
	  
	  
	    
}
