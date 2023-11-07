package com.ctm.technician.models.Sites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class sitesAssetdetails {

    @SerializedName("siteAssetDetailId")
    @Expose
    private int siteAssetDetailId;
    @SerializedName("siteAssetId")
    @Expose
    private int siteAssetId;

    @SerializedName("assetMake")
    @Expose
    private String assetMake;


    @SerializedName("assetSerialNumber")
    @Expose
    private String assetSerialNumber;

    @SerializedName("warrantyDate")
    @Expose
    private String warrantyDate;


    @SerializedName("ageingHours")
    @Expose
    private String ageingHours;


    @SerializedName("pmFrequencyHours")
    @Expose
    private String pmFrequencyHours;

    @SerializedName("assetDetailsListData")
    @Expose
    private ArrayList<Assetdetailslist> assetDetailsListData = null;




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

    public String getAssetMake() {
        return assetMake;
    }

    public void setAssetMake(String assetMake) {
        this.assetMake = assetMake;
    }


    public String getAssetSerialNumber() {
        return assetSerialNumber;
    }

    public void setAssetSerialNumber(String assetSerialNumber) {
        this.assetSerialNumber = assetSerialNumber;
    }


    public String getWarrantyDate() {
        return warrantyDate;
    }

    public void setWarrantyDate(String warrantyDate) {
        this.warrantyDate = warrantyDate;
    }


    public String getAgeingHours() {
        return ageingHours;
    }

    public void setAgeingHours(String ageingHours) {
        this.ageingHours = ageingHours;
    }


    public String getPmFrequencyHours() {
        return pmFrequencyHours;
    }

    public void setPmFrequencyHours(String pmFrequencyHours) {
        this.pmFrequencyHours = pmFrequencyHours;
    }

    public ArrayList<Assetdetailslist> getAssetDetailsListData() {
        return assetDetailsListData;
    }

    public void setAssetDetailsListData(ArrayList<Assetdetailslist> assetDetailsListData) {
        this.assetDetailsListData = assetDetailsListData;
    }
}
