package com.order.data;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import com.order.models.CustomItem;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DBAdapter {

	private static final String DATABASE_NAME = "order";

	// private static String DATABASE_NAME=null;

	private static final int DATABASE_VERSION = 1;

	private final Context context;

	private DatabaseHelper DBHelper;
	private static SQLiteDatabase db;

	public String customer_id;

	public DBAdapter(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	public static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

			//db.execSQL("DROP TABLE IF EXISTS titles");
			/*try{
				InputStream databaseInputStream1;
				databaseInputStream1 = context.getAssets().open("database");

				ImportDatabase ipd = new ImportDatabase(databaseInputStream1);
				ipd.copyDataBase();
				//onCreate(db);
			}catch(Exception e){
				e.printStackTrace();
			}*/
		

		}

	}

	// ---opens the database---
	public DBAdapter open() throws SQLException {
		db = DBHelper.getWritableDatabase();
		return this;
	}

	// ---closes the database---
	public void close() {
		DBHelper.close();
		// db.close();
	}

	// ---delete the database---
	public void Delete() throws SQLException {
		// db.execSQL("DROP TABLE IF EXISTS titles");
		// db.deleteDatabase(new
		// File("/data/data/com.appstart/databases/database"));
		context.deleteDatabase("order");
	}

	public List<CustomItem> getList(int userId) throws SQLException {

		List<CustomItem> _list=new ArrayList<CustomItem>();
		Cursor c=null;
		try{
			
			 c = db.rawQuery("SELECT * FROM tbl_item WHERE createdBy="+userId+" ORDER BY addedDateTime DESC" , null);
			
			if (c != null && c.moveToFirst()) {
				do{
					 System.out.println("itemName: "+c.getString(1).toString());
					_list.add(new CustomItem(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4),
							c.getString(5), c.getString(6), c.getString(7), c.getString(8)));
				}while(c.moveToNext());
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			c.close();
			
		}finally{
			c.close();
		}
		return _list;	
	}
	
	
	public void insertNewItem(int itemId,String ItemName,String itemPrice,String itemQuntity,String itemAmount,String addedDateTime,int createdBy,String Pack,String ColorCode){
		ContentValues values = new ContentValues();
		values.put("itemId", itemId);
		values.put("itemName", ItemName);
		values.put("itemPrice", itemPrice);
		values.put("itemQuntity", itemQuntity);
		values.put("itemAmount", itemAmount);
		values.put("addedDateTime", addedDateTime);
		values.put("createdBy", createdBy);
		values.put("Pack", Pack);
		values.put("ColorCode", ColorCode);
		System.out.println("records inserted");
    	db.insert("tbl_item", null, values);
	}
	
	
	public Boolean updateItem(int ItemId, String ItemName,
			String itemPrice, String itemQuntity, String itemAmount, 
		    String addedDateTime, int createdBy,String Pack,String ColorCode) {

		ContentValues values = new ContentValues();
		values.put("itemId", ItemId);
		values.put("itemName", ItemName);
		values.put("itemPrice", itemPrice);
		values.put("itemQuntity", itemQuntity);
		values.put("itemAmount", itemAmount);
		values.put("addedDateTime", addedDateTime);
		values.put("createdBy", createdBy);
		values.put("Pack", Pack);
		values.put("ColorCode", ColorCode);
		return db.update("tbl_item", values, "itemId"
				+ "='" + ItemId + "'", null) > 0;
	}

	 public void removeItem(int id){
	        String string =String.valueOf(id);
	        System.out.println("item removed");
	        db.execSQL("DELETE FROM tbl_item WHERE itemId = '" + string + "'");
	    }
	 
	 public boolean checkItem(String itemName){
		  boolean flag=false;
		  Cursor c=null;
			try{
				
				 c = db.rawQuery("SELECT itemId FROM tbl_item WHERE itemName='"+itemName+"'" , null);
				
				if(c!=null && c.moveToFirst()){
					if(c.getCount()>0){
						flag=true;
					}else{
						flag=false;
					}
				}
				
			}catch(Exception e){
				e.printStackTrace();
				c.close();
				
			}finally{
				c.close();
			}
			return flag;
	    }
	 
	 
	 public Double getTotalAmount(){
		 
		  Double count=0.0;
		  Cursor c=null;
			try{
				 c = db.rawQuery("SELECT itemAmount FROM tbl_item " , null);
				if(c!=null && c.moveToFirst()){
					do{
						String totalAmt = c.getString(0);
						count+=Double.valueOf(totalAmt);
						
					}while(c.moveToNext());
				}
				
			}catch(Exception e){
				e.printStackTrace();
				c.close();
				
			}finally{
				c.close();
			}
			System.out.println("total amount: "+count);
			return count;
	    }
	 
	public boolean deleteLogin() {

		return db.delete("tbl_item", null, null) > 0;
	}

}