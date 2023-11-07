package com.ctm.technician.models.Checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PMTicketdetails {


    @SerializedName("pmTicketId")
    @Expose
    private int pmTicketId;

    @SerializedName("comments")
    @Expose
    private String comments;

    @SerializedName("techComment")
    @Expose
    private String techComment;

    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("commentPhoto")
    @Expose
    private String commentPhoto;

    @SerializedName("enable")
    @Expose
    private String enable;

    @SerializedName("assetsList")
    @Expose
    private ArrayList<PMAssetdata> PMAssertslist;

    @SerializedName("ticketHistoryList")
    @Expose
    private ArrayList<TicketHistoryList> ticketHistoryList;

    public int getPmTicketId() {
        return pmTicketId;
    }

    public void setPmTicketId(int pmTicketId) {
        this.pmTicketId = pmTicketId;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ArrayList<PMAssetdata> getPMAssertslist() {
        return PMAssertslist;
    }

    public void setPMAssertslist(ArrayList<PMAssetdata> PMAssertslist) {
        this.PMAssertslist = PMAssertslist;
    }

    public ArrayList<TicketHistoryList> getTicketHistoryList() {
        return ticketHistoryList;
    }

    public void setTicketHistoryList(ArrayList<TicketHistoryList> ticketHistoryList) {
        this.ticketHistoryList = ticketHistoryList;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    public String getTechComment() {
        return techComment;
    }

    public void setTechComment(String techComment) {
        this.techComment = techComment;
    }


    public String getCommentPhoto() {
        return commentPhoto;
    }

    public void setCommentPhoto(String commentPhoto) {
        this.commentPhoto = commentPhoto;
    }
}



