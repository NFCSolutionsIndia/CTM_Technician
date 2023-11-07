package com.ctm.technician;

import android.Manifest;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.technician.adapter.ChecklistAssetsListAdapter;
import com.ctm.technician.adapter.ReasonListAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Checklist.Checklistsubmit;
import com.ctm.technician.models.Checklist.PMTicketdetails;
import com.ctm.technician.models.Checklist.TicketHistoryList;
import com.ctm.technician.models.Checklist.UpdatePmticket;
import com.ctm.technician.models.Tickets.Checklistvalue;
import com.ctm.technician.utils.onItemClick;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChecklistAssetsActivity extends AppCompatActivity implements View.OnClickListener, onItemClick, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    private RecyclerView recyclerView, recyclerview2;
    private ChecklistAssetsListAdapter assetsListAdapter;
    private ReasonListAdapter reasonAdapter;
    int numberOfColumns = 2;
    Button btnsubmit;
    EditText reassignreason;
    View historyv1;
    ImageView dot;
    TextView labelrason, radiustext, browse, nodata, headingtext, inputid, plandate, lastpmdate, input_sitename, inputsite_id, heading, status, sitedescription, input_date;
    String sitename, site_uniqueid, cretaeddate, header, description, lastdate, pmplandate, tcstatus, tech_contact, techname;
    int ticketid, typeid, siteid;
    private LinearLayout linearsite, checklist, radiusbtn, addServices, linearbtn, layoutreason, tickethistory, listtickets;
    RelativeLayout r1;
    String btnenable = "false", siteuniqueid;
    private static final int PERMISSION_ACCESS_COARSE_LOCATION = 0;

    String latitude1, longitude1;
    boolean gps_enabled = false;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assetchecklistcreen);
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);
        initializeViews();
        initializeOnClickListeners();
        LocationManager lm1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm1.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        if (!gps_enabled) {
            showAlertDialogOK(getResources().getString(R.string.nogps));
            return;
        } else {
            try {
                GPSTracker gps = new GPSTracker(ChecklistAssetsActivity.this);
                if (gps.canGetLocation()) {
                    latitude1 = String.valueOf(gps.getLatitude());
                    longitude1 = String.valueOf(gps.getLongitude());
                    getsitedetails();

                } else {
                    showAlertDialogOK(getResources().getString(R.string.nogps));
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeViews() {
        backImage = (ImageView) findViewById(R.id.back_image);
        historyv1 = (View) findViewById(R.id.historyv1);

        addServices = (LinearLayout) findViewById(R.id.add_layout);
        checklist = (LinearLayout) findViewById(R.id.checklist);
        radiusbtn = (LinearLayout) findViewById(R.id.radiusbtn);
        linearsite = (LinearLayout) findViewById(R.id.linearsite);
        linearbtn = (LinearLayout) findViewById(R.id.linearbtn);
        reassignreason = (EditText) findViewById(R.id.reassignreason);
        layoutreason = (LinearLayout) findViewById(R.id.layoutreason);
        tickethistory = (LinearLayout) findViewById(R.id.tickethistory);
        listtickets = (LinearLayout) findViewById(R.id.listtickets);
        r1 = (RelativeLayout) findViewById(R.id.r1);
        labelrason = (TextView) findViewById(R.id.labelrason);
        dot = (ImageView) findViewById(R.id.dot);
        addServices.removeAllViews();
        siteid = Integer.parseInt(getIntent().getStringExtra("site_id"));


        ticketid = Integer.parseInt(getIntent().getStringExtra("ticket_id"));
        typeid = Integer.parseInt(getIntent().getStringExtra("type_id"));


        sitename = getIntent().getStringExtra("site_name");
        siteuniqueid = getIntent().getStringExtra("site_uniqueid");


        cretaeddate = getIntent().getStringExtra("created_date");
        header = getIntent().getStringExtra("heading");
        description = getIntent().getStringExtra("description");
        lastdate = getIntent().getStringExtra("lastpmdate");
        pmplandate = getIntent().getStringExtra("pmplandate");
        tcstatus = getIntent().getStringExtra("status");


        inputid = (TextView) findViewById(R.id.input_id);

        radiustext = (TextView) findViewById(R.id.radiustext);

        plandate = (TextView) findViewById(R.id.pmplandate);
        lastpmdate = (TextView) findViewById(R.id.lastpmdate);
        input_sitename = (TextView) findViewById(R.id.input_sitename);
        heading = (TextView) findViewById(R.id.heading);
        status = (TextView) findViewById(R.id.status);
        sitedescription = (TextView) findViewById(R.id.description);
        input_date = (TextView) findViewById(R.id.input_date);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);

        inputid.setText("#" + ticketid);
        input_sitename.setText("#" + siteuniqueid + "," + sitename);
        sitedescription.setText(description);
        plandate.setText(pmplandate);
        lastpmdate.setText(lastdate);
      /*  pm.setVisibility(View.GONE);
        lastpmdate.setVisibility(View.GONE);*/


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION);
        }


       /* radiusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnenable.equals("true")) {
                    checklist.setVisibility(View.VISIBLE);
                    radiusbtn.setVisibility(View.GONE);
                    r1.setVisibility(View.VISIBLE);
                    linearsite.setVisibility(View.GONE);
                } else {
                    linearsite.setVisibility(View.VISIBLE);
                }
            }
        });*/
        checklist.setVisibility(View.VISIBLE);
        radiusbtn.setVisibility(View.GONE);
        r1.setVisibility(View.VISIBLE);
        linearsite.setVisibility(View.VISIBLE);


        input_date.setText(cretaeddate);

        status.setText("\u25CF " + tcstatus);
        heading.setText(header);
        if (tcstatus.equals("Reassign")) {
            status.setText("\u25CF " + "Re-Assign");

        }


        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview2 = (RecyclerView) findViewById(R.id.recyclerview2);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview2.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview2.setHasFixedSize(true);
        recyclerview2.setNestedScrollingEnabled(false);


    }

    private void initializeOnClickListeners() {
        backImage.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
    }


    private void getsitedetails() {

        if (com.isConnected()) {

            com.showProgressDialogue();


            Checklistvalue response1 = new Checklistvalue();

            response1.setPMTicketId(ticketid);
            response1.setLatitude("" + latitude1);
            response1.setLongitude("" + longitude1);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<PMTicketdetails> call = apiService.getPMTicketDetails(comShare.gettoken(), response1);
            call.enqueue(new Callback<PMTicketdetails>() {
                @Override
                public void onResponse(Call<PMTicketdetails> call, Response<PMTicketdetails> response) {
                    com.dismissProgressDialogue();
                    if (response.code() == 200) {
                        parsesitedetailsResponse(response.body());
                    } else {
                        com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                    }
                }

                @Override
                public void onFailure(Call<PMTicketdetails> call, Throwable t) {
                    com.dismissProgressDialogue();

                    com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                }
            });
        } else {
            com.showinternetdialog();
        }
    }

    private List<TicketHistoryList> reverseListOrder(List<TicketHistoryList> status) {
        Iterator<TicketHistoryList> it = status.iterator();
        List<TicketHistoryList> destination = new ArrayList<>();
        while (it.hasNext()) {
            destination.add(0, it.next());
            it.remove();
        }
        return destination;
    }

    private void parsesitedetailsResponse(PMTicketdetails body) {

        if (body.getPMAssertslist().size() != 0) {

            recyclerView.setVisibility(View.VISIBLE);
            String distance = "" + body.getDistance();
            assetsListAdapter = new ChecklistAssetsListAdapter(body.getPMAssertslist(), this, tcstatus, siteid, sitename, siteuniqueid, comShare, ticketid, typeid, distance, ChecklistAssetsActivity.this);
            recyclerView.setAdapter(assetsListAdapter);


            if (tcstatus.equals("Pending") || tcstatus.equals("Closed")) {
                linearsite.setVisibility(View.VISIBLE);

                radiustext.setText("You have submitted the ticket at " + body.getDistance() + " away from site radius");

            } else {
                linearsite.setVisibility(View.VISIBLE);
                radiustext.setText("You are " + distance + " away from site radius");
            }


            dot.setVisibility(View.VISIBLE);
            if (body.getEnable().equals("true")) {
                radiusbtn.setVisibility(View.VISIBLE);

            }

            btnenable = body.getEnable();
            recyclerview2.setVisibility(View.VISIBLE);
            reasonAdapter = new ReasonListAdapter(reverseListOrder(body.getTicketHistoryList()), this, tcstatus, siteid, sitename, comShare, ticketid, typeid, ChecklistAssetsActivity.this);
            recyclerview2.setAdapter(reasonAdapter);


            for (int i = 0; i < body.getPMAssertslist().size(); i++) {


                if (tcstatus.equals("Open") || tcstatus.equals("Reassign") || tcstatus.equals("Overdue")) {
                    if (body.getPMAssertslist().get(i).getAssetCheckListStatus() != 1) {
                        // btnsubmit.setVisibility(View.GONE);
                        linearbtn.setVisibility(View.VISIBLE);

                        btnsubmit.setBackground(getResources().getDrawable(R.color.light_gray));
                        linearbtn.setBackground(getResources().getDrawable(R.color.light_gray));
                        btnsubmit.setTextColor(getResources().getColor(R.color.black));
                        btnsubmit.setSelected(false);
                        btnsubmit.setClickable(false);
                        btnsubmit.setVisibility(View.VISIBLE);

                        return;
                    } else {
                       /* btnsubmit.setVisibility(View.VISIBLE);
                        linearbtn.setVisibility(View.VISIBLE);
*/
                        linearbtn.setVisibility(View.VISIBLE);

                        btnsubmit.setBackground(getResources().getDrawable(R.drawable.btngradiantheader));
                        linearbtn.setBackground(getResources().getDrawable(R.drawable.btngradiantheader));
                        btnsubmit.setTextColor(getResources().getColor(R.color.white));
                        btnsubmit.setSelected(true);
                        btnsubmit.setClickable(true);
                        btnsubmit.setVisibility(View.VISIBLE);


                    }
                } else {
                    btnsubmit.setVisibility(View.GONE);
                    linearbtn.setVisibility(View.GONE);


                }
            }

            addServices.removeAllViews();


            if (!body.getComments().equals("")) {
                reassignreason.setText(body.getComments());

                labelrason.setVisibility(View.VISIBLE);
                layoutreason.setVisibility(View.VISIBLE);
                reassignreason.setVisibility(View.VISIBLE);
            } else {
                layoutreason.setVisibility(View.GONE);
                labelrason.setVisibility(View.GONE);
                reassignreason.setVisibility(View.GONE);
            }



            if (tcstatus.equals("Open")) {
                tickethistory.setVisibility(View.GONE);
                listtickets.setVisibility(View.GONE);
                historyv1.setVisibility(View.GONE);
            }
            else{
                tickethistory.setVisibility(View.VISIBLE);
                listtickets.setVisibility(View.VISIBLE);
                historyv1.setVisibility(View.VISIBLE);
            }

        } else {
            recyclerView.setVisibility(View.GONE);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getsitedetails();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:

                finish();
                break;
            case R.id.btnsubmit:
                Checklistsubmit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    private void Checklistsubmit() {

        if (Networking.isNetworkAvailable(this)) {


            if (!gps_enabled) {
                com.showAlertDialogOK(getResources().getString(R.string.nogps));
                return;
            } else {
                GPSTracker gps = new GPSTracker(this);
                if (gps.canGetLocation()) {
                    latitude1 = String.valueOf(gps.getLatitude());
                    longitude1 = String.valueOf(gps.getLongitude());
                } else {
                    com.showAlertDialogOK(getResources().getString(R.string.nogps));
                    return;
                }
            }

            com.showProgressDialogue();


            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());


            Checklistsubmit response = new Checklistsubmit();

            response.setPmTicketId(ticketid);
            response.setUserId(Integer.parseInt(comShare.getuserid()));
            response.setCreatedDate(formattedDate);
            response.setType(2);
            response.setLatitude("" + latitude1);
            response.setLongitude("" + longitude1);

            response.setPmAssetsControlValueLists(com.datalist);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


            Call<UpdatePmticket> getMyServicesResponseCall = apiService.updatepmticket(comShare.gettoken(), response);
            getMyServicesResponseCall.enqueue(new Callback<UpdatePmticket>() {
                @Override
                public void onResponse(Call<UpdatePmticket> call, Response<UpdatePmticket> response) {
                    UpdatePmticket sitesResponse = response.body();
                    int statusCode = response.code();
                    com.dismissProgressDialogue();

                    if (statusCode == 200) {
                        parsesubmitresponse(sitesResponse);

                    } else {

                        com.showAlertDialogOK(getResources().getString(R.string.something));
                    }
                }

                @Override
                public void onFailure(Call<UpdatePmticket> call, Throwable t) {
                    com.dismissProgressDialogue();
                    com.showAlertDialogOK(getResources().getString(R.string.strErrorMessage));
                }
            });
        } else {

            com.showinternetdialog();

        }
    }


    private void parsesubmitresponse(UpdatePmticket barberOrdersResponse) {
        if (barberOrdersResponse.getStatusCode() == 1) {
            Intent i = new Intent(this, SuccessActivity.class);
            i.putExtra("submit_id", "" + ticketid);
            i.putExtra("submit_message", String.valueOf(barberOrdersResponse.getMessage()));

            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, barberOrdersResponse.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void OnItemClick() {


    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public int getWidth() {
        Display display = ((Activity) this).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        return screen_width;
    }

    private Dialog showAlertDialogOK(String message) {
        final Dialog dialog = new Dialog(this);
        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_alert);
            dialog.getWindow().setLayout(getWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setGravity(Gravity.BOTTOM);

            LinearLayout linearLayoutYesNo = (LinearLayout) dialog.findViewById(R.id.linearLayout_CustomAlert_YesNo);
            LinearLayout linearLayoutOK = (LinearLayout) dialog.findViewById(R.id.linearLayout_CustomAlert_OK);
            TextView txtCustomAlert_Heading = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Heading);
            TextView txtCustomAlert_Message = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Message);
            TextView txtCustomAlert_Yes = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Yes);
            TextView txtCustomAlert_No = (TextView) dialog.findViewById(R.id.txt_CustomAlert_No);
            TextView txtCustomAlert_OK = (TextView) dialog.findViewById(R.id.txt_CustomAlert_OK);

            txtCustomAlert_Heading.setText(getString(R.string.app_name) + " " + getString(R.string.strAlertTitle));
            txtCustomAlert_Message.setText(message);


            linearLayoutYesNo.setVisibility(View.GONE);
            linearLayoutOK.setVisibility(View.VISIBLE);
            txtCustomAlert_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    finish();

                }
            });
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return dialog;
    }


}
