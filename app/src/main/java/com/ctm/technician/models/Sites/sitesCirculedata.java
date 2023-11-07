package com.ctm.technician.models.Sites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class sitesCirculedata {


    @SerializedName("cityId")
    @Expose
    private int cityId;

    @SerializedName("circleId")
    @Expose
    private int circleId;

    @SerializedName("clusterId")
    @Expose
    private String clusterId;

    @SerializedName("clusterName")
    @Expose
    private String clusterName;

    @SerializedName("circleName")
    @Expose
    private String circleName;

    @SerializedName("cityName")
    @Expose
    private String cityName;

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCircleId() {
        return circleId;
    }

    public void setCircleId(int circleId) {
        this.circleId = circleId;
    }

    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getCircleName() {
        return circleName;
    }

    public void setCircleName(String circleName) {
        this.circleName = circleName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
