package com.ctm.technician.models.Tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TicketsPojoResponse {


    @SerializedName("statusCode")
    @Expose
    private int statusCode;


    @SerializedName("count")
    @Expose
    private int count;

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

    @SerializedName("userId")
    @Expose
    private int userId;

    @SerializedName("SiteId")
    @Expose
    private int SiteId;

    @SerializedName("AssetId")
    @Expose
    private int AssetId;


    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;

    @SerializedName("startDate")
    @Expose
    private String fromDate;

    @SerializedName("endDate")
    @Expose
    private String toDate;


    @SerializedName("pmTicketData")
    @Expose
    private ArrayList<PMTicketData> pmticketData = null;


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

    public ArrayList<PMTicketData> getPmticketData() {
        return pmticketData;
    }

    public void setPmticketData(ArrayList<PMTicketData> pmticketData) {
        this.pmticketData = pmticketData;
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


    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
