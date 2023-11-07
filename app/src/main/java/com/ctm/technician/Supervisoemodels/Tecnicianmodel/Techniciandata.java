package com.ctm.technician.Supervisoemodels.Tecnicianmodel;

import com.ctm.technician.Supervisoemodels.Tickets.SupPMTechnicianData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Techniciandata {




    @SerializedName("statusCode")
    @Expose
    private int statusCode;



    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;




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


}
