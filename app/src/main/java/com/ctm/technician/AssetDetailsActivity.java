package com.ctm.technician;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ctm.technician.adapter.Assetdetailslistadapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.models.Sites.sitesAssetdetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    int siteasset_id, site_id;
    TextView assetname, ageing, warantydate, frequency, assetmake, assetserialnumber;
    private RecyclerView siterecyclerview;
    RecyclerView.LayoutManager mLayoutManager;
    String assettextname;
    LinearLayout assertserial, linearwarranty, linearmake, linearaging, linearfreq;
    private Assetdetailslistadapter myassetListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asset_details);
        initializeViews();
        initializeOnClickListeners();
    }

    private void initializeViews() {
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);
        siteasset_id = Integer.parseInt(getIntent().getStringExtra("siteasset_id"));
        site_id = Integer.parseInt(getIntent().getStringExtra("site_id"));
        assettextname = getIntent().getStringExtra("asset_name");
        siterecyclerview = (RecyclerView) findViewById(R.id.siterecyclerview);
        backImage = (ImageView) findViewById(R.id.back_image);

        assetmake = (TextView) findViewById(R.id.assetmake);
        assetserialnumber = (TextView) findViewById(R.id.assetserialnumber);
        assetname = (TextView) findViewById(R.id.assetname);
        ageing = (TextView) findViewById(R.id.ageing);
        warantydate = (TextView) findViewById(R.id.warantydate);
        frequency = (TextView) findViewById(R.id.frequency);
        assertserial = (LinearLayout) findViewById(R.id.assertserial);
        linearwarranty = (LinearLayout) findViewById(R.id.linearwarranty);
        linearmake = (LinearLayout) findViewById(R.id.linearmake);
        linearaging = (LinearLayout) findViewById(R.id.linearaging);
        linearfreq = (LinearLayout) findViewById(R.id.linearfreq);

        mLayoutManager = new LinearLayoutManager(this);
        siterecyclerview.setLayoutManager(mLayoutManager);
        siterecyclerview.setItemAnimator(new DefaultItemAnimator());
        siterecyclerview.setLayoutManager(new LinearLayoutManager(this));
        siterecyclerview.setHasFixedSize(true);
        siterecyclerview.setNestedScrollingEnabled(false);

        getsiteassetdetails();
    }

    private void initializeOnClickListeners() {
        backImage.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getsiteassetdetails() {
        if (com.isConnected()) {
            sitesAssetdetails res = new sitesAssetdetails();
            res.setSiteAssetId(siteasset_id);
            com.showProgressDialogue();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<sitesAssetdetails> call = apiService.getassetdetals(comShare.gettoken(), res);
            call.enqueue(new Callback<sitesAssetdetails>() {
                @Override
                public void onResponse(Call<sitesAssetdetails> call, Response<sitesAssetdetails> response) {
                    com.dismissProgressDialogue();
                    if (response.code() == 200) {
                        parsesitedetailsResponse(response.body());
                    } else {
                        com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                    }
                }

                @Override
                public void onFailure(Call<sitesAssetdetails> call, Throwable t) {
                    com.dismissProgressDialogue();
                    com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                }
            });
        } else {
            com.showinternetdialog();
        }
    }

    private void parsesitedetailsResponse(sitesAssetdetails body) {
        try {
            assetname.setText("#" + body.getSiteAssetId() + "," + assettextname);
            try {
                if (TextUtils.isEmpty(body.getAssetSerialNumber())) {
                    assetserialnumber.setText("");
                    assertserial.setVisibility(View.GONE);
                } else {
                    assertserial.setVisibility(View.VISIBLE);
                    assetserialnumber.setText("" + body.getAssetSerialNumber());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (TextUtils.isEmpty(body.getAgeingHours())) {
                    linearaging.setVisibility(View.GONE);
                } else {
                    linearaging.setVisibility(View.VISIBLE);
                    ageing.setText(body.getAgeingHours() + "");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (TextUtils.isEmpty(body.getAssetMake())) {
                    linearmake.setVisibility(View.GONE);
                } else {
                    linearmake.setVisibility(View.VISIBLE);
                    assetmake.setText("" + body.getAssetMake());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (TextUtils.isEmpty(body.getWarrantyDate())) {
                    linearwarranty.setVisibility(View.GONE);
                } else {
                    linearwarranty.setVisibility(View.VISIBLE);


                    String currentString = body.getWarrantyDate();
                    String[] separated = currentString.split(" ");


                    warantydate.setText("" + separated[0]);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (TextUtils.isEmpty(body.getPmFrequencyHours())) {
                    linearfreq.setVisibility(View.GONE);
                } else {
                    linearfreq.setVisibility(View.VISIBLE);
                    frequency.setText("" + body.getPmFrequencyHours());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            siterecyclerview.setVisibility(View.VISIBLE);
            myassetListAdapter = new Assetdetailslistadapter(body.getAssetDetailsListData(), comShare, AssetDetailsActivity.this);
            siterecyclerview.setAdapter(myassetListAdapter);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
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
