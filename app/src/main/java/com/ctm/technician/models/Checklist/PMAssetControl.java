package com.ctm.technician.models.Checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class PMAssetControl {

    @SerializedName("pmAssetCheckListDetailId")
    @Expose
    private int pmAssetCheckListDetailId;


    @SerializedName("pmTicketDetailId")
    @Expose
    private int pmTicketDetailId;



    @SerializedName("pmAssetCheckLabel")
    @Expose
    private String pmAssetCheckLabel;


    @SerializedName("controlType")
    @Expose
    private String controlType;


    @SerializedName("pmAssetCheckStandardValue")
    @Expose
    private String pmAssetCheckStandardValue;


   @SerializedName("pmAssetsControlValueLists")
    @Expose
    private ArrayList<PMAssetControlValueLists> pmAssetsControlValueLists = null;


    public int getPmAssetCheckListDetailId() {
        return pmAssetCheckListDetailId;
    }

    public void setPmAssetCheckListDetailId(int pmAssetCheckListDetailId) {
        this.pmAssetCheckListDetailId = pmAssetCheckListDetailId;
    }

    public String getPmAssetCheckLabel() {
        return pmAssetCheckLabel;
    }

    public void setPmAssetCheckLabel(String pmAssetCheckLabel) {
        this.pmAssetCheckLabel = pmAssetCheckLabel;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }


    public String getPmAssetCheckStandardValue() {
        return pmAssetCheckStandardValue;
    }

    public void setPmAssetCheckStandardValue(String pmAssetCheckStandardValue) {
        this.pmAssetCheckStandardValue = pmAssetCheckStandardValue;
    }

    public ArrayList<PMAssetControlValueLists> getPmAssetsControlValueLists() {
        return pmAssetsControlValueLists;
    }

    public int getPmTicketDetailId() {
        return pmTicketDetailId;
    }

    public void setPmTicketDetailId(int pmTicketDetailId) {
        this.pmTicketDetailId = pmTicketDetailId;
    }
}
