package com.order.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CustomItem implements Parcelable {
	private int Itemid;
	private String ItemName, CreatedBy, ItemPrice,itemQuntity,itemAmount,addedDateTime,Pack,ColorCode;

	public CustomItem(int itemid, String itemname, String itemPrice,String itemQuntity,String itemAmount,String addedDateTime,String createdBy,String Pack,String ColorCode) {
    this.Itemid=itemid;
    this.ItemName=itemname;
    this.CreatedBy=createdBy;
    this.ItemPrice=itemPrice;
    this.itemQuntity=itemQuntity;
    this.itemAmount=itemAmount;
    this.addedDateTime=addedDateTime;
    this.Pack=Pack;
    this.ColorCode=ColorCode;
 	}

	public void setItemId(int iid){
		this.Itemid=iid;
	}
	
	public int getItemId(){
		return Itemid;
	} 
	
	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String iname) {
		this.ItemName = iname;
	}
	
	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String CreatedBy) {
		this.CreatedBy = CreatedBy;
	}

	public String getItemPrice() {
		return ItemPrice;
	}

	public void setItemPrice(String ItemPrice) {
		this.ItemPrice = ItemPrice;
	}
	
	public String getItemQuantity() {
		return itemQuntity;
	}

	public void setItemQuantity(String itemQuntity) {
		this.itemQuntity = itemQuntity;
	}
	
	public String getItemAmount() {
		return itemAmount;
	}

	public void setItemAmount(String itemAmount) {
		this.itemAmount = itemAmount;
	}
	
	
	public void setItemPack(String Pack) {
		this.Pack = Pack;
	}
	
	public String getItemPack() {
		return Pack;
	}
	
	public void setItemColorCode(String ColorCode) {
		this.ColorCode = ColorCode;
	}
	
	public String getItemColorCode() {
		return ColorCode;
	}
	
	public String getItemaddedDate() {
		return addedDateTime;
	}

	public void setItemAddedDateRemark(String addedDateTime) {
		this.addedDateTime = addedDateTime;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		  dest.writeInt(this.Itemid);
		  dest.writeString(this.ItemName);
		  dest.writeString(this.ItemPrice);
		  dest.writeString(this.itemAmount);
		  dest.writeString(this.itemQuntity);
		  //dest.writeString(this.itemTotalAmount);	
	}
	
	public static final Parcelable.Creator<CustomItem> CREATOR
	 = new Parcelable.Creator<CustomItem>() {
	  public CustomItem createFromParcel(Parcel in) {
	   return new CustomItem(in);
	  }
	 
	  public CustomItem[] newArray(int size) {
	   return new CustomItem[size];
	  }
	 };
	  
	 private CustomItem(Parcel in) {
	  this.Itemid = in.readInt();
	  this.ItemName = in.readString();
	  this.ItemPrice = in.readString();
	  this.itemAmount = in.readString();
	  this.itemQuntity = in.readString();
	  //this.itemTotalAmount = in.readString();
	    }
	
	
}
	