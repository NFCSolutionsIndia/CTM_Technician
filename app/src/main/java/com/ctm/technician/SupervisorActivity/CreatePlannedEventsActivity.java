package com.ctm.technician.SupervisorActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ctm.technician.BaseActivity;
import com.ctm.technician.PlannedeventsuccessActivity;
import com.ctm.technician.R;
import com.ctm.technician.Supervisoemodels.Plannedevents.Eventsitemodel;
import com.ctm.technician.Supervisoemodels.Plannedevents.Prioritymodel;
import com.ctm.technician.Supervisoemodels.Plannedevents.Submitpemodel;
import com.ctm.technician.Supervisoemodels.Tickets.SupTicketsPojoResponse;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Checklist.UpdatePmticket;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePlannedEventsActivity extends BaseActivity implements View.OnClickListener {
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage, img;
    TextView selectdate, technicialname;
    String datestring = "";
    Spinner sitename, priority;
    Button btnsubmit;
    RelativeLayout dateralative;
    ArrayList<String> sitelist;
    ArrayList<String> prioritylist;
    int selectedItemid = 0, priorityid = 0, selectedtechid = 0;
    EditText titletext, input_aboutus;
    private static int SPLASH_TIME_OUT = 2000;
    String techname, selectedsitename;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createplanevent);
        initializeViews();
        initializeOnClickListeners();
    }

    private void initializeViews() {
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);

        backImage = (ImageView) findViewById(R.id.back_image);
        img = (ImageView) findViewById(R.id.img);
        selectdate = (TextView) findViewById(R.id.selectdate);
        technicialname = (TextView) findViewById(R.id.techname);
        titletext = (EditText) findViewById(R.id.titletext);
        input_aboutus = (EditText) findViewById(R.id.input_aboutus);
        sitename = (Spinner) findViewById(R.id.site_name);
        priority = (Spinner) findViewById(R.id.priority);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        dateralative = (RelativeLayout) findViewById(R.id.dateralative);
        sitelist = new ArrayList<String>();
        prioritylist = new ArrayList<String>();

        dateralative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();

            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Createplannedevent();
            }
        });
        getticketpriority();
    }

    public void selectDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        final NumberFormat formatter = new DecimalFormat("00");


        DatePickerDialog dpd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                monthOfYear++;
                int day = dayOfMonth;
                int month = monthOfYear;
                String date_2 = year + "-" + month + "-" + day;
                selectdate.setText(localDate(date_2));
                datestring = year + "-" + month + "-" + day;
            }
        }, year, month, day);

        dpd.getDatePicker().setMinDate(c.getTimeInMillis());

        dpd.show();
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

            Call<Eventsitemodel> getMyServicesResponseCall = apiService.getTechniciansiteslist(comShare.gettoken(), response);
            getMyServicesResponseCall.enqueue(new Callback<Eventsitemodel>() {
                @Override
                public void onResponse(Call<Eventsitemodel> call, Response<Eventsitemodel> response) {
                    Eventsitemodel sitesResponse = response.body();
                    int statusCode = response.code();
                    com.dismissProgressDialogue();

                    if (statusCode == 200) {
                        parseMysitesList(sitesResponse);

                    } else {

                        com.showAlertDialogOK(getResources().getString(R.string.something));
                    }
                }

                @Override
                public void onFailure(Call<Eventsitemodel> call, Throwable t) {
                    com.dismissProgressDialogue();
                    com.showAlertDialogOK(getResources().getString(R.string.strErrorMessage));
                }
            });
        } else {

            com.showinternetdialog();

        }
    }

    private void parseMysitesList(Eventsitemodel res) {
        if (res.getTechnicianListModels().size() != 0) {

            com.TechniciansitesList.clear();
            sitelist.clear();
            com.TechniciansitesList.addAll(res.getTechnicianListModels());

            sitelist = new ArrayList<String>();
            sitelist.clear();
            sitelist.add("Select Site Name");

            for (int i = 0; i < com.TechniciansitesList.size(); i++) {

                sitelist.add(com.TechniciansitesList.get(i).getSiteName());

            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sitelist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sitename.setAdapter(dataAdapter);


            sitename.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (sitename.getSelectedItem() == "Select Site Name") {
                        selectedItemid = 0;
                        return;
                    } else {
                        selectedItemid = com.TechniciansitesList.get(position - 1).getSiteId();
                        selectedtechid = com.TechniciansitesList.get(position - 1).getTechnicianId();
                        techname = com.TechniciansitesList.get(position - 1).getTechnicianName();
                        selectedsitename = com.TechniciansitesList.get(position - 1).getSiteName();
                        technicialname.setText(techname);

                        Log.e("siteid", "" + selectedItemid);
                        Log.e("techname", "" + techname);

                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


        } else {
            showDialog(res.getStatusMessage());

        }

    }


    private void getticketpriority() {
        if (Networking.isNetworkAvailable(this)) {

            com.showProgressDialogue();

            SupTicketsPojoResponse response = new SupTicketsPojoResponse();

            response.setUserId(Integer.parseInt(comShare.getuserid()));

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            Call<Prioritymodel> getMyServicesResponseCall = apiService.getpriority(comShare.gettoken(), response);
            getMyServicesResponseCall.enqueue(new Callback<Prioritymodel>() {
                @Override
                public void onResponse(Call<Prioritymodel> call, Response<Prioritymodel> response) {
                    Prioritymodel priorityResponse = response.body();
                    int statusCode = response.code();
                    com.dismissProgressDialogue();

                    if (statusCode == 200) {
                        parsepriorityList(priorityResponse);

                    } else {
                        com.showAlertDialogOK(getResources().getString(R.string.something));
                    }
                }

                @Override
                public void onFailure(Call<Prioritymodel> call, Throwable t) {
                    com.dismissProgressDialogue();
                    com.showAlertDialogOK(getResources().getString(R.string.strErrorMessage));
                }
            });
        } else {

            com.showinternetdialog();

        }
    }

    private void parsepriorityList(Prioritymodel res) {
        if (res.getTicketPriorityModel().size() != 0) {
            com.PriorityList.clear();
            prioritylist.clear();

            com.PriorityList.addAll(res.getTicketPriorityModel());

            prioritylist = new ArrayList<String>();
            prioritylist.clear();
            prioritylist.add("Choose Priority");

            for (int i = 0; i < com.PriorityList.size(); i++) {

                prioritylist.add(com.PriorityList.get(i).getPriority());

            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prioritylist);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            priority.setAdapter(dataAdapter);

            priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (priority.getSelectedItem() == "Choose Priority") {
                        priorityid = 0;
                        return;
                    } else {
                        priorityid = com.PriorityList.get(position - 1).getPriorityId();
                    }
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            gettechniciansResponses();

        } else {
            showDialog(res.getStatusMessage());

        }
    }


    private void Createplannedevent() {
        if (priorityid == 0) {
            showDialog("Choose Priority");
            return;
        }

        if (datestring.equals("")) {
            showDialog("Select Due Date");
            return;
        }
        if (selectedItemid == 0) {
            showDialog("Select Site Name");
            return;
        }

        if (titletext.getText().toString().equals("")) {
            showDialog("Heading can not be empty");
            return;
        }
        if (input_aboutus.getText().toString().equals("")) {
            showDialog("Description can not be empty");
            return;
        }
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                com.dismissProgressDialogue();
                Intent i = new Intent(CreatePlannedEventsActivity.this,PlannedeventsuccessActivity.class);
                startActivity((i));
                finish();

            }
        }, SPLASH_TIME_OUT);
