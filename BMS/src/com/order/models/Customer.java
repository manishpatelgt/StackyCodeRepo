package com.order.models;

public class Customer {
	private int Userid;
	private String UserName, FirstName, PhoneNo, UserRole;

	public Customer(int uid, String uname, String fname, String phone, String role) {
    this.Userid=uid;
    this.UserName=uname;
    this.FirstName=fname;
    this.PhoneNo=phone;
    this.UserRole=role;
	}

	public void setUserId(int uid){
		this.Userid=uid;
	}
	
	public int getUserId(){
		return Userid;
	} 
	
	public String getUserName() {
		return UserName;
	}

	public void setUserName(String uname) {
		this.UserName = uname;
	}
	
	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String fname) {
		this.FirstName = fname;
	}

	public String getPhoneNo() {
		return PhoneNo;
	}

	public void setPhoneNo(String phone) {
		this.PhoneNo = phone;
	}
	
	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String role) {
		this.UserRole = role;
	}
}
