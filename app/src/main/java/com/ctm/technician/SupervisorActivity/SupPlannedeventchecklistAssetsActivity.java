package com.ctm.technician.SupervisorActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ctm.technician.GPSTracker;
import com.ctm.technician.LocationActivity;
import com.ctm.technician.R;
import com.ctm.technician.SuccessActivity;
import com.ctm.technician.Supervisoradapter.SupPMReasonListAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Checklist.PEChecklistsubmit;
import com.ctm.technician.models.Checklist.PMTicketdetails;
import com.ctm.technician.models.Checklist.TicketHistoryList;
import com.ctm.technician.models.Checklist.UpdatePmticket;
import com.ctm.technician.models.Tickets.Checklistvalue;
import com.ctm.technician.utils.ConstantsHelper;
import com.ctm.technician.utils.onItemClick;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupPlannedeventchecklistAssetsActivity extends LocationActivity implements View.OnClickListener, onItemClick {
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    private String comment = "";
    int buttontype;
    private SupPMReasonListAdapter reasonAdapter;
    private RecyclerView recyclerview2;
    Button btnapprove, btnreassign;
    TextView techlabel, radiustext, labelrason, techname, techcontact, nodata, headingtext, inputid, plandate, lastpmdate, input_sitename, inputsite_id, heading, status, sitedescription, input_date;
    String sitename, site_uniqueid, cretaeddate, header, description, lastdate, pmplandate, tcstatus, tech_contact, tech_name;
    int ticketid, typeid, siteid;
    private LinearLayout addServices, linearbtn, tickethistory, listtickets, layoutreason;
    View view1, historyv1;
    EditText reassignreason, technicianreason;
    LinearLayout linearsite;
    ImageView img;
    String viewtechimage;
    protected GoogleApiClient mGoogleApiClient;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplannedeventassetchecklistcreen);
        initializeViews();
        initializeOnClickListeners();
    }

    private void initializeViews() {
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);
        backImage = (ImageView) findViewById(R.id.back_image);
        addServices = (LinearLayout) findViewById(R.id.add_layout);
        linearbtn = (LinearLayout) findViewById(R.id.linearbtn);
        linearsite = (LinearLayout) findViewById(R.id.linearsite);
        view1 = (View) findViewById(R.id.view1);
        historyv1 = (View) findViewById(R.id.historyv1);
        tickethistory = (LinearLayout) findViewById(R.id.tickethistory);
        listtickets = (LinearLayout) findViewById(R.id.listtickets);
        reassignreason = (EditText) findViewById(R.id.reassignreason);
        technicianreason = (EditText) findViewById(R.id.technicianreason);
        layoutreason = (LinearLayout) findViewById(R.id.layoutreason);
        labelrason = (TextView) findViewById(R.id.labelrason);
        radiustext = (TextView) findViewById(R.id.radiustext);
        techlabel = (TextView) findViewById(R.id.techlabel);
        img = (ImageView) findViewById(R.id.img);
        addServices.removeAllViews();
        siteid = Integer.parseInt(getIntent().getStringExtra("site_id"));
        ticketid = Integer.parseInt(getIntent().getStringExtra("ticket_id"));
        typeid = Integer.parseInt(getIntent().getStringExtra("type_id"));
        tech_contact = getIntent().getStringExtra("tech_contact");
        tech_name = getIntent().getStringExtra("tech_name");
        site_uniqueid = getIntent().getStringExtra("site_uniqueid");
        sitename = getIntent().getStringExtra("site_name");
        tech_contact = getIntent().getStringExtra("tech_contact");
        tech_name = getIntent().getStringExtra("tech_name");
        cretaeddate = getIntent().getStringExtra("created_date");
        header = getIntent().getStringExtra("heading");
        description = getIntent().getStringExtra("description");
        lastdate = getIntent().getStringExtra("lastpmdate");
        pmplandate = getIntent().getStringExtra("pmplandate");
        tcstatus = getIntent().getStringExtra("status");

