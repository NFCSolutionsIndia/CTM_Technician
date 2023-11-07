package com.ctm.technician.Supervisoemodels.Tecnicianmodel;

import com.ctm.technician.Supervisoemodels.Tickets.SupPMTechnicianData;
import com.ctm.technician.Supervisoemodels.Tickets.SupPMTicketData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TechdataPojoResponse {


    @SerializedName("apiStatus")
    @Expose
    private Techniciandata apiStatus;
    @SerializedName("technicianListModels")
    @Expose
    private ArrayList<Technicianlistmodel> technicianListModels = null;


    public Techniciandata getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Techniciandata apiStatus) {
        this.apiStatus = apiStatus;
    }


    public ArrayList<Technicianlistmodel> getTechnicianListModels() {
        return technicianListModels;
    }

    public void setTechnicianListModels(ArrayList<Technicianlistmodel> technicianListModels) {
        this.technicianListModels = technicianListModels;
    }
}
