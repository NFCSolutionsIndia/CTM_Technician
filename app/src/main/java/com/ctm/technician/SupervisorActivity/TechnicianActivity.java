package com.ctm.technician.SupervisorActivity;

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

import com.ctm.technician.BaseActivity;
import com.ctm.technician.R;
import com.ctm.technician.Supervisoemodels.Tecnicianmodel.TechdataPojoResponse;
import com.ctm.technician.Supervisoemodels.Tecnicianmodel.Technicianlistmodel;
import com.ctm.technician.Supervisoemodels.Tickets.SupTicketsPojoResponse;
import com.ctm.technician.Supervisoradapter.TechniciallistAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechnicianActivity extends BaseActivity implements View.OnClickListener {
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    TextView totalsites;
    private RecyclerView recyclerView;
    private TechniciallistAdapter mysitesListAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    EditText searchautocomplete;
    Spinner sort;
    private List<Technicianlistmodel> list = new ArrayList<>();
    RelativeLayout layout2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_technician);
        initializeViews();
        initializeOnClickListeners();

    }


    private void initializeViews() {
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);

        backImage = (ImageView) findViewById(R.id.back_image);
        totalsites = (TextView) findViewById(R.id.totalsites);

        searchautocomplete = (EditText) findViewById(R.id.frag_home_search_autocomplete);
        sort = (Spinner) findViewById(R.id.sort_button);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layout2 = (RelativeLayout) findViewById(R.id.layout2);

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

        gettechniciansResponses();


    }

    private void initializeOnClickListeners() {
        backImage.setOnClickListener(this);
    }

    private void gettechniciansResponses() {
        if (Networking.isNetworkAvailable(this)) {

            com.showProgressDialogue();


            SupTicketsPojoResponse response = new SupTicketsPojoResponse();

            response.setUserId(Integer.parseInt(comShare.getuserid()));


            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);


            Call<TechdataPojoResponse> getMyServicesResponseCall = apiService.gettechnicianlist(comShare.gettoken(), response);
            getMyServicesResponseCall.enqueue(new Callback<TechdataPojoResponse>() {
                @Override
                public void onResponse(Call<TechdataPojoResponse> call, Response<TechdataPojoResponse> response) {
                    TechdataPojoResponse sitesResponse = response.body();
                    int statusCode = response.code();
                    com.dismissProgressDialogue();

                    if (statusCode == 200) {
                        parseMyStaffList(sitesResponse);

                    } else {
                        com.showAlertDialogOK(getResources().getString(R.string.something));
                    }
                }

                @Override
                public void onFailure(Call<TechdataPojoResponse> call, Throwable t) {
                    com.dismissProgressDialogue();
                    com.showAlertDialogOK(getResources().getString(R.string.strErrorMessage));
                }
            });
        } else {

            com.showinternetdialog();

        }
    }

    private void parseMyStaffList(TechdataPojoResponse res) {
        if (res.getApiStatus().getStatusCode() == 200) {
            if (res.getTechnicianListModels().size() != 0) {


                totalsites.setText(res.getTechnicianListModels().size() + " " + "Technician(s) Found");

                List<Technicianlistmodel> list1 = res.getTechnicianListModels();
                list.clear();
                list.addAll(list1);
                layout2.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);

                getsearchedsitesList();


            } else {
                totalsites.setText("0" + "" + "Technician(s) Found");
                showDialog(res.getApiStatus().getStatusMessage());
                layout2.setVisibility(View.VISIBLE);

                recyclerView.setVisibility(View.GONE);
            }
        } else {

            showDialog(res.getApiStatus().getStatusMessage());

        }
    }

    private void getsearchedsitesList() {

        mysitesListAdapter = new TechniciallistAdapter(list, comShare, TechnicianActivity.this);
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
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
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
