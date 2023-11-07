package com.ctm.technician.models.Checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PEChecklistsubmit {

    @SerializedName("PMTicketId")
    @Expose
    private int pmTicketId;

    @SerializedName("UserId")
    @Expose
    private int UserId;

    @SerializedName("Comments")
    @Expose
    private String Comments;

    @SerializedName("Photo")
    @Expose
    private String Photo;

    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    @SerializedName("Type")
    @Expose
    private int Type;
    @SerializedName("longitude")
    @Expose
    private String longitude;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    public int getPmTicketId() {
        return pmTicketId;
    }

    public void setPmTicketId(int pmTicketId) {
        this.pmTicketId = pmTicketId;
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

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
