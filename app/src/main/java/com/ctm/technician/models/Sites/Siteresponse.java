package com.ctm.technician.models.Sites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Siteresponse {


    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;


    @SerializedName("statusCode")
    @Expose
    private int statusCode;

    @SerializedName("ClientId")
    @Expose
    private int ClientId;
    @SerializedName("userId")
    @Expose
    private int userId;


    @SerializedName("count")
    @Expose
    private String count;




    @SerializedName("siteData")
    @Expose
    private ArrayList<sitesListData> sitesListData = null;


    @SerializedName("siteImages")
    @Expose
    private siteImages siteImages;



    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public ArrayList<com.ctm.technician.models.Sites.sitesListData> getSitesListData() {
        return sitesListData;
    }

    public void setSitesListData(ArrayList<com.ctm.technician.models.Sites.sitesListData> sitesListData) {
        this.sitesListData = sitesListData;
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public siteImages getSiteImages() {
        return siteImages;
    }

    public void setSiteImages(siteImages siteImages) {
        this.siteImages = siteImages;
    }



}
