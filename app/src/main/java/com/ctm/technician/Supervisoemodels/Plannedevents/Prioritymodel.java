package com.ctm.technician.Supervisoemodels.Plannedevents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Prioritymodel {

    @SerializedName("statusCode")
    @Expose
    private int statusCode;


    @SerializedName("statusMessage")
    @Expose
    private String statusMessage;


    @SerializedName("ticketPriorityModel")
    @Expose
    private ArrayList<Ticketproritymodel> ticketPriorityModel = null;

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

    public ArrayList<Ticketproritymodel> getTicketPriorityModel() {
        return ticketPriorityModel;
    }

    public void setTicketPriorityModel(ArrayList<Ticketproritymodel> ticketPriorityModel) {
        this.ticketPriorityModel = ticketPriorityModel;
    }
}
