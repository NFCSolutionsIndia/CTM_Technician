package com.ctm.technician.SupervisorActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import com.ctm.technician.R;
import com.ctm.technician.Supervisoemodels.Tickets.SupPMTechnicianData;
import com.ctm.technician.Supervisoemodels.Tickets.SupTicketsPojoResponse;
import com.ctm.technician.Supervisoradapter.ComplaincetiketAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Complaincetickets extends AppCompatActivity implements View.OnClickListener {
    ImageView backImage;
    Spinner sort;
    TextView header;
    RelativeLayout layout2;
    private RecyclerView siterecyclerview;
    RecyclerView.LayoutManager mLayoutManager;
    private ComplaincetiketAdapter todayAdapter;
    private List<SupPMTechnicianData> list = new ArrayList<>();
    Call<SupTicketsPojoResponse> barberOrdersResponseCall;
    private CommonFunctions com;
    ScrollView scrolllayout;
    private CommonSharePrefrences comShare;
    String complainceid = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaince);
        init();
        initializeOnClickListeners();
    }

    private void init() {
        layout2 = (RelativeLayout) findViewById(R.id.layout2);
        scrolllayout = (ScrollView) findViewById(R.id.scrolllayout);
        backImage = (ImageView) findViewById(R.id.back_image);
        header = (TextView) findViewById(R.id.header);
        sort = (Spinner) findViewById(R.id.sort_button);
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);
        siterecyclerview = (RecyclerView) findViewById(R.id.siterecyclerview);
        mLayoutManager = new LinearLayoutManager(this);
        siterecyclerview.setLayoutManager(mLayoutManager);
        siterecyclerview.setItemAnimator(new DefaultItemAnimator());
        siterecyclerview.setLayoutManager(new LinearLayoutManager(this));
        siterecyclerview.setHasFixedSize(true);
        siterecyclerview.setNestedScrollingEnabled(false);
        list.clear();

        todayAdapter = new ComplaincetiketAdapter(this, list);

        siterecyclerview.setAdapter(todayAdapter);
        List<String> list = new ArrayList<String>();

        list.add("Sort by");
        list.add("Site1");
        list.add("Site2");
        list.add("Site3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(dataAdapter);

        complainceid = getIntent().getStringExtra("complaineid");

        if (complainceid.equals("0")) {
            header.setText("PM Compliance Sites");
        } else {
            header.setText("PM Non Compliance Sites");
        }
        sitetickets();
    }


    private void initializeOnClickListeners() {
        backImage.setOnClickListener(Complaincetickets.this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void sitetickets() {

        if (com.isConnected()) {
            com.showProgressDialogue();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            SupTicketsPojoResponse response = new SupTicketsPojoResponse();
            response.setUserId(Integer.parseInt(comShare.getuserid()));
            response.setType(2);
            barberOrdersResponseCall = apiService.getsuppmticketdata(comShare.gettoken(), response);
            barberOrdersResponseCall.enqueue(new Callback<SupTicketsPojoResponse>() {
                @Override
                public void onResponse(Call<SupTicketsPojoResponse> call, Response<SupTicketsPojoResponse> response) {
                    com.dismissProgressDialogue();
                    int statuscode = response.code();
                    if (statuscode == 200) {
                        parseBarberOrders(response.body());
                    } else {
                        com.dismissProgressDialogue();
                    }
                }

                @Override
                public void onFailure(Call<SupTicketsPojoResponse> call, Throwable t) {
                }
            });
        } else {
            com.showinternetdialog();
        }
    }

    private void parseBarberOrders(SupTicketsPojoResponse barberOrdersResponse) {
        if (barberOrdersResponse.getStatusCode() == 200) {
            if (barberOrdersResponse.getPMTechnicianData().size() != 0) {


                if (complainceid.equals("0")) {
                    for (int i = 0; i < barberOrdersResponse.getPMTechnicianData().size(); i++) {
                        if (barberOrdersResponse.getPMTechnicianData().get(i).getPMTicketData().getPmTicketStatusName().equalsIgnoreCase("Closed")) {
                           if(barberOrdersResponse.getPMTechnicianData().get(i).getPMTicketData().getTicketType().equals("PM")) {

                               list.add(barberOrdersResponse.getPMTechnicianData().get(i));
                           }
                        }
                        todayAdapter.notifyDataSetChanged();
                        siterecyclerview.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.GONE);
                        scrolllayout.setVisibility(View.VISIBLE);
                    }

                } else {

                    for (int i = 0; i < barberOrdersResponse.getPMTechnicianData().size(); i++) {
                        if (barberOrdersResponse.getPMTechnicianData().get(i).getPMTicketData().getPmTicketStatusName().equalsIgnoreCase("Open") ||
                                barberOrdersResponse.getPMTechnicianData().get(i).getPMTicketData().getPmTicketStatusName().equalsIgnoreCase("Reassign") ||
                                barberOrdersResponse.getPMTechnicianData().get(i).getPMTicketData().getPmTicketStatusName().equalsIgnoreCase("overdue") ||
                                barberOrdersResponse.getPMTechnicianData().get(i).getPMTicketData().getPmTicketStatusName().equalsIgnoreCase("pending")) {
                            if(barberOrdersResponse.getPMTechnicianData().get(i).getPMTicketData().getTicketType().equals("PM")) {

                                list.add(barberOrdersResponse.getPMTechnicianData().get(i));
                            }                        }
                        todayAdapter.notifyDataSetChanged();
                        siterecyclerview.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.GONE);
                        scrolllayout.setVisibility(View.VISIBLE);
                    }
                }


                if (list.size() == 0) {
                    siterecyclerview.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                    scrolllayout.setVisibility(View.GONE);
                }


            } else {
                siterecyclerview.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                scrolllayout.setVisibility(View.GONE);
            }
        } else {
            siterecyclerview.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            scrolllayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent1 = new Intent(this, DashboardActivity.class);
        startActivity(intent1);
        finish();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                Intent intent1 = new Intent(this, DashboardActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}
