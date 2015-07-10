package com.order.bms.activity;


import java.util.ArrayList;
import java.util.List;

import android.text.TextWatcher;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.order.adapters.AutoCompetAdapter;
import com.order.adapters.AutoCompetAdapter2;
import com.order.adapters.CustomerAdapter;
import com.order.adapters.OrderItemAdapter;
import com.order.application.BMSActivity;
import com.order.application.Consts;
import com.order.bms.R;
import com.order.data.PreferencesHelper;
import com.order.dialogs.ProgressDialogHelper;
import com.order.models.Customer;
import com.order.models.OrderItem;
import com.order.network.NetworkHelper;

public class PlaceOrder extends BMSActivity {

	 private List<Customer> items;
	 private List<OrderItem> items2;
	 private CustomerAdapter  adapter;
	 private AutoCompetAdapter2 adapter2;
	 private LinearLayout orderlayout;
	 private Spinner spinner;
	 private ScrollView scroll;
	 private LinearLayout tl_item;
	 private EditText quantityText,remarkText;
	 private TextView RateText,AmountText,TotalAmountText,ItemNameText;
	 private Double SelectedItemPrice=0.0;
	 private AutoCompleteTextView autoCompleteOrder;
	 private LayoutInflater linf;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_place_order);
	        setupui();
	    }

	    private void setupui(){
	    	
	       linf = (LayoutInflater) getApplicationContext().getSystemService(
		                Context.LAYOUT_INFLATER_SERVICE);
		   linf = LayoutInflater.from(PlaceOrder.this);
	     
	      items=new ArrayList<Customer>();
	      items.add(new Customer(100, "Select Customer",null, null,null));
          
	      spinner=(Spinner)findViewById(R.id.spinnerCustomer);
	      orderlayout=(LinearLayout)findViewById(R.id.orderlayout);
	      scroll=(ScrollView)findViewById(R.id.scroll);
	      
	      autoCompleteOrder = (AutoCompleteTextView) findViewById(R.id.autoCompleteOrder);
	    
	       System.out.println("userId: "+PreferencesHelper.getUserID());
          
	      if (!NetworkHelper.connectedToWiFiOrMobileNetwork(PlaceOrder.this)) {
              final Toast toast = Toast.makeText(PlaceOrder.this, getResources().getString(R.string.network_must_be_online), Toast.LENGTH_SHORT);
              toast.show();

              Handler handler = new Handler();
              handler.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      toast.cancel();
                  }
              }, 1000);
              
          }else{
        	  
        	   ProgressDialogHelper.initialize(PlaceOrder.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
               // Invoke RESTful Web Service with Http parameters
        	   RequestParams params = new RequestParams();
               params.put("userId", String.valueOf(PreferencesHelper.getUserID()));
               invokeWS(params);
          }
	     
          spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  Customer _customer = (Customer) parent.getItemAtPosition(position);
                  System.out.println("selected customer role: "+_customer.getUserRole());
                  
                  if(_customer.getUserRole()!=null){
                	  
                	  items2=new ArrayList<OrderItem>();
            	      //items2.add(new OrderItem(100, "Select Order", null, null));
            	      
            	      if (!NetworkHelper.connectedToWiFiOrMobileNetwork(PlaceOrder.this)) {
                          final Toast toast = Toast.makeText(PlaceOrder.this, getResources().getString(R.string.network_must_be_online), Toast.LENGTH_SHORT);
                          toast.show();

                          Handler handler = new Handler();
                          handler.postDelayed(new Runnable() {
                              @Override
                              public void run() {
                                  toast.cancel();
                              }
                          }, 1000);
                          
                      }else{
                    	  
                    	  ProgressDialogHelper.initialize(PlaceOrder.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
                    	  RequestParams params = new RequestParams();
                          params.put("userId", String.valueOf(PreferencesHelper.getUserID()));
                          invokeWS2(params);
                      }
            	      
                  }
              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {
              }
          });
             
          
          autoCompleteOrder.setImeOptions(autoCompleteOrder.getImeOptions()| EditorInfo.IME_ACTION_DONE);
            
          autoCompleteOrder.setOnItemClickListener(new OnItemClickListener() {
        	  @Override
        	  public void onItemClick(AdapterView<?> adapter, View v, int position,long arg3) {
        	  // TODO Auto-generated method stub
        		  try{
        			  
        			  InputMethodManager imm = (InputMethodManager)getSystemService(
        				      Context.INPUT_METHOD_SERVICE);
        				imm.hideSoftInputFromWindow(autoCompleteOrder.getWindowToken(), 0);
        				
        		  }catch(Exception d){
        			 d.printStackTrace();
        		  }
        		  OrderItem _Order = (OrderItem) adapter.getItemAtPosition(position);
                  System.out.println("selected OrderItem: "+_Order.getItemName());
                  
                  if(_Order.getItemPrice()!=null){
                	  scroll.setVisibility(View.VISIBLE);
                	  //SelectedItemPrice=Double.parseDouble(_Order.getItemPrice());
                	  autoCompleteOrder.setText("");
                	  autoCompleteOrder.setHint("Enter Your Item Name");
                	  addItem(_Order.getItemName(), _Order.getItemPrice());
                  }
        	    }
        	  });
	    }
	    
	    private void addItem(String itemName,String ItemPrice){
	    	 tl_item = (LinearLayout) findViewById(R.id.tl_item);
		
	         final View v = linf.inflate(R.layout.layout_row, null);
	         
	          //ItemNameText=(TextView)v.findViewById(R.id.itemName);
	         // ItemNameText.setText(itemName);
	          
	          quantityText=(EditText)v.findViewById(R.id.quantityText);
		      
	          RateText=(TextView)v.findViewById(R.id.RateText);
		      RateText.setText(String.valueOf(SelectedItemPrice));
		      
		      AmountText=(TextView)v.findViewById(R.id.AmountText);
		      TotalAmountText=(TextView)v.findViewById(R.id.TotalAmountText);
		      remarkText=(EditText)v.findViewById(R.id.remarkText);
		      
		      quantityText.setImeOptions(quantityText.getImeOptions()| EditorInfo.IME_ACTION_DONE);
		      
		      remarkText.setImeOptions(quantityText.getImeOptions()| EditorInfo.IME_ACTION_DONE);
		      
		      SelectedItemPrice=Double.parseDouble(ItemPrice);
		      
	          quantityText.addTextChangedListener(new TextWatcher(){
	        	  
	              public void afterTextChanged(Editable s) {
	              }
	              
	              public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	              
	              public void onTextChanged(CharSequence s, int start, int before, int count){
	            	  
	            	  if(!TextUtils.isEmpty(s)){
	            		  int qun=Integer.parseInt(s.toString());
	            		  AmountText.setText(String.valueOf((SelectedItemPrice*qun))); 
	            		  TotalAmountText.setText(String.valueOf((SelectedItemPrice*qun))); 
	            	  }
	              }
	              
	          }); 
	          
	         tl_item.addView(v);
	        
	    }

	 
	    @Override
	    protected void onStop() {
	        super.onStop();

	        // Avoid leaking the progress dialog
	        ProgressDialogHelper.dismiss();
	    }
	    
	    
	    public void invokeWS(RequestParams params){
	        
	        // Make RESTful webservice call using AsyncHttpClient object
	         AsyncHttpClient client = new AsyncHttpClient();
	         System.out.println("calling: "+Consts.WEBSERVICE_URL2+" params: "+params.toString());
	        
	         client.get(Consts.WEBSERVICE_URL2,params ,new AsyncHttpResponseHandler() {
	             // When the response returned by REST has Http response code '200'
	             @Override
	             public void onSuccess(String response) {
	                 // Hide Progress Dialog
	                ProgressDialogHelper.dismiss();
	                 try {
	                	 System.out.println("response: "+response);
	                	 
	                	 if(TextUtils.isEmpty(response)){
	                		
	                		  final Toast toast = Toast.makeText(PlaceOrder.this, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                		 
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                         JSONArray ja =(JSONArray) obj.getJSONArray("aaData");

	                         for (int i = 0; i < ja.length(); i++) {
	             				
	             				final JSONObject jo2 = (JSONObject) ja.get(i);
	             				/*System.out.println("UserId: "+jo2.getString("UserId"));
	             				System.out.println("UserName: "+jo2.getString("UserName"));
	             				System.out.println("FirstName: "+jo2.getString("FirstName"));
	             				System.out.println("PhoneNo: "+jo2.getString("PhoneNo"));
	             				System.out.println("status: "+jo2.getString("status"));
	             				System.out.println("UserRole: "+jo2.getString("UserRole"));*/
	             				
	             				items.add(new Customer(jo2.getInt("UserId"), jo2.getString("UserName"), jo2.getString("FirstName"), jo2.getString("PhoneNo"),jo2.getString("UserRole")));
	                         }
	                	 }
	                	 adapter=new CustomerAdapter(PlaceOrder.this, R.layout.spinner_item_customer_role, items);
	                	 spinner.setAdapter(adapter);
	                	
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(PlaceOrder.this, "Reponse failed.", Toast.LENGTH_SHORT);
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
	                	  final Toast toast = Toast.makeText(PlaceOrder.this, "Requested resource not found", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(PlaceOrder.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(PlaceOrder.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
	    
	   //http://stackoverflow.com/questions/13052036/posting-json-xml-using-android-async-http-loopj
       public void invokeWS2(RequestParams params){
	        
	        // Make RESTful webservice call using AsyncHttpClient object
	         AsyncHttpClient client = new AsyncHttpClient();
	         System.out.println("calling: "+Consts.WEBSERVICE_URL3);
	         client.get(Consts.WEBSERVICE_URL3,params ,new AsyncHttpResponseHandler() {
	             // When the response returned by REST has Http response code '200'
	             @Override
	             public void onSuccess(String response) {
	                 // Hide Progress Dialog
	                ProgressDialogHelper.dismiss();
	                 try {
	                	 System.out.println("response: "+response);
	                	 
	                	 if(TextUtils.isEmpty(response)){
	                		
	                		  final Toast toast = Toast.makeText(PlaceOrder.this, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                		 orderlayout.setVisibility(View.VISIBLE);
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                         JSONArray ja =(JSONArray) obj.getJSONArray("aaData");

	                         for (int i = 0; i < ja.length(); i++) {
	             				
	             				final JSONObject jo2 = (JSONObject) ja.get(i);
	             				/*System.out.println("itemMasterId: "+jo2.getString("itemMasterId"));
	             				System.out.println("name: "+jo2.getString("name"));
	             				System.out.println("createdBy: "+jo2.getString("createdBy"));
	             				System.out.println("itemPrice: "+jo2.getString("itemPrice"));
	             				System.out.println("status: "+jo2.getString("status"));*/
	             				
	             				items2.add(new OrderItem(jo2.getInt("itemMasterId"), jo2.getString("name"), jo2.getString("createdBy"), jo2.getString("itemPrice")));
	                         }
	                	 }
	                	 
	                	 adapter2 = new AutoCompetAdapter2(PlaceOrder.this, R.layout.spinner_item_customer_role, items2);
	                	 autoCompleteOrder.setAdapter(adapter2);
	                	 
	               	
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(PlaceOrder.this, "Reponse failed.", Toast.LENGTH_SHORT);
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
	                	  final Toast toast = Toast.makeText(PlaceOrder.this, "Requested resource not found", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(PlaceOrder.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(PlaceOrder.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
	    
	    
	}

