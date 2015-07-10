package com.order.models;


/**
 * Created by Manish on 03/17/2015.
 */
public class Stages {

    String name = null;
    int id;
    boolean selected = false;

    public Stages(int eid,String name, boolean selected) {
        super();
        this.id=eid;
        this.name = name;
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}

