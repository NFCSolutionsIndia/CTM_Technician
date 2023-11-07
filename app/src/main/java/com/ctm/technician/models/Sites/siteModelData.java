package com.ctm.technician.models.Sites;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class siteModelData {

    @SerializedName("siteUniqueId")
    @Expose
    private String siteUniqueId;


    @SerializedName("siteName")
    @Expose
    private String siteName;

    @SerializedName("siteCircule")
    @Expose
    private String siteCircule;


    @SerializedName("installation")
    @Expose
    private String installationDate;



  @SerializedName("photo")
    @Expose
    private String photo;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("clientId")
    @Expose
    private String clientId;

    @SerializedName("siteId")
    @Expose
    private String siteId;

    @SerializedName("ticketsCount")
    @Expose
    private String ticketsCount;




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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }


    public String getSiteCircule() {
        return siteCircule;
    }

    public void setSiteCircule(String siteCircule) {
        this.siteCircule = siteCircule;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public String getTicketsCount() {
        return ticketsCount;
    }

    public void setTicketsCount(String ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}
