package com.ctm.technician;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ctm.technician.SupervisorActivity.DashboardActivity;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;

public class SuccessActivity extends Activity implements OnClickListener {

    ImageView backimage;
    private CommonFunctions com;
    Button btnSubmit;
    int ticketsubmitid;
    TextView input_id, successmsg;
    private CommonSharePrefrences comShare;
    String submitmessage;
    ImageView img;
    LinearLayout approvestatusmsg, linearbtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_confirm);
        backimage = (ImageView) findViewById(R.id.back_image);
        btnSubmit = (Button) findViewById(R.id.btnsubmit);
        input_id = (TextView) findViewById(R.id.input_id);
        successmsg = (TextView) findViewById(R.id.successmsg);
        img = (ImageView) findViewById(R.id.img);
        approvestatusmsg = (LinearLayout) findViewById(R.id.approvestatusmsg);
        linearbtn = (LinearLayout) findViewById(R.id.linearbtn);
        com = new CommonFunctions(this);
        ticketsubmitid = Integer.parseInt(getIntent().getStringExtra("submit_id"));
        submitmessage = getIntent().getStringExtra("submit_message");
        comShare = CommonSharePrefrences.getInstance(this);
        if (comShare.getdesignation().equals("Technician")) {

            approvestatusmsg.setVisibility(View.VISIBLE);
            linearbtn.setBackground(getResources().getDrawable(R.drawable.btngradiantheader));
            btnSubmit.setBackground(getResources().getDrawable(R.drawable.btngradiantheader));

        } else {
            approvestatusmsg.setVisibility(View.GONE);
            linearbtn.setBackground(getResources().getDrawable(R.drawable.btngradiantheadersup));
            btnSubmit.setBackground(getResources().getDrawable(R.drawable.btngradiantheadersup));


        }
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
                if (comShare.getdesignation().equals("Technician")) {
                    Intent i = new Intent(SuccessActivity.this, HomeActivity.class);
                    startActivity((i));
                    finishAffinity();
                } else if (comShare.getdesignation().equals("Supervisor")) {
                    Intent i = new Intent(SuccessActivity.this, DashboardActivity.class);
                    startActivity((i));
                    finishAffinity();
                }
            }
        });
        Log.e("ticketid", "" + ticketsubmitid);
        input_id.setText(" # " + ticketsubmitid);
        successmsg.setText(submitmessage);
    }

    @Override
    public void onClick(View view) {
        com.HidingSoftKeyBoard(view);
        switch (view.getId()) {
            case R.id.btnsubmit:
                if (comShare.getdesignation().equals("Technician")) {

                    Intent i = new Intent(SuccessActivity.this, HomeActivity.class);
                    startActivity((i));
                    finishAffinity();

                } else if (comShare.getdesignation().equals("Supervisor")) {
                    Intent i = new Intent(SuccessActivity.this, DashboardActivity.class);
                    startActivity((i));
                    finishAffinity();


                }
                break;


        }
    }


    @Override
    public void onBackPressed() {
        if (comShare.getdesignation().equals("Technician")) {
            Intent i = new Intent(SuccessActivity.this, HomeActivity.class);
            startActivity((i));
            finishAffinity();

        } else if (comShare.getdesignation().equals("Supervisor")) {
            Intent i = new Intent(SuccessActivity.this, DashboardActivity.class);
            startActivity((i));
            finishAffinity();


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}