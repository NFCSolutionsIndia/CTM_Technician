package com.ctm.technician.models.Checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PMAssetsubmitdata {

    @SerializedName("pmTicketDetailId")
    @Expose
    private int pmTicketDetailId;

    @SerializedName("PMAssetCheckStandardValue")
    @Expose
    private String PMAssetCheckStandardValue;

    public int getPmTicketDetailId() {
        return pmTicketDetailId;
    }

    public void setPmTicketDetailId(int pmTicketDetailId) {
        this.pmTicketDetailId = pmTicketDetailId;
    }

    public String getPMAssetCheckStandardValue() {
        return PMAssetCheckStandardValue;
    }

    public void setPMAssetCheckStandardValue(String PMAssetCheckStandardValue) {
        this.PMAssetCheckStandardValue = PMAssetCheckStandardValue;
    }


}
