package com.ctm.technician.Supervisoemodels.Plannedevents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Eventsitemodel {


    @SerializedName("statusCode")
    @Expose
    private int statusCode;


    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;


    @SerializedName("supervisorSiteList")
    @Expose
    private ArrayList<Techniciansitelistmodel> technicianListModels = null;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public ArrayList<Techniciansitelistmodel> getTechnicianListModels() {
        return technicianListModels;
    }

    public void setTechnicianListModels(ArrayList<Techniciansitelistmodel> technicianListModels) {
        this.technicianListModels = technicianListModels;
    }
}
