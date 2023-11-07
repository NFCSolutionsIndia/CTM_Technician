package com.ctm.technician.models.Checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PMTicketssubmitlist {

    @SerializedName("statusCode")
    @Expose
    private int statusCode;

    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;

    @SerializedName("count")
    @Expose
    private int count;
    @SerializedName("distance")
    @Expose
    private String distance;

    @SerializedName("pmAssetsControl")
    @Expose
    private ArrayList<PMAssetControl> pmCheckLists = null;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<PMAssetControl> getPmCheckLists() {
        return pmCheckLists;
    }

    public void setPmCheckLists(ArrayList<PMAssetControl> pmCheckLists) {
        this.pmCheckLists = pmCheckLists;
    }


    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}



