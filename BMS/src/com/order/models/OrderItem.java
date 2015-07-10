package com.order.models;

public class OrderItem {
	private int Itemid;
	private String ItemName, CreatedBy, ItemPrice, CreatedDTTM;

	public OrderItem(int itemid, String itemname, String createdBy, String itemPrice) {
    this.Itemid=itemid;
    this.ItemName=itemname;
    this.CreatedBy=createdBy;
    this.ItemPrice=itemPrice;
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
	
	public String getCreatedDTTM() {
		return CreatedDTTM;
	}

	public void setCreatedDTTM(String CreatedDTTM) {
		this.CreatedDTTM = CreatedDTTM;
	}
	
}
