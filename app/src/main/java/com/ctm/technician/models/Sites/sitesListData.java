package com.ctm.technician.models.Sites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class sitesListData {

    @SerializedName("siteUserId")
    @Expose
    private String siteUserId;


    @SerializedName("siteId")
    @Expose
    private String siteId;



    @SerializedName("siteUniqueId")
    @Expose
    private String siteUniqueId;



    @SerializedName("siteModel")
    @Expose
    private siteModelData siteModel;


    @SerializedName("userId")
    @Expose
    private String userId;


    @SerializedName("count")
    @Expose
    private int count;


    public String getSiteUserId() {
        return siteUserId;
    }

    public void setSiteUserId(String siteUserId) {
        this.siteUserId = siteUserId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public siteModelData getSiteModel() {
        return siteModel;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setSiteModel(siteModelData siteModel) {
        this.siteModel = siteModel;
    }


    public String getSiteUniqueId() {
        return siteUniqueId;
    }

    public void setSiteUniqueId(String siteUniqueId) {
        this.siteUniqueId = siteUniqueId;
    }
}
