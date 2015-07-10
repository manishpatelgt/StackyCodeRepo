package com.order.bms.activity;

import org.json.JSONObject;
import android.view.KeyEvent;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.order.models.MobileUser;
import com.order.network.NetworkHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.order.application.Consts;
import com.order.bms.R;
import com.order.data.PreferencesHelper;
import com.order.dialogs.ProgressDialogHelper;

public class Login extends Activity {
    private EditText userName,passWord;
    private String userRollName,displayName,Username;
    private int currentUserId,rollId;
    private Resources resources;
      
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupUI();
     }

    private void setupUI() {	
    	resources = getResources();
    	 
    	userName=(EditText)findViewById(R.id.editTextLoginUser);
        passWord=(EditText)findViewById(R.id.editTextLoginPassword);
        userName.setText("admin");
        passWord.setText("admin");    
        
        passWord.setImeOptions(passWord.getImeOptions()| EditorInfo.IME_ACTION_DONE);
        
        passWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView pView, int pActionId, KeyEvent pEvent) {
                if (pActionId == EditorInfo.IME_ACTION_DONE) {

                    userName.clearFocus();
                    passWord.clearFocus();
                    InputMethodManager imm = (InputMethodManager) pView.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(pView.getWindowToken(), 0);

                    if (!NetworkHelper.connectedToWiFiOrMobileNetwork(Login.this)) {
                        final Toast toast = Toast.makeText(Login.this, resources.getString(R.string.network_must_be_online), Toast.LENGTH_SHORT);
                        toast.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                toast.cancel();
                            }
                        }, 1000);
                        return false;
                    }

                    final String userLogin = userName.getText().toString();
                    final String userPassword = passWord.getText().toString();

                    if(TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(passWord.getText().toString())){
                        shakeEmptyEditText(userName);
                        shakeEmptyEditText(passWord);
                        return false;
                    }
                    else {
                    	 // Check user's credentials by querying the Security webservice
                        ProgressDialogHelper.initialize(Login.this, "Signing In...", R.layout.progress_dailog_blue);   
                        // Instantiate Http Request Param Object
                        RequestParams params = new RequestParams();
                        
                        params.put("username", userLogin);
                        
                        // Put Http parameter password with value of Password Edit Value control
                        params.put("password", userPassword);
                        
                        // Invoke RESTful Web Service with Http parameters
                        invokeWS(params);
                        
                    }

                    return true;
                }
                return false;
            }
        });
    }
    
    private void completeLogin() {
    	
    	 PreferencesHelper.setUserID(currentUserId);
         PreferencesHelper.setUserName(displayName);
         PreferencesHelper.setRollId(rollId);
         PreferencesHelper.setUserRole(userRollName);
         PreferencesHelper.setLoginCheck(true);
         
         if(userRollName.equals("admin")){
        	 Intent i = new Intent(Login.this, Admin.class);
             startActivity(i);
         } 
         else if(userRollName.equals("superadmin")){
        	 Intent i = new Intent(Login.this, Admin.class);
             startActivity(i);
         }
        
         //Navigate to main Activity
         overridePendingTransition(R.anim.slide_activity_in_right, R.anim.slide_activity_out_right);
         Login.this.finish();
    }
    
    
    @Override
    protected void onStart() {
        super.onStart();
    }
    
    @Override
    protected void onStop() {
        super.onStop();

        // Avoid leaking the progress dialog
        ProgressDialogHelper.dismiss();
    }
    
    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
        builder.setTitle("Exit")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.dismiss();
                                Login.this.finish();
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
        alert.setMessage("Are you sure want to exit?");
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.show();
        //super.onBackPressed();
    }

    public void loginButtonClick(View v){
    	
    	  if (!NetworkHelper.connectedToWiFiOrMobileNetwork(this)) {
              // Phone needs to have Internet connectivity to check user login/password or NFC pin code
    		  final Toast toast = Toast.makeText(Login.this,  resources.getString(R.string.network_must_be_online), Toast.LENGTH_SHORT);
			    toast.show();

			    Handler handler = new Handler();
			        handler.postDelayed(new Runnable() {
			           @Override
			           public void run() {
			               toast.cancel(); 
			           }
			    }, 500);
              return;
          }
    	  
    	  final String userLogin = userName.getText().toString();
          final String userPassword = passWord.getText().toString();
    	  
        if(TextUtils.isEmpty(userName.getText().toString()) || TextUtils.isEmpty(passWord.getText().toString())){
        	shakeEmptyEditText(userName);
        	shakeEmptyEditText(passWord);
        	return;
        }
        else {
        	
        	 // Check user's credentials by querying the Security webservice
            ProgressDialogHelper.initialize(Login.this, "Signing In...", R.layout.progress_dailog_blue);   
            // Instantiate Http Request Param Object
            RequestParams params = new RequestParams();
            
            params.put("username", userLogin);
            
            // Put Http parameter password with value of Password Edit Value control
            params.put("password", userPassword);
            
            // Invoke RESTful Web Service with Http parameters
            invokeWS(params);
            
        }

    }
    
    public void invokeWS(RequestParams params){
        
        // Make RESTful webservice call using AsyncHttpClient object
         AsyncHttpClient client = new AsyncHttpClient();
         System.out.println("params: "+params.toString());
         client.get(Consts.WEBSERVICE_URL,params ,new AsyncHttpResponseHandler() {
             // When the response returned by REST has Http response code '200'
             @Override
             public void onSuccess(String response) {
                 // Hide Progress Dialog
                ProgressDialogHelper.dismiss();
                 try {
                	 System.out.println("response: "+response);
                	 
                	 if(TextUtils.isEmpty(response)){
                		
                		  final Toast toast = Toast.makeText(Login.this, "User " + userName.getText().toString() + " failed to login", Toast.LENGTH_SHORT);
                          toast.show();
                          
                	 }else{
                		  // JSON Object
                         JSONObject obj = new JSONObject(response);
                         MobileUser user = new Gson().fromJson(response, MobileUser.class);
                         currentUserId=user.userid;
                         displayName=user.username;
                         rollId=user.userRollId;
                         userRollName=user.userRolename;
                         completeLogin();
                         System.out.println(obj.getInt("userid")+" "+obj.getInt("userRollId")+" "+obj.getString("username")+" "+obj.getString("userRolename"));         
                	 }
                	
                	
                 } catch (Exception e) {
                     // TODO Auto-generated catch block
                     e.printStackTrace();
                     final Toast toast = Toast.makeText(Login.this, "User "+userName.getText().toString()+ " login Failed.", Toast.LENGTH_SHORT);
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
                	  final Toast toast = Toast.makeText(Login.this, "Requested resource not found", Toast.LENGTH_SHORT);
                      toast.show();
                 } 
                 // When Http response code is '500'
                 else if(statusCode == 500){
                	  final Toast toast = Toast.makeText(Login.this, "Something went wrong at server end", Toast.LENGTH_SHORT);
                      toast.show();
                 } 
                 // When Http response code other than 404, 500
                 else{
                	 final Toast toast = Toast.makeText(Login.this, "Unexpected Error occcured!", Toast.LENGTH_SHORT);
                     toast.show();
                 }
             }
         });
         
    }
    
    private void shakeEmptyEditText(EditText view) {
        if (TextUtils.isEmpty(view.getText().toString())) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.view_shake);
            view.startAnimation(animation);
        }
    }

}