package com.ctm.technician.models.Sites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assetdetailslist {

    @SerializedName("siteAssetDetailId")
    @Expose
    private int siteAssetDetailId;

    @SerializedName("siteAssetId")
    @Expose
    private int siteAssetId;

    @SerializedName("columnName")
    @Expose
    private String columnName;


    @SerializedName("columnValue")
    @Expose
    private String columnValue;

    public int getSiteAssetDetailId() {
        return siteAssetDetailId;
    }

    public void setSiteAssetDetailId(int siteAssetDetailId) {
        this.siteAssetDetailId = siteAssetDetailId;
    }

    public int getSiteAssetId() {
        return siteAssetId;
    }

    public void setSiteAssetId(int siteAssetId) {
        this.siteAssetId = siteAssetId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
}
