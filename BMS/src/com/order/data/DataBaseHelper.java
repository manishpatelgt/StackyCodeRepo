package com.order.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.order.application.Consts;
import com.order.models.CustomItem;
import com.order.models.Order;
import com.order.models.TaskNumber;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

    public class DataBaseHelper extends SQLiteOpenHelper{

    //The Android's default system path of your application database.
    private static String DB_PATH = Consts.APPLICATION_DATABASES_PATH+"order";
    private static String DB_NAME = "order";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
 // Data Base Version.
    private static final int DATABASE_VERSION = 1;
    /**
      * Constructor
      * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
      * @param context
      */
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
            this.myContext = context;
    }   

    /**
     * This method opens the data base connection.
     * First it create the path up till data base of the device.
     * Then create connection with data base.
     */
    public void open() throws SQLException{      
        //Open the database
        String myPath = DB_PATH;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);  
    }
    
    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * By calling this method and empty database will be created into the default system path
     * of your application so we are gonna be able to overwrite that database with our database.
     * */
    public void createDataBase() {
        //check if the database exist
        boolean databaseExist=checkDataBase();
       
        if(databaseExist){
            // By calling this method here onUpgrade will be called on a
            // writable database, but only if the version number has been increased

        	 // Do Nothing.
      	 this.getWritableDatabase();
      	//onUpgrade(myDataBase,1,2);
      	//open();
      	//String upgradeQuery = "ALTER TABLE tbl_item ADD COLUMN Testing TEXT";
   	    //myDataBase.execSQL(upgradeQuery);
   	    //System.out.println("Column added");
   	    //close();
      	System.out.println("do someting over here");
        }
        
        databaseExist=checkDataBase();
        if(!databaseExist){
        	
        	System.out.println("create brand new DB");
            this.getReadableDatabase(); 
            
            try {
                copyDataBase();
                
            } catch (IOException e) {

                e.printStackTrace();
                throw new Error("Error copying database");

            }
          
        }// end if else dbExist
    } // end createDataBase().

    
    public static void DumpMYDb(){
        //Dump Database DemoHHReplication
        File f=new File(DB_PATH);
        FileInputStream fis=null;
        FileOutputStream fos=null;

        try
        {
            fis=new FileInputStream(f);
            fos=new FileOutputStream("/mnt/sdcard/OrderDB");
            while(true)
            {
                int i=fis.read();
                if(i!=-1)
                {fos.write(i);}
                else
                {break;}
            }
            fos.flush();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fos.close();
                fis.close();
            }
            catch(Exception ioe)
            {}
        }

    }

    
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
        File databaseFile = new File(DB_PATH);
        return databaseFile.exists();        
    }

    /**
      * Copies your database from your local assets-folder to the just created empty database in the
      * system folder, from where it can be accessed and handled.
      * This is done by transfering bytestream.
      * */
    private void copyDataBase() throws IOException{

            //Open your local db as the input stream
            InputStream myInput = myContext.getAssets().open(DB_NAME);

            // Path to the just created empty db
            String outFileName = DB_PATH ;
            File databaseFile = new File( DB_PATH);
             // check if databases folder exists, if not create one and its subfolders
            if (!databaseFile.exists()){
                databaseFile.mkdir();
            }

            //Open the empty db as the output stream
            OutputStream myOutput = new FileOutputStream(outFileName);

            //transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
            }

            //Close the streams
            myOutput.flush();
            myOutput.close();
            myInput.close();
    }

    public List<CustomItem> getList(int userId) throws SQLException {

		List<CustomItem> _list=new ArrayList<CustomItem>();
		Cursor c=null;
		try{
			
			 ///c = myDataBase.rawQuery("SELECT * FROM tbl_item WHERE createdBy="+userId+" ORDER BY addedDateTime DESC" , null);
			 c = myDataBase.rawQuery("SELECT * FROM tbl_item ORDER BY addedDateTime DESC" , null);
				
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
		myDataBase.insert("tbl_item", null, values);
	}
	
	
	public void insertOrderItem(int itemId,
			String ItemName,
			String itemPrice,
			String itemQuntity,
			String itemAmount,
			String addedDateTime,
			String createdBy,
			String Pack,
			String ColorCode){
		
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
		myDataBase.insert("tbl_item", null, values);
	}
	
	public void insertTask(int orderMasterId,String ordernumber,String orderAmount,
			String state,String remark,String customerName,String UpdatedBy,
			String UpdatedDTTM,String createdBy,String orderstage,String CreatedDate){
		ContentValues values = new ContentValues();
		values.put("orderMasterId", orderMasterId);
		values.put("ordernumber", ordernumber);
		values.put("orderAmount", orderAmount);
		values.put("state", state);
		values.put("remark", remark);
		values.put("customerName", customerName);
		values.put("UpdatedBy", UpdatedBy);
		values.put("UpdatedDTTM", UpdatedDTTM);
		values.put("createdBy", createdBy);
		values.put("orderstage", orderstage);
		values.put("CreatedDate", CreatedDate);
		System.out.println("records inserted");
		myDataBase.insert("tbl_task", null, values);
	}
	
	
	 public List<Order> getTaskListByDate(String fromDate,String toDate) throws SQLException {

			List<Order> _list=new ArrayList<Order>();
			Cursor c=null;
			try{
				
				c = myDataBase.query("tbl_task", null,
                      "CreatedDate between ? and ? ORDER BY CreatedDate ASC", 
                       new String[] {fromDate, toDate},
                       null, null, null);
				 
				if (c != null && c.moveToFirst()) {
					do{
						 System.out.println("Number: "+c.getString(1).toString());
						_list.add(new Order(c.getInt(0), 
								c.getString(1).toString(), 
								c.getString(2).toString(), 
								c.getString(3).toString(),
								c.getString(4).toString(), 
								c.getString(5).toString(), 
								c.getString(6).toString(), 
								c.getString(7).toString(),
								c.getString(8).toString(), 
								c.getString(9).toString(), 
								c.getString(10).toString()));
					}while(c.moveToNext());
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
				c.close();
				
			}finally{
				c.close();
			}
			System.out.println("count is: "+_list.size());
			return _list;	
		}
		
	 
	 public List<Order> getTaskListByOrderNumber(String orderNumber) throws SQLException {

			List<Order> _list=new ArrayList<Order>();
			Cursor c=null;
			try{
				
				 c = myDataBase.rawQuery("SELECT * FROM tbl_task WHERE ordernumber='"+orderNumber+"'" , null);
					
				/*c = myDataBase.query("tbl_task", null,
                   "ordernumber? ", 
                    new String[] {orderNumber},
                    null, null, null);*/
				 
				if (c != null && c.moveToFirst()) {
					do{
						 System.out.println("Number: "+c.getString(1).toString());
						_list.add(new Order(c.getInt(0), 
								c.getString(1).toString(), 
								c.getString(2).toString(), 
								c.getString(3).toString(),
								c.getString(4).toString(), 
								c.getString(5).toString(), 
								c.getString(6).toString(), 
								c.getString(7).toString(),
								c.getString(8).toString(), 
								c.getString(9).toString(), 
								c.getString(10).toString()));
					}while(c.moveToNext());
					
				}
				
			}catch(Exception e){
				e.printStackTrace();
				c.close();
				
			}finally{
				c.close();
			}
			System.out.println("count is: "+_list.size());
			return _list;	
		}
		
	 
	 
	  public List<TaskNumber> getTaskList() throws SQLException {

			List<TaskNumber> _list=new ArrayList<TaskNumber>();
			Cursor c=null;
			try{
				
				 c = myDataBase.rawQuery("SELECT ordernumber FROM tbl_task" , null);
				
				if (c != null && c.moveToFirst()) {
					do{
						 System.out.println("Number: "+c.getString(0).toString());
						_list.add(new TaskNumber(c.getString(0).toString()));
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
		return myDataBase.update("tbl_item", values, "itemId"
				+ "='" + ItemId + "'", null) > 0;
	}

	 public void removeItem(int id){
	        String string =String.valueOf(id);
	        System.out.println("item removed");
	        myDataBase.execSQL("DELETE FROM tbl_item WHERE itemId = '" + string + "'");
	    }
	 
	 public boolean checkItem(String itemName){
		  boolean flag=false;
		  Cursor c=null;
			try{
				
				 c = myDataBase.rawQuery("SELECT itemId FROM tbl_item WHERE itemName='"+itemName+"'" , null);
				
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
				 c = myDataBase.rawQuery("SELECT itemAmount FROM tbl_item " , null);
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
	 
	public boolean deletetbl_item() {

		return myDataBase.delete("tbl_item", null, null) > 0;
	}
	
	public boolean deletetbl_task() {

		return myDataBase.delete("tbl_task", null, null) > 0;
	}

	
    @Override
    public synchronized void close() {

        if(myDataBase != null)
        myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    	   System.out.println("onCreate called");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    
    	   System.out.println("onUpgrade called");
    	  /* String upgradeQuery = "ALTER TABLE tbl_item ADD COLUMN Testing2 TEXT";
    	    if (oldVersion == 1 && newVersion == 2)
    	         db.execSQL(upgradeQuery);
    	         System.out.println("Column added");
    	         onCreate(db);*/
    }

     //you to create adapters for your views.

}