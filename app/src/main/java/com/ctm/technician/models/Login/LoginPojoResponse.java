package com.ctm.technician.models.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginPojoResponse {
    @SerializedName("apiStatus")
    @Expose
    private LogindetailsResponse logindetails;

    @SerializedName("loginInfo")
    @Expose
    private LogininfoResponse logininfo;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("ContactNumber")
    @Expose
    private String ContactNumber;


    @SerializedName("Password")
    @Expose
    private String Password;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public LogindetailsResponse getLogindetails() {
        return logindetails;
    }

    public void setLogindetails(LogindetailsResponse logindetails) {
        this.logindetails = logindetails;

    }


    public LogininfoResponse getLogininfo() {
        return logininfo;
    }

    public void setLogininfo(LogininfoResponse logininfo) {
        this.logininfo = logininfo;
    }


}