*/
        Submitplannedevent();
    }


    private void Submitplannedevent() {

        if (Networking.isNetworkAvailable(this)) {
            com.showProgressDialogue();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());
            Submitpemodel response = new Submitpemodel();


            response.setSiteId(selectedItemid);
            response.setTechnicianName(techname);
            response.setAssignedTo(selectedtechid);
            response.setTicketDescription(input_aboutus.getText().toString());
            response.setTicketHeading(titletext.getText().toString());
            response.setRecCreateDate(formattedDate);
            response.setDateAsPerPMPlan(datestring);
            response.setSiteName(selectedsitename);
            response.setPriorityId(priorityid);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdatePmticket> getMyServicesResponseCall = apiService.Submitplannedevent(comShare.gettoken(), response);
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

            Intent i = new Intent(this, PlannedeventsuccessActivity.class);
            i.putExtra("submit_id", "" + barberOrdersResponse.getTicketId());
            startActivity(i);
            finish();

            /*Intent i = new Intent(CreatePlannedEventsActivity.this, PlannedeventsuccessActivity.class);
            startActivity((i));
            finish();
*/
        } else {
            Toast.makeText(this, barberOrdersResponse.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public String localDate(String date) {
        try {
            Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return new SimpleDateFormat("dd-MM-yyyy").format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
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
