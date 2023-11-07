package com.ctm.technician.Supervisoemodels.Tickets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupPMTechnicianData {


    @SerializedName("technicianId")
    @Expose
    private int technicianId;

     @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("contact")
    @Expose
    private String contact;

    @SerializedName("pMTicketResponse")
    @Expose
    private SupPMTicketData PMTicketData;




    public int getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public SupPMTicketData getPMTicketData() {
        return PMTicketData;
    }

    public void setPMTicketData(SupPMTicketData PMTicketData) {
        this.PMTicketData = PMTicketData;
    }
}
