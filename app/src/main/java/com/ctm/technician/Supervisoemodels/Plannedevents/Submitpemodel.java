package com.ctm.technician.Supervisoemodels.Plannedevents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Submitpemodel {


    @SerializedName("siteId")
    @Expose
    private int siteId;
  @SerializedName("PriorityId")
    @Expose
    private int PriorityId;

    @SerializedName("siteName")
    @Expose
    private String siteName;

    @SerializedName("technicianName")
    @Expose
    private String technicianName;


    @SerializedName("TicketHeading")
    @Expose
    private String TicketHeading;

    @SerializedName("DateAsPerPMPlan")
    @Expose
    private String DateAsPerPMPlan;

    @SerializedName("RecCreateDate")
    @Expose
    private String RecCreateDate;

    @SerializedName("TicketDescription")
    @Expose
    private String TicketDescription;


    @SerializedName("AssignedTo")
    @Expose
    private int AssignedTo;

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }


    public String getDateAsPerPMPlan() {
        return DateAsPerPMPlan;
    }

    public void setDateAsPerPMPlan(String dateAsPerPMPlan) {
        DateAsPerPMPlan = dateAsPerPMPlan;
    }

    public int getAssignedTo() {
        return AssignedTo;
    }

    public void setAssignedTo(int assignedTo) {
        AssignedTo = assignedTo;
    }


    public String getTicketHeading() {
        return TicketHeading;
    }

    public void setTicketHeading(String ticketHeading) {
        TicketHeading = ticketHeading;
    }

    public String getRecCreateDate() {
        return RecCreateDate;
    }

    public void setRecCreateDate(String recCreateDate) {
        RecCreateDate = recCreateDate;
    }

    public String getTicketDescription() {
        return TicketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        TicketDescription = ticketDescription;
    }

    public int getPriorityId() {
        return PriorityId;
    }

    public void setPriorityId(int priorityId) {
        PriorityId = priorityId;
    }
}
