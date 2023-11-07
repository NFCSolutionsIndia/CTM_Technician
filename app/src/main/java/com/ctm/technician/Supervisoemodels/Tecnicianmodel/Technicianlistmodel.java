package com.ctm.technician.Supervisoemodels.Tecnicianmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Technicianlistmodel {

    @SerializedName("siteCount")
    @Expose
    private int siteCount;



    @SerializedName("technicianId")
    @Expose
    private int technicianId;

    @SerializedName("contact")
    @Expose
    private String contact;

    @SerializedName("userName")
    @Expose
    private String userName;


    public int getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(int siteCount) {
        this.siteCount = siteCount;
    }

    public int getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }



}
