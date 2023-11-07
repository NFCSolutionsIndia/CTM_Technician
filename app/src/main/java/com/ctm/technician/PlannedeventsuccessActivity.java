package com.ctm.technician;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.ctm.technician.SupervisorActivity.DashboardActivity;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;

public class PlannedeventsuccessActivity extends Activity implements OnClickListener {

    ImageView backimage;
    private CommonFunctions com;
    Button btnSubmit;
    TextView input_id;
    private CommonSharePrefrences comShare;
    int ticketsubmitid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planevent_confirm);
        backimage = (ImageView) findViewById(R.id.back_image);
        btnSubmit = (Button) findViewById(R.id.btnsubmit);
        input_id = (TextView) findViewById(R.id.input_id);
        ticketsubmitid = Integer.parseInt(getIntent().getStringExtra("submit_id"));
        input_id.setText(" # " + ticketsubmitid);
        initializeViews();
        initializeOnClickListeners();
    }

    private void initializeOnClickListeners() {
        btnSubmit.setOnClickListener(this);
    }

    private void initializeViews() {
        backimage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlannedeventsuccessActivity.this, DashboardActivity.class);
                startActivity((i));
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnsubmit:
                Intent i = new Intent(PlannedeventsuccessActivity.this, DashboardActivity.class);
                startActivity((i));
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(PlannedeventsuccessActivity.this, DashboardActivity.class);
        startActivity((i));
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}