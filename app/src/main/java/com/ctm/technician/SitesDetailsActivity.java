package com.ctm.technician;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ctm.technician.adapter.AssetsListAdapter;
import com.ctm.technician.adapter.TodayAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.models.Sites.SiteDetails;
import com.ctm.technician.models.Tickets.PMTicketData;
import com.ctm.technician.models.Tickets.TicketsPojoResponse;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SitesDetailsActivity extends Activity implements View.OnClickListener {
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    TextView sysgen, sitename, siteid, address, circle, cluster, dg, power, solar, siteassetname;
    private RecyclerView recyclerView, siterecyclerview;
    RecyclerView.LayoutManager mLayoutManager;
    private AssetsListAdapter assetsListAdapter;
    int site_id;
    private TodayAdapter todayAdapter;
    Call<TicketsPojoResponse> barberOrdersResponseCall;
    private List<PMTicketData> list = new ArrayList<>();
    int numberOfColumns = 3;
    TextView input_id, input_name, input_number, input_count, header;
    int technicianid, count;
    String techname, mobile, site_uniqid;
    LinearLayout technicianlayout;
    RelativeLayout appbar;
    LinearLayout pmticketheader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_site_details);
        initializeViews();
        initializeOnClickListeners();
    }

    private void initializeViews() {
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);
        backImage = (ImageView) findViewById(R.id.back_image);
        site_id = Integer.parseInt(getIntent().getStringExtra("site_id"));
        site_uniqid = getIntent().getStringExtra("site_uniqid");

        siteid = (TextView) findViewById(R.id.siteid);
        sitename = (TextView) findViewById(R.id.sitename);
        address = (TextView) findViewById(R.id.address);
        circle = (TextView) findViewById(R.id.circle);
        cluster = (TextView) findViewById(R.id.cluster);
        dg = (TextView) findViewById(R.id.dg);
        power = (TextView) findViewById(R.id.power);
        solar = (TextView) findViewById(R.id.solar);
        sysgen = (TextView) findViewById(R.id.sysgen);
        header = (TextView) findViewById(R.id.header);
        technicianlayout = (LinearLayout) findViewById(R.id.technicianlayout);
        pmticketheader = (LinearLayout) findViewById(R.id.pmticketheader);
        appbar = (RelativeLayout) findViewById(R.id.appbar);

        input_id = (TextView) findViewById(R.id.input_id);
        input_name = (TextView) findViewById(R.id.input_name);
        input_number = (TextView) findViewById(R.id.input_number);
        input_count = (TextView) findViewById(R.id.input_count);

        if (comShare.getdesignation().equals("Technician")) {
            technicianlayout.setVisibility(View.GONE);
            appbar.setBackgroundColor(getResources().getColor(R.color.white));
            header.setTextColor(getResources().getColor(R.color.activecolor));
            backImage.setImageResource(R.mipmap.arrowblue);
        } else {
            technicianid = Integer.parseInt(getIntent().getStringExtra("tech_id"));
            techname = getIntent().getStringExtra("tech_name");
            mobile = getIntent().getStringExtra("tech_mobile");
            count = Integer.parseInt(getIntent().getStringExtra("tech_ticketcount"));
            appbar.setBackgroundColor(getResources().getColor(R.color.black));
            header.setTextColor(getResources().getColor(R.color.white));
            backImage.setImageResource(R.mipmap.whitearrow);
            technicianlayout.setVisibility(View.VISIBLE);
            input_id.setText("#" + technicianid);
            input_name.setText(techname);
            input_number.setText(mobile);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        siterecyclerview = (RecyclerView) findViewById(R.id.siterecyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(this);
        siterecyclerview.setLayoutManager(mLayoutManager);
        siterecyclerview.setItemAnimator(new DefaultItemAnimator());
        siterecyclerview.setLayoutManager(new LinearLayoutManager(this));
        siterecyclerview.setHasFixedSize(true);
        siterecyclerview.setNestedScrollingEnabled(false);
        todayAdapter = new TodayAdapter(this, techname, technicianid, mobile, list, comShare);
        siterecyclerview.setAdapter(todayAdapter);

        getsitedetails();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getsitedetails();
    }

    private void initializeOnClickListeners() {
        backImage.setOnClickListener(this);
    }

    private void getsitedetails() {
        if (com.isConnected()) {
            SiteDetails res = new SiteDetails();
            res.setSiteId(site_id);

            com.showProgressDialogue();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<SiteDetails> call = apiService.getsitedetals(comShare.gettoken(), res);
            call.enqueue(new Callback<SiteDetails>() {
                @Override
                public void onResponse(Call<SiteDetails> call, Response<SiteDetails> response) {
                    com.dismissProgressDialogue();
                    if (response.code() == 200) {
                        parsesitedetailsResponse(response.body());
                    } else {
                        com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                    }
                }

                @Override
                public void onFailure(Call<SiteDetails> call, Throwable t) {
                    com.dismissProgressDialogue();

                    com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                }
            });
        } else {
            com.showinternetdialog();
        }
    }

    private void parsesitedetailsResponse(SiteDetails body) {

        if (body.getSitesAssetdata().size() != 0) {
            recyclerView.setVisibility(View.VISIBLE);
            assetsListAdapter = new AssetsListAdapter(body.getSitesAssetdata(), body.getSitesCirculedata().getClusterName(), comShare, SitesDetailsActivity.this);
            recyclerView.setAdapter(assetsListAdapter);

        } else {
            recyclerView.setVisibility(View.GONE);
        }

        try {
            siteid.setText("#" + body.getSiteId());
            sitename.setText("#" + body.getSiteUniqueId() + ", " + body.getSiteName());
            address.setText(body.getAddress());
            circle.setText(body.getSitesCirculedata().getCircleName());
            cluster.setText(body.getSitesCirculedata().getClusterName() + " ");
            sitetickets();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    private void sitetickets() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        TicketsPojoResponse response = new TicketsPojoResponse();

        if (comShare.getdesignation().equals("Technician")) {
            response.setUserId(Integer.parseInt(comShare.getuserid()));
        } else {
            int tecid = Integer.parseInt(comShare.gettechnicianid());
            response.setUserId(tecid);
        }

        response.setSiteId(site_id);

        barberOrdersResponseCall = apiService.getsitepmticketlist(comShare.gettoken(), response);

        barberOrdersResponseCall.enqueue(new Callback<TicketsPojoResponse>() {
            @Override
            public void onResponse(Call<TicketsPojoResponse> call, Response<TicketsPojoResponse> response) {

                int statuscode = response.code();
                if (statuscode == 200) {
                    siterecyclerview.setVisibility(View.VISIBLE);

                    pmticketheader.setVisibility(View.VISIBLE);
                    parseBarberOrders(response.body());
                } else {
                    try {
                        siterecyclerview.setVisibility(View.GONE);
                        pmticketheader.setVisibility(View.GONE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<TicketsPojoResponse> call, Throwable t) {

            }
        });


    }

    private void parseBarberOrders(TicketsPojoResponse barberOrdersResponse) {

        if (barberOrdersResponse.getPmticketData().size() != 0) {
            pmticketheader.setVisibility(View.VISIBLE);

            siterecyclerview.setVisibility(View.VISIBLE);
            List<PMTicketData> list1 = barberOrdersResponse.getPmticketData();
            sysgen.setText("(" + barberOrdersResponse.getPmticketData().size() + ")");
            input_count.setText(""+barberOrdersResponse.getPmticketData().size());

            list.clear();
            list.addAll(list1);
            todayAdapter.notifyDataSetChanged();


        } else {
            pmticketheader.setVisibility(View.GONE);

            siterecyclerview.setVisibility(View.GONE);

        }


    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                finish();
                break;
        }
    }


}
