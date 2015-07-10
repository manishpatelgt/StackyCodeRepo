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
import com.order.adapters.ColorCodeAdapter;
import com.order.application.Consts;
import com.order.bms.R;
import com.order.data.DBAdapter;
import com.order.data.DataBaseHelper;
import com.order.data.PreferencesHelper;
import com.order.dialogs.ProgressDialogHelper;
import com.order.models.ColorCode;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDialogActivity2 extends Activity {
	
	 private EditText quantityText;
	 private TextView RateText,AmountText,edit_itemText,packText;
	 private int itemId;
	 private List<ColorCode> items2;
	 private ColorCodeAdapter  adapter2;
	 private Spinner spinnerColorCode;
	 private String itemName,itemQuntity,itemPrice,itemAmount,SelectedItemColorCode,itemPack;
	 private Button buttonUpdate,buttonCancel;
	 //private DBAdapter dba;
	 private DataBaseHelper dbhelper;	 
	 
	  @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        DisplayMetrics metrics = getResources().getDisplayMetrics();
	        int screenWidth = (int) (metrics.widthPixels*0.98);
	        setContentView(R.layout.layout_update);
	        getWindow().setLayout(screenWidth, android.view.WindowManager.LayoutParams.WRAP_CONTENT); 
	        Bundle b=getIntent().getExtras();
	        itemId=b.getInt("itemId");
	        itemName=b.getString("itemName");
	        itemQuntity=b.getString("itemQuntity");
	        itemPrice=b.getString("itemPrice");
	        itemAmount=b.getString("itemAmount");
	        itemPack=b.getString("itemPack");
	        SelectedItemColorCode=b.getString("itemColorCode");
	        setupui();
	    }
	  
	  private void setupui(){
		  
		      items2=new ArrayList<ColorCode>();
	         
		      //dba=new DBAdapter(OrderDialogActivity2.this);
		      dbhelper=new DataBaseHelper(OrderDialogActivity2.this);
	       
		      quantityText=(EditText)findViewById(R.id.quantityText);
	          buttonUpdate=(Button)findViewById(R.id.buttonUpdate);
	          buttonCancel=(Button)findViewById(R.id.buttonCancel);
	          RateText=(TextView)findViewById(R.id.RateText);
		      AmountText=(TextView)findViewById(R.id.AmountText);
		      edit_itemText=(EditText)findViewById(R.id.itemText);
		      packText=(TextView)findViewById(R.id.packText);
		      spinnerColorCode=(Spinner)findViewById(R.id.spinnerColorCode);
		      
		      edit_itemText.setText(itemName);
		      quantityText.setText(itemQuntity);
		      RateText.setText(itemPrice);
		      AmountText.setText(itemAmount);
		      packText.setText(itemPack);
		      
		      ProgressDialogHelper.initialize(OrderDialogActivity2.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
   		      RequestParams params = new RequestParams();
   		      params.put("userId", String.valueOf(PreferencesHelper.getUserID()));
   		      invokeWS3(params);
   		    
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
              
		      
		      quantityText.setImeOptions(quantityText.getImeOptions()| EditorInfo.IME_ACTION_DONE);
		   
              quantityText.addTextChangedListener(new TextWatcher(){
	        	  
	              public void afterTextChanged(Editable s) {
	              }
	              
	              public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	              
	              public void onTextChanged(CharSequence s, int start, int before, int count){
	            	  
	            	  if(!TextUtils.isEmpty(s)){
	            		  int qun=Integer.parseInt(s.toString());
	            		  AmountText.setText(String.valueOf((Double.parseDouble(itemPrice)*qun))); 
	            		  packText.setText(String.valueOf(qun*144));
	            	  }
	              }
	              
	          }); 
              
              buttonCancel.setOnClickListener(new View.OnClickListener() {
    				
    				@Override
    				public void onClick(View v) {
    	                   finish();
    				}
    			});
              
              buttonUpdate.setOnClickListener(new View.OnClickListener() {
  				
  				@Override
  				public void onClick(View v) {
  					// TODO Auto-generated method stub
  					if(itemId!=0 && !TextUtils.isEmpty(quantityText.getText().toString()) && !TextUtils.isEmpty(SelectedItemColorCode)){
  						dbhelper.open();
  						Date now=new Date();
  						dbhelper.updateItem(itemId,itemName,RateText.getText().toString(), quantityText.getText().toString(), AmountText.getText().toString(), Util.getDateFormatted(now), PreferencesHelper.getUserID(),packText.getText().toString(),SelectedItemColorCode);
  						dbhelper.close();
  	                    Util.is_finish=true;
  	                    finish();
  					}else{
  						
  					  final Toast toast = Toast.makeText(OrderDialogActivity2.this, "Enter Information properly", Toast.LENGTH_SHORT);
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
	                		
	                		  final Toast toast = Toast.makeText(OrderDialogActivity2.this, "Reponse null", Toast.LENGTH_SHORT);
	                          toast.show();
	                          
	                	 }else{
	                		 
	                		  // JSON Object
	                         JSONObject obj = new JSONObject(response);
	                         JSONArray ja =(JSONArray) obj.getJSONArray("aaData");
                             int index=0;
	                         items2.add(new ColorCode(0,"Select ColorCode"));
	                         
	                         for (int i = 0; i < ja.length(); i++) {
	             				
	             				final JSONObject jo2 = (JSONObject) ja.get(i);
	             				items2.add(new ColorCode(jo2.getInt("colourcodeid"),jo2.getString("Colourcode")));
	                         }
	                         
	                         for(int k=0;k<items2.size();k++){
	                        	 if(SelectedItemColorCode.equals(items2.get(k).getColourcode())){
	                        		 index=k;
	                        	 }
	                         }
	                         adapter2 = new ColorCodeAdapter(OrderDialogActivity2.this, R.layout.spinner_item_color_code, items2);
		                   	 spinnerColorCode.setAdapter(adapter2);
		                     spinnerColorCode.setSelection(index);
	                      
	                	 }
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(OrderDialogActivity2.this, "Reponse failed.", Toast.LENGTH_SHORT);
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
	                	  final Toast toast = Toast.makeText(OrderDialogActivity2.this, "Requested resource not found", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(OrderDialogActivity2.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(OrderDialogActivity2.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
}
