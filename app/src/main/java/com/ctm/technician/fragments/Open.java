package com.ctm.technician.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ctm.technician.R;
import com.ctm.technician.adapter.OpenAdapter;
import com.ctm.technician.adapter.TodayAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.models.Tickets.PMTicketData;
import com.ctm.technician.models.Tickets.TicketsPojoResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Open extends Fragment {
    private TextView nodata, totalsites;
    private RecyclerView recyclerView;
    View view;
    private OpenAdapter todayAdapter;
    Call<TicketsPojoResponse> barberOrdersResponseCall;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ArrayList<PMTicketData> list3 = new ArrayList<>();
    private ArrayList<PMTicketData> list = new ArrayList<>();
    RelativeLayout layout2;
    EditText searchautocomplete;
    Spinner sort;
    LinearLayout appbar;
    SwipeRefreshLayout mSwipeRefreshLayout;

    public Open() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {

        nodata = (TextView) view.findViewById(R.id.no_data_text);
        totalsites = (TextView) view.findViewById(R.id.totalsites);

        layout2 = (RelativeLayout) view.findViewById(R.id.layout2);
        searchautocomplete = (EditText) view.findViewById(R.id.frag_home_search_autocomplete);
        sort = (Spinner) view.findViewById(R.id.sort_button);
        appbar = (LinearLayout) view.findViewById(R.id.appbar);
        appbar.setVisibility(View.GONE);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        //mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        com = new CommonFunctions(getActivity());
        comShare = CommonSharePrefrences.getInstance(getActivity());
        com.controllist.clear();
        list3.clear();
        totalsites.setText("Open");
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recyler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy < 0) {

                    todayAdapter.notifyDataSetChanged();

                } else {

                    todayAdapter.notifyDataSetChanged();

                }
            }
        });
        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         if (com.isConnected()) {
                                             mSwipeRefreshLayout.setRefreshing(true);
                                             mSwipeRefreshLayout.setVisibility(View.VISIBLE);

                                             parseBarberOrders();
                                         } else {
                                             mSwipeRefreshLayout.setRefreshing(false);
                                             com.showinternetdialog();

                                         }
                                     }
                                 }
        );

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (com.isConnected()) {
                    mSwipeRefreshLayout.setRefreshing(true);
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);

                    parseBarberOrders();
                } else {
                    mSwipeRefreshLayout.setRefreshing(false);

                    com.showinternetdialog();

                }
            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mSwipeRefreshLayout.setRefreshing(true);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);

            parseBarberOrders();
        }
    }
    private void Opentickets() {
        if (com.isConnected()) {
            com.showProgressDialogue();
            //mSwipeRefreshLayout.setRefreshing(false);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            TicketsPojoResponse response = new TicketsPojoResponse();

            response.setUserId(Integer.parseInt(comShare.getuserid()));

            barberOrdersResponseCall = apiService.getpmticketdata(comShare.gettoken(), response);


            barberOrdersResponseCall.enqueue(new Callback<TicketsPojoResponse>() {
                @Override
                public void onResponse(Call<TicketsPojoResponse> call, Response<TicketsPojoResponse> response) {
                    com.dismissProgressDialogue();
                    int statuscode = response.code();
                    if (statuscode == 200) {
                        //parseBarberOrders(response.body());
                    } else {
                        com.dismissProgressDialogue();
                        layout2.setVisibility(View.VISIBLE);
                        nodata.setText("No data Found");

                    }
                }

                @Override
                public void onFailure(Call<TicketsPojoResponse> call, Throwable t) {

                }
            });
        } else {
            com.showinternetdialog();
        }


    }

    private void parseBarberOrders() {
        mSwipeRefreshLayout.setRefreshing(false);
        list3.clear();
        if (com.ticketdata.size() != 0) {
            for (int i = 0; i < com.ticketdata.size(); i++) {
                if (com.ticketdata.get(i).getPmTicketStatusName().equalsIgnoreCase("Open") || com.ticketdata.get(i).getPmTicketStatusName().equalsIgnoreCase("Reassign")) {
                    list3.add(com.ticketdata.get(i));
                }
            }

            list.clear();
            list.addAll(list3);
            layout2.setVisibility(View.GONE);

            getsearchedsitesList();


            if (list3.size() == 0) {
                layout2.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setVisibility(View.GONE);

                nodata.setText("No data Found");
            }

        } else {
            layout2.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setVisibility(View.GONE);

            nodata.setText("No data Found");
        }


    }

    private void getsearchedsitesList() {

        todayAdapter = new OpenAdapter(getActivity(), list, comShare);
        recyclerView.setAdapter(todayAdapter);

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
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onStop() {
        super.onStop();
        searchautocomplete.setText("");

    }
}
