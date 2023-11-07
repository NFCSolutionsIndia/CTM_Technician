package com.ctm.technician.models.Checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PMAssetControlValueLists {

    @SerializedName("controlValueId")
    @Expose
    private int pmCheckControlId;

    @SerializedName("pmAssetCheckListDetId")
    @Expose
    private int pmAssetCheckListDetId;

    @SerializedName("controlType")
    @Expose
    private String controlValue;

    public int getPmCheckControlId() {
        return pmCheckControlId;
    }

    public void setPmCheckControlId(int pmCheckControlId) {
        this.pmCheckControlId = pmCheckControlId;
    }

    public String getControlValue() {
        return controlValue;
    }

    public void setControlValue(String controlValue) {
        this.controlValue = controlValue;
    }

    public int getPmAssetCheckListDetId() {
        return pmAssetCheckListDetId;
    }

    public void setPmAssetCheckListDetId(int pmAssetCheckListDetId) {
        this.pmAssetCheckListDetId = pmAssetCheckListDetId;
    }
}
