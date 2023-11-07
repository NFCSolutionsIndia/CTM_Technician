package com.ctm.technician.Supervisoemodels.Plannedevents;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ticketproritymodel {


    @SerializedName("priorityId")
    @Expose
    private int priorityId;


    @SerializedName("priority")
    @Expose
    private String priority;

    public int getPriorityId() {
        return priorityId;
    }

    public void setPriorityId(int priorityId) {
        this.priorityId = priorityId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
