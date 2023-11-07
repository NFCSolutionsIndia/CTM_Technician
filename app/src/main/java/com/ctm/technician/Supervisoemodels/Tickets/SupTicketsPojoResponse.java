package com.ctm.technician.Supervisoemodels.Tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SupTicketsPojoResponse {

    @SerializedName("statusCode")
    @Expose
    private int statusCode;


    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("type")
    @Expose
    private int type;

    @SerializedName("openCount")
    @Expose
    private int openCount;

    @SerializedName("closedCount")
    @Expose
    private int closedCount;

    @SerializedName("overdueCount")
    @Expose
    private int overdueCount;

    @SerializedName("pendingCount")
    @Expose
    private int pendingCount;
    @SerializedName("compliantCount")
    @Expose
    private int compliantCount;
    @SerializedName("nonCompliantCount")
    @Expose
    private int nonCompliantCount;


    @SerializedName("SiteId")
    @Expose
    private int SiteId;

    @SerializedName("AssetId")
    @Expose
    private int AssetId;

    @SerializedName("userId")
    @Expose
    private int userId;
    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;


    @SerializedName("pmTicketData")
    @Expose
    private ArrayList<SupPMTechnicianData> PMTechnicianData = null;


    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOpenCount() {
        return openCount;
    }

    public void setOpenCount(int openCount) {
        this.openCount = openCount;
    }

    public int getClosedCount() {
        return closedCount;
    }

    public void setClosedCount(int closedCount) {
        this.closedCount = closedCount;
    }

    public int getOverdueCount() {
        return overdueCount;
    }

    public void setOverdueCount(int overdueCount) {
        this.overdueCount = overdueCount;
    }

    public int getPendingCount() {
        return pendingCount;
    }

    public void setPendingCount(int pendingCount) {
        this.pendingCount = pendingCount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public ArrayList<SupPMTechnicianData> getPMTechnicianData() {
        return PMTechnicianData;
    }

    public void setPMTechnicianData(ArrayList<SupPMTechnicianData> PMTechnicianData) {
        this.PMTechnicianData = PMTechnicianData;
    }

    public int getSiteId() {
        return SiteId;
    }

    public void setSiteId(int siteId) {
        SiteId = siteId;
    }


    public int getAssetId() {
        return AssetId;
    }

    public void setAssetId(int assetId) {
        AssetId = assetId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getCompliantCount() {
        return compliantCount;
    }

    public void setCompliantCount(int compliantCount) {
        this.compliantCount = compliantCount;
    }

    public int getNonCompliantCount() {
        return nonCompliantCount;
    }

    public void setNonCompliantCount(int nonCompliantCount) {
        this.nonCompliantCount = nonCompliantCount;
    }
}
