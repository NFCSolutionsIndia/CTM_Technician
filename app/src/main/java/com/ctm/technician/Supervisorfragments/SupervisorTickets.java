package com.ctm.technician.Supervisorfragments;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ctm.technician.R;
import com.ctm.technician.Supervisoemodels.Tickets.SupPMTechnicianData;
import com.ctm.technician.Supervisoemodels.Tickets.SupTicketsPojoResponse;
import com.ctm.technician.Supervisoradapter.RecenttiketAdapter;
import com.ctm.technician.adapter.TodayAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.AddCount;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupervisorTickets extends Fragment {
    private TextView nodata, totalsites;
    private RecyclerView recyclerView;
    View view;
    private RecenttiketAdapter todayAdapter;
    Call<SupTicketsPojoResponse> barberOrdersResponseCall;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private List<SupPMTechnicianData> list = new ArrayList<>();
    private AddCount mCallback;
    boolean _areLecturesLoaded = false;
    EditText searchautocomplete;
    Spinner sort;
    RelativeLayout layout2;
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<SupPMTechnicianData> list1;

    public SupervisorTickets() {

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

        searchautocomplete = (EditText) view.findViewById(R.id.frag_home_search_autocomplete);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        sort = (Spinner) view.findViewById(R.id.sort_button);
        totalsites.setText("All");

        com = new CommonFunctions(getActivity());
        comShare = CommonSharePrefrences.getInstance(getActivity());
        com.controllist.clear();

        recyclerView = (RecyclerView) view.findViewById(R.id.home_recyler_view);
        layout2 = (RelativeLayout) view.findViewById(R.id.layout2);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setNestedScrollingEnabled(false);

        List<String> list = new ArrayList<String>();

        list.add("Sort by");
        list.add("Site1");
        list.add("Site2");
        list.add("Site3");

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
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(dataAdapter);

        mSwipeRefreshLayout.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         if (com.isConnected()) {
                                             mSwipeRefreshLayout.setVisibility(View.VISIBLE);

                                             mSwipeRefreshLayout.setRefreshing(true);

                                                 Supervisortickets();

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
                    mSwipeRefreshLayout.setVisibility(View.VISIBLE);

                    mSwipeRefreshLayout.setRefreshing(true);

                        Supervisortickets();

                } else {
                    mSwipeRefreshLayout.setRefreshing(false);

                    com.showinternetdialog();

                }
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (AddCount) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement IFragmentToActivity");
        }
    }


    private void Supervisortickets() {
        if (com.isConnected()) {
            // com.showProgressDialogue();

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            SupTicketsPojoResponse response = new SupTicketsPojoResponse();

            response.setUserId(Integer.parseInt(comShare.getuserid()));
            response.setType(2);

            barberOrdersResponseCall = apiService.getsuppmticketdata(comShare.gettoken(), response);


            barberOrdersResponseCall.enqueue(new Callback<SupTicketsPojoResponse>() {
                @Override
                public void onResponse(Call<SupTicketsPojoResponse> call, Response<SupTicketsPojoResponse> response) {
                    //com.dismissProgressDialogue();
                    mSwipeRefreshLayout.setRefreshing(false);

                    int statuscode = response.code();
                    if (statuscode == 200) {
                        parseBarberOrders(response.body());
                     } else {
                        nodata.setText("No data Found");
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }

                @Override
                public void onFailure(Call<SupTicketsPojoResponse> call, Throwable t) {

                }
            });
        } else {
            com.showinternetdialog();
        }

    }

    private void parseBarberOrders(SupTicketsPojoResponse barberOrdersResponse) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (barberOrdersResponse.getStatusCode() == 200) {
            if (barberOrdersResponse.getPMTechnicianData().size() != 0) {
                mCallback.addCount(String.valueOf(barberOrdersResponse.getCount()), String.valueOf(barberOrdersResponse.getOpenCount()), String.valueOf(barberOrdersResponse.getClosedCount()), String.valueOf(barberOrdersResponse.getOverdueCount()), String.valueOf(barberOrdersResponse.getPendingCount()));
                com.supticketdata.clear();

                List<SupPMTechnicianData> list1 = barberOrdersResponse.getPMTechnicianData();

                com.supticketdata.addAll(barberOrdersResponse.getPMTechnicianData());

                list.clear();
                list.addAll(list1);
                layout2.setVisibility(View.GONE);
                getsearchedsitesList();
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);

            } else {
                nodata.setText("No data Found");
                mSwipeRefreshLayout.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
                mSwipeRefreshLayout.setRefreshing(false);
            }


        } else {
            nodata.setText("No data Found");
            mSwipeRefreshLayout.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }




    private void getsearchedsitesList() {

        todayAdapter = new RecenttiketAdapter(getActivity(), list);
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

}
