package com.ctm.technician.Supervisorfragments;

import android.content.Context;
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
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.AddCount;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupervisoropenTickets extends Fragment {
    private TextView nodata, totalsites;
    private RecyclerView recyclerView;
    View view;
    private RecenttiketAdapter todayAdapter;
    Call<SupTicketsPojoResponse> barberOrdersResponseCall;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private List<SupPMTechnicianData> list = new ArrayList<>();
    private List<SupPMTechnicianData> list1 = new ArrayList<>();

    RelativeLayout layout2;
    EditText searchautocomplete;
    Spinner sort;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean mViewCreated = false;
    private AddCount mCallback;

    public SupervisoropenTickets() {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);
        initializeViews(view);
        mViewCreated = true;

        return view;
    }

    private void initializeViews(View view) {

        nodata = (TextView) view.findViewById(R.id.no_data_text);
        layout2 = (RelativeLayout) view.findViewById(R.id.layout2);
        totalsites = (TextView) view.findViewById(R.id.totalsites);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);

        searchautocomplete = (EditText) view.findViewById(R.id.frag_home_search_autocomplete);
        sort = (Spinner) view.findViewById(R.id.sort_button);
        totalsites.setText("Open");

        com = new CommonFunctions(getActivity());
        comShare = CommonSharePrefrences.getInstance(getActivity());
        com.controllist.clear();
        list.clear();
        recyclerView = (RecyclerView) view.findViewById(R.id.home_recyler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
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
        List<String> list = new ArrayList<String>();

        list.add("Sort by");
        list.add("Site1");
        list.add("Site2");
        list.add("Site3");


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

                                                 Opentickets();



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

                        Opentickets();

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
        if (isVisibleToUser && mViewCreated) {
            mSwipeRefreshLayout.setRefreshing(true);
            mSwipeRefreshLayout.setVisibility(View.VISIBLE);


                Opentickets();

        }
    }

    private void Opentickets() {
        if (com.isConnected()) {

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

            SupTicketsPojoResponse response = new SupTicketsPojoResponse();

            response.setUserId(Integer.parseInt(comShare.getuserid()));
            response.setType(2);

            barberOrdersResponseCall = apiService.getsuppmticketdata(comShare.gettoken(), response);


            barberOrdersResponseCall.enqueue(new Callback<SupTicketsPojoResponse>() {
                @Override
                public void onResponse(Call<SupTicketsPojoResponse> call, Response<SupTicketsPojoResponse> response) {
                    mSwipeRefreshLayout.setRefreshing(false);
                    int statuscode = response.code();
                    if (statuscode == 200) {
                        parseopenOrders(response.body());

                    } else {
                        nodata.setText("No data Found");
                        mSwipeRefreshLayout.setVisibility(View.GONE);
                        layout2.setVisibility(View.VISIBLE);
                        mSwipeRefreshLayout.setRefreshing(false);

                    }
                }

                @Override
                public void onFailure(Call<SupTicketsPojoResponse> call, Throwable t) {
                    mSwipeRefreshLayout.setRefreshing(false);

                }
            });
        } else {
            mSwipeRefreshLayout.setRefreshing(false);

            com.showinternetdialog();
        }


    }

    private void parseopenOrders(SupTicketsPojoResponse barberOrdersResponse) {

        if (barberOrdersResponse.getStatusCode() == 200) {
            com.supticketdata.clear();
            list.clear();
            if (barberOrdersResponse.getPMTechnicianData().size() != 0) {
                mCallback.addCount(String.valueOf(barberOrdersResponse.getCount()), String.valueOf(barberOrdersResponse.getOpenCount()), String.valueOf(barberOrdersResponse.getClosedCount()), String.valueOf(barberOrdersResponse.getOverdueCount()), String.valueOf(barberOrdersResponse.getPendingCount()));


                for (int i = 0; i < barberOrdersResponse.getPMTechnicianData().size(); i++) {
                    if (barberOrdersResponse.getPMTechnicianData().get(i).getPMTicketData().getPmTicketStatusName().equalsIgnoreCase("Open") || barberOrdersResponse.getPMTechnicianData().get(i).getPMTicketData().getPmTicketStatusName().equalsIgnoreCase("Reassign")) {
                        list.add(barberOrdersResponse.getPMTechnicianData().get(i));
                    }


                }
                com.supticketdata.addAll(barberOrdersResponse.getPMTechnicianData());

                List<SupPMTechnicianData> list1 = barberOrdersResponse.getPMTechnicianData();
                list1.clear();
                list1.addAll(list);
                getsearchedsitesList();
                layout2.setVisibility(View.GONE);
                mSwipeRefreshLayout.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);


                if (list.size() == 0) {
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


        } else {
            nodata.setText("No data Found");
            mSwipeRefreshLayout.setVisibility(View.GONE);
            layout2.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    private void parseBarberOrders() {
        list.clear();
        mSwipeRefreshLayout.setRefreshing(false);

        if (com.supticketdata.size() != 0) {


            for (int i = 0; i < com.supticketdata.size(); i++) {
                if (com.supticketdata.get(i).getPMTicketData().getPmTicketStatusName().equalsIgnoreCase("Open") || com.supticketdata.get(i).getPMTicketData().getPmTicketStatusName().equalsIgnoreCase("Reassign")) {
                    list.add(com.supticketdata.get(i));
                }


            }

            list1.clear();
            list1.addAll(list);
            getsearchedsitesList();
            layout2.setVisibility(View.GONE);


            if (list.size() == 0) {
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

    @Override
    public void onStop() {
        super.onStop();
        searchautocomplete.setText("");

        mViewCreated = false;


    }
}
