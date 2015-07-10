
package com.order.models;

import com.google.gson.annotations.SerializedName;

/**
 * Model for MobileUser business entity returned by the Web API service.
 */
public class MobileUser {
	
    @SerializedName("userid")
    public int userid;

    @SerializedName("username")
    public String username;

    @SerializedName("userRollId")
    public int userRollId;

    @SerializedName("userRolename")
    public String userRolename;

}
