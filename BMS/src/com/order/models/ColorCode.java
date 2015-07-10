package com.order.models;

public class ColorCode {

	private String Colourcode;
	private int ColourCodeid;

	public ColorCode(int ColourCodeid,String Colourcode) {
		this.ColourCodeid=ColourCodeid;
    this.Colourcode=Colourcode;
	}

	public void setColourcode(String Colourcode){
		this.Colourcode=Colourcode;
	}
	public String getColourcode() {
		return Colourcode;
	}

	public void setColourCodeid(int ColourCodeid){
		this.ColourCodeid=ColourCodeid;
	}
	
	public int getColourCodeid(){
		return ColourCodeid;
	}
}

