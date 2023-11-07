package com.ctm.technician.SupervisorActivity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.ctm.technician.BaseActivity;
import com.ctm.technician.R;
import com.ctm.technician.Supervisoradapter.SupervisorsitesListAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Sites.Siteresponse;
import com.ctm.technician.models.Sites.sitesListData;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechniciandetailsActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    TextView totalsites, input_id, input_name, input_number, input_count;
    private SupervisorsitesListAdapter mysitesListAdapter;
    int technicianid, count;
    String techname, mobile;
    EditText searchautocomplete;
    Spinner sort;
    private List<sitesListData> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_techdetails);
        initializeViews();
        initializeOnClickListeners();
    }

    private void initializeViews() {
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        searchautocomplete = (EditText) findViewById(R.id.frag_home_search_autocomplete);
        sort = (Spinner) findViewById(R.id.sort_button);

        backImage = (ImageView) findViewById(R.id.back_image);

        sort = (Spinner) findViewById(R.id.sort_button);
        totalsites = (TextView) findViewById(R.id.totalsites);
        input_id = (TextView) findViewById(R.id.input_id);
        input_name = (TextView) findViewById(R.id.input_name);
        input_number = (TextView) findViewById(R.id.input_number);
        input_count = (TextView) findViewById(R.id.input_count);

        technicianid = Integer.parseInt(getIntent().getStringExtra("technician_id"));
        techname = getIntent().getStringExtra("technician_name");
        mobile = getIntent().getStringExtra("number");
        count = Integer.parseInt(getIntent().getStringExtra("tickets_count"));

        input_id.setText("# " + technicianid);
        input_name.setText(techname);
        input_number.setText(mobile);



        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        List<String> list = new ArrayList<String>();

        list.add("Sort by");
        list.add("Site1");
        list.add("Site2");
        list.add("Site3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_duration_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(dataAdapter);
        getMyServicesResponses();

    }

    private void initializeOnClickListeners() {
        backImage.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getMyServicesResponses() {
        if (Networking.isNetworkAvailable(this)) {

            com.showProgressDialogue();


            Siteresponse response = new Siteresponse();
            int tecid = Integer.parseInt(comShare.gettechnicianid());
            response.setUserId(tecid);


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<Siteresponse> getMyServicesResponseCall = apiService.getsitelist(comShare.gettoken(), response);
            getMyServicesResponseCall.enqueue(new Callback<Siteresponse>() {
                @Override
                public void onResponse(Call<Siteresponse> call, Response<Siteresponse> response) {
                    Siteresponse sitesResponse = response.body();
                    int statusCode = response.code();
                    com.dismissProgressDialogue();

                    if (statusCode == 200) {
                        parseMyStaffList(sitesResponse);

                    } else {
                        com.showAlertDialogOK(getResources().getString(R.string.something));
                    }
                }

                @Override
                public void onFailure(Call<Siteresponse> call, Throwable t) {
                    com.dismissProgressDialogue();
                    com.showAlertDialogOK(getResources().getString(R.string.strErrorMessage));
                }
            });
        } else {

            com.showinternetdialog();

        }
    }

    private void parseMyStaffList(Siteresponse res) {
        if (res.getStatusCode() == 1) {
            if (res.getSitesListData().size() != 0) {
                if (res.getCount().equals("1")) {
                    totalsites.setText(res.getCount() + " " + " Site(s) found ");
                } else {
                    totalsites.setText(res.getCount() + " " + " Sites(s) found ");
                }
                if(TextUtils.isEmpty(res.getCount())){
                    input_count.setText("0");

                }else {
                    input_count.setText("" + res.getCount());
                }


                List<sitesListData> list1 = res.getSitesListData();
                list.clear();
                list.addAll(list1);

                recyclerView.setVisibility(View.VISIBLE);

                getsearchedsitesList();



            } else {
                totalsites.setText("Sites Not found");
                showDialog(res.getStatusMessage());

                recyclerView.setVisibility(View.GONE);
            }
        } else {

            showDialog(res.getStatusMessage());

        }
    }

    private void getsearchedsitesList() {


        mysitesListAdapter = new SupervisorsitesListAdapter(list, comShare, techname, technicianid, mobile, count, TechniciandetailsActivity.this);
        recyclerView.setAdapter(mysitesListAdapter);

        searchautocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                mysitesListAdapter.filter(cs.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });

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
