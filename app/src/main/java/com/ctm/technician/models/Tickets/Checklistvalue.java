package com.ctm.technician.models.Tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checklistvalue {

    @SerializedName("PMTicketId")
    @Expose
    private int PMTicketId;

    @SerializedName("AssetId")
    @Expose
    private int AssetId;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("latitude")
    @Expose
    private String latitude;


    public int getPMTicketId() {
        return PMTicketId;
    }

    public void setPMTicketId(int PMTicketId) {
        this.PMTicketId = PMTicketId;
    }

    public int getAssetId() {
        return AssetId;
    }

    public void setAssetId(int assetId) {
        AssetId = assetId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
