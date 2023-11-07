package com.ctm.technician.models.Checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketHistoryList {


    @SerializedName("pmHistoryId")
    @Expose
    private int pmHistoryId;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("assignedTo")
    @Expose
    private String assignedTo;

    @SerializedName("recCreateDate")
    @Expose
    private String recCreateDate;
    @SerializedName("comments")
    @Expose
    private String comments;

    public int getPmHistoryId() {
        return pmHistoryId;
    }

    public void setPmHistoryId(int pmHistoryId) {
        this.pmHistoryId = pmHistoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getRecCreateDate() {
        return recCreateDate;
    }

    public void setRecCreateDate(String recCreateDate) {
        this.recCreateDate = recCreateDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
