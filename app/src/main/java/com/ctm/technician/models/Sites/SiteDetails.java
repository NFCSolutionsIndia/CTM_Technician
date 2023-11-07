package com.ctm.technician.models.Sites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class SiteDetails {

    @SerializedName("siteId")
    @Expose
    private int siteId;

    @SerializedName("siteUniqueId")
    @Expose
    private String siteUniqueId;


    @SerializedName("siteName")
    @Expose
    private String siteName;


    @SerializedName("installationDate")
    @Expose
    private String installationDate;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("clientId")
    @Expose
    private int clientId;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("siteAssetData")
    @Expose
    private ArrayList<sitesAssetdata> sitesAssetdata = null;


    @SerializedName("siteCircle")
    @Expose
    private sitesCirculedata sitesCirculedata;


    public String getSiteUniqueId() {
        return siteUniqueId;
    }

    public void setSiteUniqueId(String siteUniqueId) {
        this.siteUniqueId = siteUniqueId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }


    public ArrayList<com.ctm.technician.models.Sites.sitesAssetdata> getSitesAssetdata() {
        return sitesAssetdata;
    }

    public void setSitesAssetdata(ArrayList<com.ctm.technician.models.Sites.sitesAssetdata> sitesAssetdata) {
        this.sitesAssetdata = sitesAssetdata;
    }


    public com.ctm.technician.models.Sites.sitesCirculedata getSitesCirculedata() {
        return sitesCirculedata;
    }

    public void setSitesCirculedata(com.ctm.technician.models.Sites.sitesCirculedata sitesCirculedata) {
        this.sitesCirculedata = sitesCirculedata;
    }
}
