package com.ctm.technician;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import com.ctm.technician.adapter.TodayAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Tickets.PMTicketData;
import com.ctm.technician.models.Tickets.TicketsPojoResponse;
import org.joda.time.DateTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import noman.weekcalendar.WeekCalendar;
import noman.weekcalendar.listener.OnDateClickListener;
import noman.weekcalendar.listener.OnWeekChangeListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHistory extends AppCompatActivity implements View.OnClickListener {
    private WeekCalendar weekCalendar;
    private TextView selecteddate, selectmonth;
    ImageView backImage;
    RelativeLayout layout2, previous, next;
    private RecyclerView recyclerView, siterecyclerview;
    RecyclerView.LayoutManager mLayoutManager;
    private TodayAdapter todayAdapter;
    private List<PMTicketData> list = new ArrayList<>();
    Call<TicketsPojoResponse> barberOrdersResponseCall;
    private CommonFunctions com;
    ScrollView scrolllayout;
    private CommonSharePrefrences comShare;
    String fromdate, todate;
    EditText searchautocomplete;
    Spinner sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initializeOnClickListeners();
    }

    private void init() {
        selecteddate = (TextView) findViewById(R.id.textselectedate);
        layout2 = (RelativeLayout) findViewById(R.id.layout2);
        searchautocomplete = (EditText) findViewById(R.id.frag_home_search_autocomplete);
        sort = (Spinner) findViewById(R.id.sort_button);

        previous = (RelativeLayout) findViewById(R.id.previous);
        next = (RelativeLayout) findViewById(R.id.nextbtn);
        scrolllayout = (ScrollView) findViewById(R.id.scrolllayout);
        selectmonth = (TextView) findViewById(R.id.selectmonth);
        backImage = (ImageView) findViewById(R.id.back_image);
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
        List<String> list = new ArrayList<String>();

        list.add("Sort by");
        list.add("Site1");
        list.add("Site2");
        list.add("Site3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(dataAdapter);
        weekCalendar = (WeekCalendar) findViewById(R.id.weekCalendar);

        weekCalendar.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(DateTime dateTime) {

            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekCalendar.swipeBack();

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weekCalendar.swipeForward();

            }
        });

        weekCalendar.setOnWeekChangeListener(new OnWeekChangeListener() {
            @Override
            public void onWeekChange(DateTime firstDayOfTheWeek, DateTime lastDayOfTheWeek, boolean forward) {

                SimpleDateFormat outputFormatyear = new SimpleDateFormat("yyyy", Locale.US);
                SimpleDateFormat outputFormatmonth = new SimpleDateFormat("MM", Locale.US);
                SimpleDateFormat outputFormatdate = new SimpleDateFormat("dd", Locale.US);
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);

                String inputText = String.valueOf(firstDayOfTheWeek);
                Date date = null;
                try {
                    date = inputFormat.parse(inputText);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int y1 = Integer.parseInt(outputFormatyear.format(date));
                int m1 = Integer.parseInt(outputFormatmonth.format(date));
                int d1 = Integer.parseInt(outputFormatdate.format(date));


                String inputText1 = String.valueOf(lastDayOfTheWeek);
                Date date1 = null;
                try {
                    date1 = inputFormat.parse(inputText1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int y7 = Integer.parseInt(outputFormatyear.format(date1));
                int m7 = Integer.parseInt(outputFormatmonth.format(date1));
                int d7 = Integer.parseInt(outputFormatdate.format(date1));

                String day1, day2;
                if (d1 < 10) {
                    day1 = "0" + d1;
                } else {
                    day1 = "" + d1;

                }
                if (d7 < 10) {
                    day2 = "0" + d7;
                } else {
                    day2 = "" + d7;

                }
                String month1, month2;
                if (m1 < 10) {
                    month1 = "0" + m1;
                } else {
                    month1 = "" + m1;

                }
                if (m7 < 10) {
                    month2 = "0" + m7;
                } else {
                    month2 = "" + m7;
                }

                selecteddate.setText("From :" + " " + day1 + "-" + month1 + "-" + y1 + "  To :" + " " + day2 + "-" + month2 + "-" + y7);

                Calendar ca1 = Calendar.getInstance();

                ca1.set(y1, m1, d1);

                int wk = ca1.get(Calendar.WEEK_OF_MONTH);

                Log.e("weeknumber", "" + wk);
                selecteddate.setText("Week " + wk);

                fromdate = y1 + "-" + month1 + "-" + day1;
                todate = y7 + "-" + month2 + "-" + day2;


                sitetickets();

                selectmonth.setText(theMonth(m1 - 1));


            }
        });


        Calendar c1 = Calendar.getInstance();

        //first day of week
        c1.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);

        int year1 = c1.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH) + 1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);


        //last day of week
        //c1.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        c1.set(Calendar.DAY_OF_WEEK,7);

        // c1.add(Calendar.DATE, 7);

        int year7 = c1.get(Calendar.YEAR);
        int month7 = c1.get(Calendar.MONTH) + 1;
        int day7 = c1.get(Calendar.DAY_OF_MONTH);


        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);

        Calendar calender = Calendar.getInstance();
        calender.set(year, month, day);

        Log.e("currentday", "" + year + "---" + month + "---" + day);
        selectmonth.setText(theMonth(month - 1));

        String d1, d2;
        if (day1 < 10) {
            d1 = "0" + day1;
        } else {
            d1 = "" + day1;

        }
        if (day7 < 10) {
            d2 = "0" + day7;
        } else {
            d2 = "" + day7;

        }
        String m1, m2;
        if (month1 < 10) {
            m1 = "0" + month1;
        } else {
            m1 = "" + month1;

        }
        if (month7 < 10) {
            m2 = "0" + month7;
        } else {
            m2 = "" + month7;

        }
        selecteddate.setText("From :" + " " + d1 + "-" + m1 + "-" + year1 + "  To :" + " " + d2 + "-" + m2 + "-" + year7);


        Calendar ca1 = Calendar.getInstance();

        ca1.set(year1, month1, day1);

        int wk = ca1.get(Calendar.WEEK_OF_MONTH);

        Log.e("weeknumber", "" + wk);

        selecteddate.setText("Week " + wk);

        fromdate = year1 + "-" + m1 + "-" + d1;
        todate = year7 + "-" + m2 + "-" + d2;
        sitetickets();
    }

    public static String theMonth(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        return monthNames[month];
    }


    private void initializeOnClickListeners() {
        backImage.setOnClickListener(ActivityHistory.this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void sitetickets() {

        if (Networking.isNetworkAvailable(this)) {
            com.showProgressDialogue();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            TicketsPojoResponse response = new TicketsPojoResponse();

            response.setUserId(Integer.parseInt(comShare.getuserid()));
            response.setFromDate(fromdate);
            response.setToDate(todate);

            Log.e("fromdate",fromdate);
            Log.e("todate",todate);

            barberOrdersResponseCall = apiService.getClosePMTicketListDateWise(comShare.gettoken(), response);


            barberOrdersResponseCall.enqueue(new Callback<TicketsPojoResponse>() {
                @Override
                public void onResponse(Call<TicketsPojoResponse> call, Response<TicketsPojoResponse> response) {
                    com.dismissProgressDialogue();

                    int statuscode = response.code();
                    if (statuscode == 200) {
                        siterecyclerview.setVisibility(View.VISIBLE);
                        scrolllayout.setVisibility(View.VISIBLE);
                        layout2.setVisibility(View.GONE);

                        parseBarberOrders(response.body());
                    } else {
                        try {

                            siterecyclerview.setVisibility(View.GONE);
                            layout2.setVisibility(View.VISIBLE);
                            scrolllayout.setVisibility(View.GONE);


                        } catch (Exception e) {
                            com.dismissProgressDialogue();

                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TicketsPojoResponse> call, Throwable t) {
                    com.dismissProgressDialogue();

                }
            });
        } else {
            com.showinternetdialog();
        }


    }

    private void parseBarberOrders(TicketsPojoResponse barberOrdersResponse) {
        if (barberOrdersResponse.getStatusCode() == 200) {
            if (barberOrdersResponse.getPmticketData().size() != 0) {
                siterecyclerview.setVisibility(View.VISIBLE);
                List<PMTicketData> list1 = barberOrdersResponse.getPmticketData();
                list.clear();
                list.addAll(list1);
                layout2.setVisibility(View.GONE);
                scrolllayout.setVisibility(View.VISIBLE);

                getsearchedsitesList();


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

    private void getsearchedsitesList() {

        todayAdapter = new TodayAdapter(this, "", 0, "", list, comShare);
        siterecyclerview.setAdapter(todayAdapter);


        searchautocomplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                todayAdapter.filter(cs.toString());
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
