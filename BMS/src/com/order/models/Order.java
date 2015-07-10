package com.order.models;

public class Order {
	
	private int OrderMasterId;
	private String OrderNumber, CustomerName,State,Remark, UpdatedBy,UpdatedDTTM,CreatedBy,Orderstage,CreatedDateTime,OrderAmount;

	public Order(int orderMasterId, String OrderNumber, String OrderAmount, String state,String remark,String customerName,String UpdatedBy,String UpdatedDTTM,String createdBy,String Orderstage,String CreatedDateTime) {
    this.OrderMasterId=orderMasterId;
    this.OrderNumber=OrderNumber;
    this.State=state;
    this.Remark=remark;
    this.CustomerName=customerName;
    this.UpdatedBy=UpdatedBy;
    this.UpdatedDTTM=UpdatedDTTM;
    this.CreatedBy=createdBy;
    this.OrderAmount=OrderAmount;
    this.Orderstage=Orderstage;
    this.CreatedDateTime=CreatedDateTime;
 	}

	public void setOrderMasterId(int iid){
		this.OrderMasterId=iid;
	}
	
	public int getOrderMasterId(){
		return OrderMasterId;
	} 
	
	public String getOrderAmount() {
		return OrderAmount;
	}

	public void setOrderAmount(String OrderAmount) {
		this.OrderAmount = OrderAmount;
	}
	
	
	public String getOrderNumber() {
		return OrderNumber;
	}

	public void setOrderNumber(String onum) {
		this.OrderNumber = onum;
	}
	
	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String cname) {
		this.CustomerName = cname;
	}

	public String getRemark() {
		return Remark;
	}

	public void setRemark(String remark) {
		this.Remark = remark;
	}
	
	public String getState() {
		return State;
	}

	public void setState(String state) {
		this.State = state;
	}
	
	public String getUpdatedBy() {
		return UpdatedBy;
	}

	public void setUpdatedBy(String UpdatedBy) {
		this.UpdatedBy = UpdatedBy;
	}
	
	public String getUpdatedDTTM() {
		return UpdatedDTTM;
	}

	public void setUpdatedDTTM(String UpdatedDTTM) {
		this.UpdatedDTTM = UpdatedDTTM;
	}
	
	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String CreatedBy) {
		this.CreatedBy = CreatedBy;
	}
	
	public String getOrderstage() {
		return Orderstage;
	}

	public void setOrderstage(String Orderstage) {
		this.Orderstage = Orderstage;
	}
	
	public String getCreatedDateTime() {
		return CreatedDateTime;
	}

	public void setCreatedDateTime(String CreatedDateTime) {
		this.CreatedDateTime = CreatedDateTime;
	}
}