//        if (tcstatus.equals("Closed") || tcstatus.equals("Reassign") || tcstatus.equals("Open")) {
//            linearbtn.setVisibility(View.GONE);
//        } else {
//            linearbtn.setVisibility(View.VISIBLE);
//        }

        inputid = (TextView) findViewById(R.id.input_id);
        plandate = (TextView) findViewById(R.id.pmplandate);
        lastpmdate = (TextView) findViewById(R.id.lastpmdate);
        input_sitename = (TextView) findViewById(R.id.input_sitename);
        heading = (TextView) findViewById(R.id.heading);
        status = (TextView) findViewById(R.id.status);
        sitedescription = (TextView) findViewById(R.id.description);
        input_date = (TextView) findViewById(R.id.input_date);
        techname = (TextView) findViewById(R.id.techname);
        techcontact = (TextView) findViewById(R.id.techcontact);
        btnreassign = (Button) findViewById(R.id.btnreassign);
        btnapprove = (Button) findViewById(R.id.btnapprove);

        inputid.setText("#" + ticketid);
        input_sitename.setText("#" + site_uniqueid + "," + sitename);
        sitedescription.setText(description);
        plandate.setText(pmplandate);
        lastpmdate.setText(lastdate);
      /* pm.setVisibility(View.GONE);
        lastpmdate.setVisibility(View.GONE);*/
        input_date.setText(cretaeddate);
        status.setText("\u25CF " + tcstatus);
        heading.setText(header);
        techcontact.setText(tech_contact);
        techname.setText(tech_name);

        if (tcstatus.equals("Pending")) {
            status.setTextColor(getResources().getColor(R.color.pendingtextcolor));
            view1.setBackgroundColor(getResources().getColor(R.color.pendingtextcolor));
        } else if (tcstatus.equals("Closed")) {
            status.setTextColor(getResources().getColor(R.color.closedtextcolor));
            view1.setBackgroundColor(getResources().getColor(R.color.closedtextcolor));
        } else if (tcstatus.equals("Overdue")) {
            status.setTextColor(getResources().getColor(R.color.overduetextcolor));
            view1.setBackgroundColor(getResources().getColor(R.color.overduetextcolor));
        } else if (tcstatus.equals("Open") || tcstatus.equals("Reassign")) {
            status.setTextColor(getResources().getColor(R.color.opentextcolor));
            view1.setBackgroundColor(getResources().getColor(R.color.opentextcolor));
            if (tcstatus.equals("Reassign")) {
                status.setText("\u25CF " + "Re-Assign");
            }
        }

        LocationManager lm1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = lm1.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled) {
            checkGpsStatus();
        } else {
            GPSTracker gps = new GPSTracker(this);
            if (gps.canGetLocation()) {
                latitude = String.valueOf(gps.getLatitude());
                longitude = String.valueOf(gps.getLongitude());
            } else {
                com.showAlertDialogOK("Location Not found");
                return;
            }
        }

        recyclerview2 = (RecyclerView) findViewById(R.id.recyclerview2);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview2.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview2.setHasFixedSize(true);
        recyclerview2.setNestedScrollingEnabled(false);
        getsitedetails();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );

                final Dialog settingsDialog = new Dialog(SupPlannedeventchecklistAssetsActivity.this);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.imageview, null));

                ImageView viewimg = (ImageView) settingsDialog.findViewById(R.id.viewimg);
                ImageView close = (ImageView) settingsDialog.findViewById(R.id.clolse);
                viewimg.setImageBitmap(ConstantsHelper.getBitmap(SupPlannedeventchecklistAssetsActivity.this, viewtechimage));

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingsDialog.cancel();
                    }
                });

                settingsDialog.show();
            }
        });
    }

    private void initializeOnClickListeners() {
        backImage.setOnClickListener(this);
        btnreassign.setOnClickListener(this);
        btnapprove.setOnClickListener(this);
    }

    private void getsitedetails() {
        if (com.isConnected()) {
            Checklistvalue response1 = new Checklistvalue();
            response1.setPMTicketId(ticketid);
            com.showProgressDialogue();
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



        try {
            if(body.getTicketHistoryList().size()==0){
                linearbtn.setVisibility(View.GONE);
                tickethistory.setVisibility(View.GONE);
                listtickets.setVisibility(View.GONE);
                historyv1.setVisibility(View.GONE);
            }else{
                tickethistory.setVisibility(View.VISIBLE);
                linearbtn.setVisibility(View.VISIBLE);
                listtickets.setVisibility(View.VISIBLE);
                historyv1.setVisibility(View.VISIBLE);
                if (tcstatus.equals("Closed") || tcstatus.equals("Reassign") || tcstatus.equals("Open") || tcstatus.equals("Overdue")) {
                    linearbtn.setVisibility(View.GONE);
                } else {
                    linearbtn.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }



        recyclerview2.setVisibility(View.VISIBLE);
        reasonAdapter = new SupPMReasonListAdapter(reverseListOrder(body.getTicketHistoryList()), this, tcstatus, siteid, sitename, comShare, ticketid, typeid, SupPlannedeventchecklistAssetsActivity.this);
        recyclerview2.setAdapter(reasonAdapter);


        if (tcstatus.equals("Pending") || tcstatus.equals("Closed")) {
            linearsite.setVisibility(View.VISIBLE);

            radiustext.setText("Technician submitted the ticket at " + body.getDistance() + " away from site radius");

        } else {
            linearsite.setVisibility(View.GONE);

        }
        if (TextUtils.isEmpty(body.getCommentPhoto())) {
            img.setImageResource(R.mipmap.rectimg);
        } else {
            img.setImageBitmap(ConstantsHelper.getBitmap(this, body.getCommentPhoto()));
            viewtechimage = body.getCommentPhoto();
        }

        if (body.getTechComment() != null) {

            technicianreason.setText(body.getTechComment());

        }




        if (tcstatus.equals("Closed")) {
            labelrason.setVisibility(View.GONE);
            layoutreason.setVisibility(View.GONE);
            reassignreason.setVisibility(View.GONE);
            linearbtn.setVisibility(View.GONE);


        }
        if (tcstatus.equals("Open")) {
            labelrason.setVisibility(View.GONE);
            layoutreason.setVisibility(View.GONE);
            reassignreason.setVisibility(View.GONE);

        }

        if (tcstatus.equals("Overdue")) {
            labelrason.setVisibility(View.GONE);
            layoutreason.setVisibility(View.GONE);
            reassignreason.setVisibility(View.GONE);

        }

        if (!body.getComments().equals("")) {
            reassignreason.setText(body.getComments());
            labelrason.setVisibility(View.VISIBLE);
            layoutreason.setVisibility(View.VISIBLE);
            reassignreason.setVisibility(View.VISIBLE);
            technicianreason.setVisibility(View.GONE);
            techlabel.setVisibility(View.GONE);
        } else {
            layoutreason.setVisibility(View.GONE);
            labelrason.setVisibility(View.GONE);
            reassignreason.setVisibility(View.GONE);
        }

        addServices.removeAllViews();


    }

    private void checkGpsStatus() {

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Creating LocationSettingsRequest.Builder
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        builder.setAlwaysShow(true); // this is the key ingredient

        //To check whether current location settings are satisfied:
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {  //Check the location settings from LocationSettingsResult

                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates LS_state = locationSettingsResult.getLocationSettingsStates(); //Retriving the current state of location by calling getLocationSettingsStates()
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        getsitedetails();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(SupPlannedeventchecklistAssetsActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
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
               /* Intent i = new Intent(this, SupervisorPMDashboard.class);
                startActivity((i));*/
                finish();
                break;
            case R.id.btnapprove:
                buttontype = 3;
                Checklistsubmit();
                break;
            case R.id.btnreassign:
                showAlertDialogOK();
                break;
        }
    }

    public Dialog showAlertDialogOK() {
        final Dialog dialog = new Dialog(this);
        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.alert_cancelapptmt);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(com.getWidth() - 100, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView txtCustomAlert_OK = (TextView) dialog.findViewById(R.id.txt_CustomAlert_OK);
            ImageView close = (ImageView) dialog.findViewById(R.id.closereassign);
            final EditText txtCustomAlert_Comments = (EditText) dialog.findViewById(R.id.dialogue_comments);
            txtCustomAlert_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    comment = txtCustomAlert_Comments.getText().toString().trim();
                    if (!comment.equals("")) {
                        buttontype = 4;
                        dialog.dismiss();
                        Checklistsubmit();
                    } else {
                        Toast.makeText(SupPlannedeventchecklistAssetsActivity.this, "Reason cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });
            dialog.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return dialog;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  Intent i = new Intent(this, SupervisorPMDashboard.class);
        startActivity((i));*/
        finish();
    }

    private void Checklistsubmit() {
        if (Networking.isNetworkAvailable(this)) {
            com.showProgressDialogue();
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());


            PEChecklistsubmit response = new PEChecklistsubmit();

            response.setPmTicketId(ticketid);
            response.setUserId(Integer.parseInt(comShare.getuserid()));
            response.setCreatedDate(formattedDate);

            if (buttontype == 3) {
                response.setType(2);

                response.setComments("");


            } else if (buttontype == 4) {

                response.setType(3);
                response.setComments(comment);
            }


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


            Call<UpdatePmticket> getMyServicesResponseCall = apiService.UpdatePETicke(comShare.gettoken(), response);
            getMyServicesResponseCall.enqueue(new Callback<UpdatePmticket>() {
                @Override
                public void onResponse(Call<UpdatePmticket> call, Response<UpdatePmticket> response) {
                    UpdatePmticket sitesResponse = response.body();
                    int statusCode = response.code();
                    com.dismissProgressDialogue();
                    if (statusCode == 200) {
                        parsesubmitresponse(sitesResponse);
                    } else {
                        com.dismissProgressDialogue();
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
}
