package com.order.bms.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.order.Utils.Util;
import com.order.adapters.AutoCompetAdapter2;
import com.order.adapters.ColorCodeAdapter;
import com.order.adapters.CustomItemAdapter;
import com.order.application.Consts;
import com.order.bms.R;
import com.order.data.DBAdapter;
import com.order.data.DataBaseHelper;
import com.order.data.PreferencesHelper;
import com.order.dialogs.ProgressDialogHelper;
import com.order.models.ColorCode;
import com.order.models.Customer;
import com.order.models.OrderItem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class OrderDialogActivity extends Activity {
	
	 private List<OrderItem> items;
	 private List<ColorCode> items2;
	 private AutoCompetAdapter2 adapter;
	 private ColorCodeAdapter  adapter2;
	 private Spinner spinnerColorCode;
	 private EditText quantityText;
	 private TextView RateText,AmountText,packText;
	 private Double SelectedItemPrice=0.0;
	 private int SelectedItemId;
	 private String SelectedItemName,SelectedItemColorCode="";
	 private AutoCompleteTextView autoCompleteOrder;
	 private Button buttonAdd,buttonCancel;
	 //private DBAdapter dba;
	 private DataBaseHelper dbhelper;
		 
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        DisplayMetrics metrics = getResources().getDisplayMetrics();
	        int screenWidth = (int) (metrics.widthPixels*0.98);
	        setContentView(R.layout.layout_row2);
	        getWindow().setLayout(screenWidth, android.view.WindowManager.LayoutParams.WRAP_CONTENT); 
	        setupui();
	    }
	  
	  private void setupui(){
		  
		      //dba=new DBAdapter(OrderDialogActivity.this);
		      dbhelper=new DataBaseHelper(OrderDialogActivity.this);
		      items2=new ArrayList<ColorCode>();
		      items2.add(new ColorCode(0,"Select ColorCode"));
		      
		      autoCompleteOrder = (AutoCompleteTextView)findViewById(R.id.autoCompleteOrder);
		      spinnerColorCode=(Spinner)findViewById(R.id.spinnerColorCode);
	          quantityText=(EditText)findViewById(R.id.quantityText);
	          buttonAdd=(Button)findViewById(R.id.buttonAdd);
	          buttonCancel=(Button)findViewById(R.id.buttonCancel);
	         
	          RateText=(TextView)findViewById(R.id.RateText);
	          packText=(TextView)findViewById(R.id.packText);
		      AmountText=(TextView)findViewById(R.id.AmountText);
		      
		      quantityText.setImeOptions(quantityText.getImeOptions()| EditorInfo.IME_ACTION_DONE);
		      
	          autoCompleteOrder.setImeOptions(autoCompleteOrder.getImeOptions()| EditorInfo.IME_ACTION_DONE);
	          
	          items=new ArrayList<OrderItem>();
	          
	          ProgressDialogHelper.initialize(OrderDialogActivity.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
        	  RequestParams params = new RequestParams();
              params.put("userId", String.valueOf(PreferencesHelper.getUserID()));
              invokeWS2(params);
              
	          autoCompleteOrder.setOnItemClickListener(new OnItemClickListener() {
	        	  @Override
	        	  public void onItemClick(AdapterView<?> adapter, View v, int position,long arg3) {
	        	  // TODO Auto-generated method stub
	        		  try{
	        			  
	        			  InputMethodManager imm = (InputMethodManager)getSystemService(
	        				      Context.INPUT_METHOD_SERVICE);
	        				imm.hideSoftInputFromWindow(autoCompleteOrder.getWindowToken(), 0);
	        				
	        		 
	        		  OrderItem _Order = (OrderItem) adapter.getItemAtPosition(position);
	                  System.out.println("selected OrderItem: "+_Order.getItemName());
	                  
	                  if(_Order.getItemPrice()!=null){
	                	  SelectedItemPrice=Double.parseDouble(_Order.getItemPrice());
	                	  SelectedItemId=_Order.getItemId();
	                	  SelectedItemName=_Order.getItemName();
	                	  RateText.setText(String.valueOf(SelectedItemPrice));
	  
	                  }
	                  
	        		  }catch(Exception d){
		        			 d.printStackTrace();
		        		  }
	        	    }
	        	  });
	  
              quantityText.addTextChangedListener(new TextWatcher(){
	        	  
	              public void afterTextChanged(Editable s) {
	              }
	              
	              public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	              
	              public void onTextChanged(CharSequence s, int start, int before, int count){
	            	  
	            	  if(!TextUtils.isEmpty(s)){
	            		  int qun=Integer.parseInt(s.toString());
	            		  AmountText.setText(String.valueOf(SelectedItemPrice*qun)); 
	            		  System.out.println("value is: "+SelectedItemPrice*qun);
	            		  packText.setText(String.valueOf(qun*144));
	            	  }
	              }
	              
	          }); 
              
              /*packText.addTextChangedListener(new TextWatcher(){
	        	  
	              public void afterTextChanged(Editable s) {
	            	  
	              }
	              
	              public void beforeTextChanged(CharSequence s, int start, int count, int after){
	            	  System.out.println("beforeTextChanged");
	              }
	              
	              public void onTextChanged(CharSequence s, int start, int before, int count){
	            	  
	            	  System.out.println("onTextChanged");
	            	  if(!TextUtils.isEmpty(s)){
	            		  int pack=Integer.parseInt(s.toString());
	            		  //packText.setText(String.valueOf(pack/144));
	            		  //quantityText.setText(String.valueOf((pack/144)));
	            		  int qun=pack/144;
	            		 // AmountText.setText(String.valueOf(SelectedItemPrice*qun));
	            		  //packText.clearFocus();
	            		 // packText.setText(String.valueOf(qun*144));
	            	  }
	              }
	              
	          }); */
              
              spinnerColorCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	              @Override
	              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	            	  ColorCode _colorcode = (ColorCode) parent.getItemAtPosition(position);
	                  System.out.println("selected color code: "+_colorcode.getColourcode());
	                  
	                  if(!_colorcode.getColourcode().equals("Select ColorCode")){
	                	  //SelectedItemColorCode=_colorcode.getColourcode();
	                	  SelectedItemColorCode=String.valueOf(_colorcode.getColourCodeid());
	                  }else{
	                	  SelectedItemColorCode="";
	                  }
	              }

	              @Override
	              public void onNothingSelected(AdapterView<?> parent) {
	              }
	          });
              
              buttonCancel.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    	                   finish();
    				}
    			});
              
              buttonAdd.setOnClickListener(new View.OnClickListener() {
  				
  				@Override
  				public void onClick(View v) {
  					// TODO Auto-generated method stub
  					if(SelectedItemId!=0 && !TextUtils.isEmpty(quantityText.getText().toString()) && !TextUtils.isEmpty(SelectedItemColorCode)){
  						dbhelper.open();
  						Date now=new Date();
  						
  						if(dbhelper.checkItem(SelectedItemName)){
  							  dbhelper.close();
  							  final Toast toast = Toast.makeText(OrderDialogActivity.this, "Item already available!!!", Toast.LENGTH_SHORT);
  		                      toast.show();
  						}else{
  							
  						  dbhelper.insertNewItem(SelectedItemId,SelectedItemName,RateText.getText().toString(), quantityText.getText().toString(), AmountText.getText().toString(), Util.getDateFormatted(now), PreferencesHelper.getUserID(),packText.getText().toString(),SelectedItemColorCode);
  						  dbhelper.close();
    	                  Util.is_finish=true;
    	                  finish();
  						}
  	                 
  					}else{
  						
  					  final Toast toast = Toast.makeText(OrderDialogActivity.this, "Enter Information properly", Toast.LENGTH_SHORT);
                      toast.show();
                      
  					}
  					
  				}
  			});
	          
	  }
	  
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
	                		
	                		  final Toast toast = Toast.makeText(OrderDialogActivity.this, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                	
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
	             				
	             				items.add(new OrderItem(jo2.getInt("itemMasterId"), jo2.getString("name"), jo2.getString("createdBy"), jo2.getString("itemPrice")));
	                         }
	                         
	                            adapter = new AutoCompetAdapter2(OrderDialogActivity.this, R.layout.autocomple_item, items);
		                   	    autoCompleteOrder.setAdapter(adapter);
		                   	    
		             		    ProgressDialogHelper.initialize(OrderDialogActivity.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
		             		    RequestParams params = new RequestParams();
		             		    params.put("userId", String.valueOf(PreferencesHelper.getUserID()));
		             		    invokeWS3(params);
	                	 }
	                	 
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(OrderDialogActivity.this, "Reponse failed.", Toast.LENGTH_SHORT);
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
	                	  final Toast toast = Toast.makeText(OrderDialogActivity.this, "Requested resource not found", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(OrderDialogActivity.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(OrderDialogActivity.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
	    
	 
	  public void invokeWS3(RequestParams params){
	        
	        // Make RESTful webservice call using AsyncHttpClient object
	         AsyncHttpClient client = new AsyncHttpClient();
	         System.out.println("calling: "+Consts.WEBSERVICE_URL4+params);
	         client.get(Consts.WEBSERVICE_URL4,params ,new AsyncHttpResponseHandler() {
	             // When the response returned by REST has Http response code '200'
	             @Override
	             public void onSuccess(String response) {
	                 // Hide Progress Dialog
	                ProgressDialogHelper.dismiss();
	                 try {
	                	 System.out.println("response: "+response);
	                	 
	                	 if(TextUtils.isEmpty(response)){
	                		
	                		  final Toast toast = Toast.makeText(OrderDialogActivity.this, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                		 
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                         JSONArray ja =(JSONArray) obj.getJSONArray("aaData");

	                         for (int i = 0; i < ja.length(); i++) {
	             				
	             				final JSONObject jo2 = (JSONObject) ja.get(i);
	             				System.out.println("code: "+jo2.getString("Colourcode"));
	             				
	             				items2.add(new ColorCode(jo2.getInt("colourcodeid"),jo2.getString("Colourcode")));
	                         }
	                         
	                         adapter2 = new ColorCodeAdapter(OrderDialogActivity.this, R.layout.spinner_item_color_code, items2);
		                   	 spinnerColorCode.setAdapter(adapter2);
	                      
	                	 }
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(OrderDialogActivity.this, "Reponse failed.", Toast.LENGTH_SHORT);
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
	                	  final Toast toast = Toast.makeText(OrderDialogActivity.this, "Requested resource not found", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(OrderDialogActivity.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(OrderDialogActivity.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }


}
