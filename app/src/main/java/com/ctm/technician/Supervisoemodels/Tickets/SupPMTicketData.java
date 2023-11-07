package com.ctm.technician.Supervisoemodels.Tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupPMTicketData {
    @SerializedName("pmTicketId")
    @Expose
    private int pmTicketId;

    @SerializedName("pmTypeId")
    @Expose
    private int pmTypeId;

    @SerializedName("siteId")
    @Expose
    private int siteId;

    @SerializedName("assetId")
    @Expose
    private int assetId;


    @SerializedName("siteName")
    @Expose
    private String siteName;
    @SerializedName("siteUniqueId")
    @Expose
    private String siteUniqueId;

    @SerializedName("dateAsPerPMPlanDate")
    @Expose
    private String dateAsPerPMPlan;

    @SerializedName("dateTimeOfSitePM")
    @Expose
    private String dateTimeOfSitePM;

    @SerializedName("assignedTo")
    @Expose
    private String assignedTo;

    @SerializedName("pmTicketStatus")
    @Expose
    private String pmTicketStatus;

    @SerializedName("pmTypeName")
    @Expose
    private String pmTypeName;

    @SerializedName("pmTicketStatusName")
    @Expose
    private String pmTicketStatusName;

    @SerializedName("recCreateDate")
    @Expose
    private String recCreateDate;
    @SerializedName("ticketType")
    @Expose
    private String ticketType;

    @SerializedName("ticketDescription")
    @Expose
    private String ticketDescription;

    @SerializedName("ticketHeading")
    @Expose
    private String ticketHeading;

    @SerializedName("priority")
    @Expose
    private String priority;



    public int getPmTicketId() {
        return pmTicketId;
    }

    public void setPmTicketId(int pmTicketId) {
        this.pmTicketId = pmTicketId;
    }

    public int getPmTypeId() {
        return pmTypeId;
    }

    public void setPmTypeId(int pmTypeId) {
        this.pmTypeId = pmTypeId;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getDateAsPerPMPlan() {
        return dateAsPerPMPlan;
    }

    public void setDateAsPerPMPlan(String dateAsPerPMPlan) {
        this.dateAsPerPMPlan = dateAsPerPMPlan;
    }

    public String getDateTimeOfSitePM() {
        return dateTimeOfSitePM;
    }

    public void setDateTimeOfSitePM(String dateTimeOfSitePM) {
        this.dateTimeOfSitePM = dateTimeOfSitePM;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getPmTicketStatus() {
        return pmTicketStatus;
    }

    public void setPmTicketStatus(String pmTicketStatus) {
        this.pmTicketStatus = pmTicketStatus;
    }

    public String getPmTicketStatusName() {
        return pmTicketStatusName;
    }

    public void setPmTicketStatusName(String pmTicketStatusName) {
        this.pmTicketStatusName = pmTicketStatusName;
    }

    public String getRecCreateDate() {
        return recCreateDate;
    }

    public void setRecCreateDate(String recCreateDate) {
        this.recCreateDate = recCreateDate;
    }

    public String getPmTypeName() {
        return pmTypeName;
    }

    public void setPmTypeName(String pmTypeName) {
        this.pmTypeName = pmTypeName;
    }


    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketHeading() {
        return ticketHeading;
    }

    public void setTicketHeading(String ticketHeading) {
        this.ticketHeading = ticketHeading;
    }


    public String getSiteUniqueId() {
        return siteUniqueId;
    }

    public void setSiteUniqueId(String siteUniqueId) {
        this.siteUniqueId = siteUniqueId;
    }


    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
