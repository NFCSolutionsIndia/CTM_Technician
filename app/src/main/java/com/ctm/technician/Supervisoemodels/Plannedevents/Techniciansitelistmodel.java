package com.ctm.technician.Supervisoemodels.Plannedevents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Techniciansitelistmodel {


    @SerializedName("siteId")
    @Expose
    private int siteId;

 @SerializedName("technicianId")
    @Expose
    private int technicianId;


    @SerializedName("siteName")
    @Expose
    private String siteName;

    @SerializedName("technicianName")
    @Expose
    private String technicianName;


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


    public int getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(int technicianId) {
        this.technicianId = technicianId;
    }
}
