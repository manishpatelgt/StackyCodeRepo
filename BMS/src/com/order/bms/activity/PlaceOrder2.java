package com.order.bms.activity;


import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.order.Utils.Util;
import com.order.adapters.CustomItemAdapter;
import com.order.adapters.CustomerAdapter;
import com.order.application.BMSAapplication;
import com.order.application.Consts;
import com.order.bms.R;
import com.order.data.DBAdapter;
import com.order.data.DataBaseHelper;
import com.order.data.PreferencesHelper;
import com.order.dialogs.ProgressDialogHelper;
import com.order.models.CustomItem;
import com.order.models.Customer;
import com.order.models.OrderItem;
import com.order.network.NetworkHelper;

public class PlaceOrder2 extends ActionBarActivity {

	 private List<Customer> items;
	 private List<CustomItem> items2;
	 private CustomerAdapter  adapter;
	 private CustomItemAdapter  adapter2;
	 private Spinner spinner;
	 private int selectedCustomerId;
	 private ListView _list;
	 //private DBAdapter dba;
	 private DataBaseHelper dbhelper;
	 private LinearLayout footer;
	 private String selectedCustomerName,selectedFirstName,selectedRole,selectedCustomerPhone;
	 private TextView textNoFound;
	 private Double totalAmount;
	 private TextView totalAmountText;
	 private Button buttonPlaceOrder;
	 private String OrderNumber="0";
	 private int OrderMasterId;
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_order_new);
	        Bundle b=getIntent().getExtras();
	        if(b!=null){
	        	OrderMasterId=b.getInt("OrderId");
	        	System.out.println("OrderId: "+OrderMasterId);
	        	OrderNumber=b.getString("OrderNumber");
	        }else{
	        	OrderMasterId=0;
	        }
	        setupui();
	    }

	    private void setupui(){
	         
	      items=new ArrayList<Customer>();
	      items.add(new Customer(100, "Select Customer",null, null,null));
          
	       spinner=(Spinner)findViewById(R.id.spinnerCustomer);
	      _list=(ListView)findViewById(R.id.list);
	      
	      textNoFound=(TextView)findViewById(R.id.textNoFound);
	      footer=(LinearLayout)findViewById(R.id.footer);
	      totalAmountText=(TextView)findViewById(R.id.TotalAmountText);
	      buttonPlaceOrder=(Button)findViewById(R.id.buttonPlaceOrder);
	      
	      //dba=new DBAdapter(PlaceOrder2.this);
	      dbhelper=new DataBaseHelper(PlaceOrder2.this);
	      dbhelper.open();
	      items2=dbhelper.getList(PreferencesHelper.getUserID());
	      
	      if(items2.size()==0){
	    	  textNoFound.setVisibility(View.VISIBLE);
	    	  _list.setVisibility(View.GONE);
	    	  footer.setVisibility(View.GONE);
	      }else{
	    	  totalAmount=dbhelper.getTotalAmount();
	    	  totalAmountText.setText("Total Amount: "+totalAmount);
	      }
	      dbhelper.close();
	      
	       adapter2=new CustomItemAdapter(PlaceOrder2.this, R.layout.list_item, items2);
	      _list.setAdapter(adapter2);
	       
	       registerForContextMenu(_list);  
	       System.out.println("userId: "+PreferencesHelper.getUserID());
	       
	       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	              @Override
	              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	                  Customer _customer = (Customer) parent.getItemAtPosition(position);
	                  System.out.println("selected customer role: "+_customer.getUserRole());
	                  
	                  if(_customer.getUserRole()!=null){
	                	  selectedCustomerId=_customer.getUserId();
	                	  selectedCustomerName=_customer.getUserName();
	                	  selectedFirstName=_customer.getFirstName();
	                	  selectedCustomerPhone=_customer.getPhoneNo();
	                	  selectedRole=_customer.getUserRole();
	                   }else{
	                	  selectedCustomerId=0;
	                  }
	              }

	              @Override
	              public void onNothingSelected(AdapterView<?> parent) {
	              }
	          });
	       
	       buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//DFragment dFragment = new DFragment().newInstance(String.valueOf(totalAmount));
			 	//dFragment.show(getSupportFragmentManager(), "Order");
			 	 BMSAapplication.getInstance().setOrderItems(items2);
			 	 Intent i=new Intent(PlaceOrder2.this,ConfirmOrderActivity.class);
	        	 i.putExtra("totalAmount",String.valueOf(totalAmount));
	        	 i.putExtra("OrderId",OrderMasterId);
	        	 i.putExtra("OrderNumber",OrderNumber);
	             startActivity(i);
		         overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
			  }
	    	});
	   
	      if (!NetworkHelper.connectedToWiFiOrMobileNetwork(PlaceOrder2.this)) {
              final Toast toast = Toast.makeText(PlaceOrder2.this, getResources().getString(R.string.network_must_be_online), Toast.LENGTH_SHORT);
              toast.show();

              Handler handler = new Handler();
              handler.postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      toast.cancel();
                  }
              }, 1000);
              
          }else{
        	  
        	   ProgressDialogHelper.initialize(PlaceOrder2.this, "Please wait downloding data...", R.layout.layout_progress_dailog);   
               // Invoke RESTful Web Service with Http parameters
        	   RequestParams params = new RequestParams();
               params.put("userId", String.valueOf(PreferencesHelper.getUserID()));
               invokeWS(params);
          }
	    }
	   
	    
	    @Override
	    public void onResume() {
	        super.onResume();
	        try{
	        	System.out.println("on onResume");
	        	if(Util.is_finish){
	        		System.out.println("data changed");
	        		items2.clear();
	        		dbhelper.open();
		  	        items2=dbhelper.getList(PreferencesHelper.getUserID());
		  	        totalAmount=dbhelper.getTotalAmount();
		  	        totalAmountText.setText("Total Amount: "+totalAmount);
		  	       
		  	        if(items2.size()!=0){
          	    	  textNoFound.setVisibility(View.GONE);
          	    	  _list.setVisibility(View.VISIBLE);
          	    	  footer.setVisibility(View.VISIBLE);
          	    	  totalAmount=dbhelper.getTotalAmount();
          	    	  dbhelper.close();
          	    	  totalAmountText.setText("Total Amount: "+totalAmount);
          	         }else{
          	          textNoFound.setVisibility(View.VISIBLE);
             	      _list.setVisibility(View.GONE);
             	      footer.setVisibility(View.GONE); 
          	         }
		  	        adapter2.refresh(items2);
		  	       //adapter2=new CustomItemAdapter(PlaceOrder2.this, R.layout.list_item, items2);
			       //_list.setAdapter(adapter2);
		  	       // adapter2.notifyDataSetChanged();
		  	     	Util.is_finish=false;
	        	}
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
	     }

	    @Override
	    public void onPause() {
	        super.onPause();
	        System.out.println("on Pause");
	    }

	    
	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.add_menu, menu);
	        return true;
	    }
	    
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {

	        if (item.getItemId() == android.R.id.home) {
	            System.out.println("get key yeeeee!!!!!");
	            Util.is_finish2=true;
	            finish();
	            overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
	            return true;
	        }
	        if (item.getItemId() == R.id.action_add) {
	            Intent i=new Intent(PlaceOrder2.this,OrderDialogActivity.class);
	            startActivity(i);
	            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	            return true;
	        }
	        
	        if (item.getItemId() == R.id.action_info) {
	        	if(selectedCustomerId!=0){
					DFragment2 dt=new DFragment2().newInstance(selectedCustomerName,selectedFirstName,selectedRole,selectedCustomerPhone);
					dt.show(getSupportFragmentManager(), "Details");
				}else{
					
					 final Toast toast = Toast.makeText(PlaceOrder2.this, "Select customer first", Toast.LENGTH_SHORT);
		              toast.show();

		              Handler handler = new Handler();
		              handler.postDelayed(new Runnable() {
		                  @Override
		                  public void run() {
		                      toast.cancel();
		                  }
		              }, 1000);
		              
				}
	            return true;
	        }
	        return false;
	    }   
	    
	    @Override
	    public void onBackPressed(){
	        super.onBackPressed();
	        Util.is_finish2=true;
	        // Use left swipe when user presses hardware back button
	        overridePendingTransition(R.anim.activity_slide_in_left, R.anim.activity_slide_out_left);
	    }
	       
	    @Override  
	    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {  
	    super.onCreateContextMenu(menu, v, menuInfo);  
	        menu.setHeaderTitle("Modify item");  
	        menu.add(0, v.getId(), 0, "Edit");  
	        menu.add(0, v.getId(), 0, "Delete");  
	    }  
	  
	    @Override  
	    public boolean onContextItemSelected(MenuItem item) {  
	    	
	    	 final AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	        
	        if(item.getTitle()=="Edit"){
	        	
	        	CustomItem cItem = (CustomItem) items2.get(info.position);
	        	String  ItemName= cItem.getItemName();
	        	System.out.println("selected item for edit is: "+ItemName);
	        	
	        	 Intent i=new Intent(PlaceOrder2.this,OrderDialogActivity2.class);
	        	 i.putExtra("itemId",cItem.getItemId());
	        	 i.putExtra("itemName", cItem.getItemName());
	        	 i.putExtra("itemQuntity", cItem.getItemQuantity());
	        	 i.putExtra("itemPrice", cItem.getItemPrice());
	        	 i.putExtra("itemAmount", cItem.getItemAmount());	
	        	 i.putExtra("itemPack", cItem.getItemPack());	
	        	 i.putExtra("itemColorCode", cItem.getItemColorCode());	
	             startActivity(i);
		         overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	        	}
	        
	        else if(item.getTitle()=="Delete"){
	        	System.out.println("in Delete mode");
	        	
	        	final CustomItem cItem = (CustomItem) items2.get(info.position);
	        	String  ItemName= cItem.getItemName();
	        	
	        	 AlertDialog.Builder builder = new AlertDialog.Builder(PlaceOrder2.this);
	             builder.setTitle("Delete item "+ItemName)
	                     .setPositiveButton("Yes",
	                             new DialogInterface.OnClickListener() {
	                                 @Override
	                                 public void onClick(DialogInterface dialog,
	                                                     int id) {
	                                     dialog.dismiss();
	                                    
	                                     dbhelper.open();
	                                     dbhelper.removeItem(cItem.getItemId());
	                           	         items2.remove(info.position);
	                           	         adapter2.notifyDataSetChanged();
	                           	         
	                           	         if(items2.size()==0){
	                        	    	   textNoFound.setVisibility(View.VISIBLE);
	                        	    	  _list.setVisibility(View.GONE);
	                        	    	   footer.setVisibility(View.GONE);
	                        	         }else{
	                        	        	  totalAmount=dbhelper.getTotalAmount();
	                        	        	  totalAmountText.setText("Total Amount: "+totalAmount);
	                        	         }
	                           	         dbhelper.close();
	                           	         Util.is_finish=true;
	                                 }
	                             });
	             builder.setNegativeButton("No",
	                     new DialogInterface.OnClickListener() {

	                         @Override
	                         public void onClick(DialogInterface dialog, int which) {
	                             dialog.dismiss();
	                         }
	                     });

	             AlertDialog alert = builder.create();
	             alert.setMessage("Are you sure want to delete?");
	             alert.setIcon(android.R.drawable.ic_dialog_alert);
	             alert.show();
	        	}  
	        else {return false;}  
	    return true;  
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
	                		
	                		  final Toast toast = Toast.makeText(PlaceOrder2.this, "Reponse null", Toast.LENGTH_SHORT);
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
	                	 adapter=new CustomerAdapter(PlaceOrder2.this, R.layout.spinner_item_customer_role, items);
	                	 spinner.setAdapter(adapter);
	                	
	                 } catch (Exception e) {
	                     // TODO Auto-generated catch block
	                     e.printStackTrace();
	                     final Toast toast = Toast.makeText(PlaceOrder2.this, "Reponse failed.", Toast.LENGTH_SHORT);
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
	                	  final Toast toast = Toast.makeText(PlaceOrder2.this, "Requested resource not found", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code is '500'
	                 else if(statusCode == 500){
	                	  final Toast toast = Toast.makeText(PlaceOrder2.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
	                      toast.show();
	                 } 
	                 // When Http response code other than 404, 500
	                 else{
	                	 final Toast toast = Toast.makeText(PlaceOrder2.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
	                     toast.show();
	                 }
	             }
	         });
	         
	    }
	    
	    
	}

