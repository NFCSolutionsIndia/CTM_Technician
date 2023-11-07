package com.ctm.technician.SupervisorActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.technician.R;
import com.ctm.technician.SuccessActivity;
import com.ctm.technician.Supervisoradapter.SupChecklistAssetsListAdapter;
import com.ctm.technician.Supervisoradapter.SupReasonListAdapter;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupChecklistAssetsActivity extends Activity implements View.OnClickListener, onItemClick {
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    private String comment = "";
    int buttontype;
    private SupReasonListAdapter reasonAdapter;
    private RecyclerView recyclerView, recyclerview2;
    private SupChecklistAssetsListAdapter assetsListAdapter;
    int numberOfColumns = 2;
    Button btnapprove, btnreassign;
    TextView browse, radiustext, labelrason, techname, techcontact, nodata, headingtext, inputid, plandate, lastpmdate, input_sitename, inputsite_id, heading, status, sitedescription, input_date;
    String sitename, site_uniqueid, cretaeddate, header, description, lastdate, pmplandate, tcstatus, tech_contact, tech_name;
    int ticketid, typeid, siteid;
    private LinearLayout addServices, linearbtn, tickethistory, listtickets, layoutreason;
    View view1, historyv1;
    EditText reassignreason;
    LinearLayout linearsite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supassetchecklistcreen);
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
        layoutreason = (LinearLayout) findViewById(R.id.layoutreason);
        labelrason = (TextView) findViewById(R.id.labelrason);
        radiustext = (TextView) findViewById(R.id.radiustext);

        addServices.removeAllViews();
        siteid = Integer.parseInt(getIntent().getStringExtra("site_id"));


        ticketid = Integer.parseInt(getIntent().getStringExtra("ticket_id"));
        typeid = Integer.parseInt(getIntent().getStringExtra("type_id"));


        tech_contact = getIntent().getStringExtra("tech_contact");
        tech_name = getIntent().getStringExtra("tech_name");
        site_uniqueid = getIntent().getStringExtra("site_uniqueid");


        sitename = getIntent().getStringExtra("site_name");
        site_uniqueid = getIntent().getStringExtra("site_uniqueid");


        tech_contact = getIntent().getStringExtra("tech_contact");
        tech_name = getIntent().getStringExtra("tech_name");


        cretaeddate = getIntent().getStringExtra("created_date");
        header = getIntent().getStringExtra("heading");
        description = getIntent().getStringExtra("description");
        lastdate = getIntent().getStringExtra("lastpmdate");
        pmplandate = getIntent().getStringExtra("pmplandate");
        tcstatus = getIntent().getStringExtra("status");



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
      /*  pm.setVisibility(View.GONE);
        lastpmdate.setVisibility(View.GONE);*/
        input_date.setText(cretaeddate);
        status.setText("\u25CF " + tcstatus);
        heading.setText(header);
        techcontact.setText(tech_contact);
        techname.setText(tech_name);

//        if (tcstatus.equals("Closed") || tcstatus.equals("Reassign") || tcstatus.equals("Open")) {
//            linearbtn.setVisibility(View.GONE);
//        } else {
//            linearbtn.setVisibility(View.VISIBLE);
//        }



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

        getsitedetails();


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
        addServices.removeAllViews();

        if (body.getPMAssertslist().size() != 0) {

            if (tcstatus.equals("Pending") || tcstatus.equals("Closed")) {
                linearsite.setVisibility(View.VISIBLE);
                radiustext.setText("Technician submitted the ticket at " + body.getDistance() + " away from site radius");

            } else {
                linearsite.setVisibility(View.GONE);
            }

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
            recyclerView.setVisibility(View.VISIBLE);
            assetsListAdapter = new SupChecklistAssetsListAdapter(body.getPMAssertslist(), this, tech_name, tech_contact, tcstatus, siteid, sitename, site_uniqueid, comShare, ticketid, typeid, SupChecklistAssetsActivity.this);
            recyclerView.setAdapter(assetsListAdapter);

            recyclerview2.setVisibility(View.VISIBLE);
            reasonAdapter = new SupReasonListAdapter(reverseListOrder(body.getTicketHistoryList()), this, tcstatus, siteid, sitename, comShare, ticketid, typeid, SupChecklistAssetsActivity.this);
            recyclerview2.setAdapter(reasonAdapter);


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
            dialog.getWindow().setLayout(com.getWidth() - 100, LinearLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            TextView txtCustomAlert_OK = (TextView) dialog.findViewById(R.id.txt_CustomAlert_OK);
            final EditText txtCustomAlert_Comments = (EditText) dialog.findViewById(R.id.dialogue_comments);
            ImageView close = (ImageView) dialog.findViewById(R.id.closereassign);


            txtCustomAlert_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    comment = txtCustomAlert_Comments.getText().toString().trim();
                    if (!comment.equals("")) {
                        buttontype = 4;
                        dialog.dismiss();
                        Checklistsubmit();
                    } else {
                        Toast.makeText(SupChecklistAssetsActivity.this, "Reason cannot be empty", Toast.LENGTH_SHORT).show();
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

            Checklistsubmit response = new Checklistsubmit();
            response.setCreatedDate(formattedDate);

            if (buttontype == 3) {
                response.setPmTicketId(ticketid);
                response.setUserId(Integer.parseInt(comShare.getuserid()));
                response.setType(3);
            } else if (buttontype == 4) {
                response.setPmTicketId(ticketid);
                response.setUserId(Integer.parseInt(comShare.getuserid()));
                response.setType(4);
                response.setComments(comment);

            }


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
