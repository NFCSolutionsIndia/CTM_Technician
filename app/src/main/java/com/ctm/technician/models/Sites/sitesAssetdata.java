package com.ctm.technician.models.Sites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class sitesAssetdata {


    @SerializedName("siteAssetId")
    @Expose
    private int siteAssetId;


    @SerializedName("assetCheckListStatus")
    @Expose
    private int assetCheckListStatus;


    @SerializedName("siteAssetName")
    @Expose
    private String siteAssetName;


    @SerializedName("siteId")
    @Expose
    private String siteId;


    @SerializedName("assestImage")
    @Expose
    private assertImage assestImage;



    public int getSiteAssetId() {
        return siteAssetId;
    }

    public void setSiteAssetId(int siteAssetId) {
        this.siteAssetId = siteAssetId;
    }

    public String getSiteAssetName() {
        return siteAssetName;
    }

    public void setSiteAssetName(String siteAssetName) {
        this.siteAssetName = siteAssetName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getAssetCheckListStatus() {
        return assetCheckListStatus;
    }

    public void setAssetCheckListStatus(int assetCheckListStatus) {
        this.assetCheckListStatus = assetCheckListStatus;
    }


    public assertImage getAssestImage() {
        return assestImage;
    }

    public void setAssestImage(assertImage assestImage) {
        this.assestImage = assestImage;
    }
}
