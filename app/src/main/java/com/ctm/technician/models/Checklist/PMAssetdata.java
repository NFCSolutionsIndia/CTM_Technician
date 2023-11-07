package com.ctm.technician.models.Checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PMAssetdata {

    @SerializedName("siteAssetId")
    @Expose
    private int siteAssetId;

    @SerializedName("assetCheckListStatus")
    @Expose
    private int assetCheckListStatus;

    @SerializedName("siteAssetName")
    @Expose
    private String siteAssetName;

    @SerializedName("distance")
    @Expose
    private String distance;

    @SerializedName("photo")
    @Expose
    private String photo;

    public int getSiteAssetId() {
        return siteAssetId;
    }

    public void setSiteAssetId(int siteAssetId) {
        this.siteAssetId = siteAssetId;
    }

    public int getAssetCheckListStatus() {
        return assetCheckListStatus;
    }

    public void setAssetCheckListStatus(int assetCheckListStatus) {
        this.assetCheckListStatus = assetCheckListStatus;
    }

    public String getSiteAssetName() {
        return siteAssetName;
    }

    public void setSiteAssetName(String siteAssetName) {
        this.siteAssetName = siteAssetName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
