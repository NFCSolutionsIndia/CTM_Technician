package com.ctm.technician;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;

public class PMActivityHistory extends BaseActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    EditText searchautocomplete;
    Spinner sort;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmactivity);
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



        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


    }

    private void initializeOnClickListeners() {
        backImage.setOnClickListener(this);
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
