package com.ctm.technician.models.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LogininfoResponse {

    @SerializedName("contactNumber")
    @Expose
    private String contactNumber;

    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("designation")
    @Expose
    private String designation;

    @SerializedName("userId")
    @Expose
    private int userId;


    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
