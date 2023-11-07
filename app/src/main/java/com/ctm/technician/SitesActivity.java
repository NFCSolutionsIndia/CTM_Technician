package com.ctm.technician;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.ctm.technician.adapter.SitesListAdapter;
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

public class SitesActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    Spinner sort;
    RelativeLayout layout2;
    TextView totalsites;
    private SitesListAdapter mysitesListAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    EditText searchautocomplete;
    private List<sitesListData> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sites);
        initializeViews();
        initializeOnClickListeners();
    }


    private void initializeViews() {
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        totalsites = (TextView) findViewById(R.id.totalsites);
        layout2 = (RelativeLayout) findViewById(R.id.layout2);
        searchautocomplete = (EditText) findViewById(R.id.frag_home_search_autocomplete);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        backImage = (ImageView) findViewById(R.id.back_image);
        sort = (Spinner) findViewById(R.id.sort_button);
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

    @Override
    protected void onRestart() {
        super.onRestart();
        getMyServicesResponses();
    }

    private void getMyServicesResponses() {
        if (Networking.isNetworkAvailable(this)) {
            com.showProgressDialogue();
            Siteresponse response = new Siteresponse();
            response.setUserId(Integer.parseInt(comShare.getuserid()));
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
                List<sitesListData> list1 = res.getSitesListData();
                list.clear();
                list.addAll(list1);
                layout2.setVisibility(View.GONE);

                recyclerView.setVisibility(View.VISIBLE);

                getsearchedsitesList();
            } else {
                layout2.setVisibility(View.VISIBLE);
                totalsites.setText("Sites Not found");
                showDialog(res.getStatusMessage());

                recyclerView.setVisibility(View.GONE);
            }
        } else {

            showDialog(res.getStatusMessage());

        }
    }


    private void getsearchedsitesList() {

        mysitesListAdapter = new SitesListAdapter(list, comShare, SitesActivity.this, R.layout.sites_list);
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


    private void initializeOnClickListeners() {
        backImage.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        searchautocomplete.setText("");
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
