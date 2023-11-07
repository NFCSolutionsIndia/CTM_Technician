package com.ctm.technician.models.Checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Checklistsubmit {

    @SerializedName("PMTicketId")
    @Expose
    private int pmTicketId;


    @SerializedName("UserId")
    @Expose
    private int UserId;

    @SerializedName("Type")
    @Expose
    private int Type;

    @SerializedName("AssetId")
    @Expose
    private int AssetId;

    @SerializedName("Comments")
    @Expose
    private String Comments;

    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("PMTicketCheckListData")
    @Expose
    private ArrayList<PMAssetsubmitdata> pmAssetsControlValueLists = null;

    public int getPmTicketId() {
        return pmTicketId;
    }

    public void setPmTicketId(int pmTicketId) {
        this.pmTicketId = pmTicketId;
    }

    public ArrayList<PMAssetsubmitdata> getPmAssetsControlValueLists() {
        return pmAssetsControlValueLists;
    }

    public void setPmAssetsControlValueLists(ArrayList<PMAssetsubmitdata> pmAssetsControlValueLists) {
        this.pmAssetsControlValueLists = pmAssetsControlValueLists;
    }


    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }


    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }


    public int getAssetId() {
        return AssetId;
    }

    public void setAssetId(int assetId) {
        AssetId = assetId;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }


    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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
