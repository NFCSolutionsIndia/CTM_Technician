package com.ctm.technician.apis;

import com.ctm.technician.Supervisoemodels.Plannedevents.Eventsitemodel;
import com.ctm.technician.Supervisoemodels.Plannedevents.Prioritymodel;
import com.ctm.technician.Supervisoemodels.Plannedevents.Submitpemodel;
import com.ctm.technician.Supervisoemodels.Tecnicianmodel.TechdataPojoResponse;
import com.ctm.technician.Supervisoemodels.Tickets.SupTicketsPojoResponse;
import com.ctm.technician.models.Checklist.Checklistsubmit;
import com.ctm.technician.models.Checklist.PEChecklistsubmit;
import com.ctm.technician.models.Checklist.PMTicketdetails;
import com.ctm.technician.models.Checklist.PMTicketssubmitlist;
import com.ctm.technician.models.Checklist.UpdatePmticket;
import com.ctm.technician.models.Login.LoginPojoResponse;
import com.ctm.technician.models.Sites.SiteDetails;
import com.ctm.technician.models.Sites.Siteresponse;
import com.ctm.technician.models.Sites.sitesAssetdetails;
import com.ctm.technician.models.Tickets.Checklistvalue;
import com.ctm.technician.models.Tickets.TicketsPojoResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("IdentityAPI/Login")
    Call<LoginPojoResponse> getLoginDetails(@Body LoginPojoResponse res);

    @POST("api/GetSiteList")
    Call<Siteresponse> getsitelist(@Header("Authorization") String token, @Body Siteresponse res);

    @POST("api/GetSiteDetails")
    Call<SiteDetails> getsitedetals(@Header("Authorization") String token, @Body SiteDetails res);

    @POST("api/GetAssetsDetails")
    Call<sitesAssetdetails> getassetdetals(@Header("Authorization") String token, @Body sitesAssetdetails res);

    @POST("api/GetPMTicketList")
    Call<TicketsPojoResponse> getpmticketdata(@Header("Authorization") String token, @Body TicketsPojoResponse res);

    @POST("api/GetClosePMTicketListDateWise")
    Call<TicketsPojoResponse> getClosePMTicketListDateWise(@Header("Authorization") String token, @Body TicketsPojoResponse res);

    @POST("api/GetSitePMTicketList")
    Call<TicketsPojoResponse> getsitepmticketlist(@Header("Authorization") String token, @Body TicketsPojoResponse res);

    @POST("api/GetPMTicketCheckList")
    Call<PMTicketssubmitlist> getpmticketchecklistdata(@Header("Authorization") String token, @Body Checklistvalue res);

    @POST("api/UpdatePMTicketCheckValue")
    Call<UpdatePmticket> updatepmticket(@Header("Authorization") String token, @Body Checklistsubmit res);

    @POST("api/UpdatePETicket")
    Call<UpdatePmticket> UpdatePETicke(@Header("Authorization") String token, @Body PEChecklistsubmit res);

    @POST("api/GetSupervisorPMTicketList")
    Call<SupTicketsPojoResponse> getsuppmticketdata(@Header("Authorization") String token, @Body SupTicketsPojoResponse res);

    @POST("api/GetTechnicianList")
    Call<TechdataPojoResponse> gettechnicianlist(@Header("Authorization") String token, @Body SupTicketsPojoResponse res);

    @POST("api/GetPMTicketDetails")
    Call<PMTicketdetails> getPMTicketDetails(@Header("Authorization") String token, @Body Checklistvalue res);

    @POST("api/GetTechnicianSiteList")
    Call<Eventsitemodel> getTechniciansiteslist(@Header("Authorization") String token, @Body SupTicketsPojoResponse res);

    @POST("api/GetTicketPriority")
    Call<Prioritymodel> getpriority(@Header("Authorization") String token, @Body SupTicketsPojoResponse res);

    @POST("api/CreatePETickets")
    Call<UpdatePmticket> Submitplannedevent(@Header("Authorization") String token, @Body Submitpemodel res);


}












