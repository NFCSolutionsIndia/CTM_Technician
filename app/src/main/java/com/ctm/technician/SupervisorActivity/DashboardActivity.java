package com.ctm.technician.SupervisorActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.technician.BaseActivity;
import com.ctm.technician.MyApplication;
import com.ctm.technician.NavDrawerCallback;
import com.ctm.technician.R;
import com.ctm.technician.SplashActivity;
import com.ctm.technician.Supervisoemodels.Tickets.SupPMTechnicianData;
import com.ctm.technician.Supervisoemodels.Tickets.SupTicketsPojoResponse;
import com.ctm.technician.Supervisoradapter.RecenttiketAdapter;
import com.ctm.technician.adapter.SupNavigationDrawerAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends BaseActivity implements AdapterView.OnItemClickListener, NavDrawerCallback {
    Context context = this;
    private DrawerLayout drawer;
    private ListView drawerList;
    private static final int REQUEST_PERMISSIONS = 20;
    ImageView profile_image;
    TextView sidemenu_name, sidemenucontact;
    private ImageView menuImage, menuImage2;
    private RecyclerView recyclerView;
    LinearLayout openjobs, overduejobs, closedjobs, pendingjobs;
    TextView totaltickets, open, overdue, closed, approval, input_complaince, input_noncomplaince;
    private CommonFunctions com;
    private CommonSharePrefrences pref;
    LinearLayout signout, noncomplaince, compliance, navtop;
    RecyclerView.LayoutManager mLayoutManager;
    private RecenttiketAdapter ticketAdapter;
    RelativeLayout layout2;
    Call<SupTicketsPojoResponse> barberOrdersResponseCall;
    RelativeLayout menu;
    ImageView refresh;
    private List<SupPMTechnicianData> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initializeViews();
        initializeDrawerAdapter();
        initializeEventListeners();
        checkPermissionsMarsh();
    }

    private void initializeViews() {
        context = this;
        com = new CommonFunctions(context);
        pref = CommonSharePrefrences.getInstance(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        signout = (LinearLayout) findViewById(R.id.signout);
        openjobs = (LinearLayout) findViewById(R.id.openjobs);
        compliance = (LinearLayout) findViewById(R.id.compliance);
        noncomplaince = (LinearLayout) findViewById(R.id.noncomplaince);
        navtop = (LinearLayout) findViewById(R.id.navtop);
        overduejobs = (LinearLayout) findViewById(R.id.overduejobs);
        closedjobs = (LinearLayout) findViewById(R.id.closedjobs);
        pendingjobs = (LinearLayout) findViewById(R.id.pendingjobs);
        menu = (RelativeLayout) findViewById(R.id.menu);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        sidemenu_name = (TextView) findViewById(R.id.sidemenu_name);
        sidemenucontact = (TextView) findViewById(R.id.sidemenu_contact);
        totaltickets = (TextView) findViewById(R.id.totaltickets);
        open = (TextView) findViewById(R.id.open);
        refresh = (ImageView) findViewById(R.id.refresh);
        overdue = (TextView) findViewById(R.id.overdue);
        closed = (TextView) findViewById(R.id.closed);
        approval = (TextView) findViewById(R.id.approval);
        input_noncomplaince = (TextView) findViewById(R.id.input_noncomplaince);
        input_complaince = (TextView) findViewById(R.id.input_complaince);
        menuImage = (ImageView) findViewById(R.id.menu_image);
        menuImage2 = (ImageView) findViewById(R.id.menu_image2);
        layout2 = (RelativeLayout) findViewById(R.id.layout2);
        sidemenu_name.setText(pref.getusername());
        sidemenucontact.setText(pref.getUsermobile());
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        ticketAdapter = new RecenttiketAdapter(this, list);
        recyclerView.setAdapter(ticketAdapter);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getticketdetails();
            }
        });
        compliance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, Complaincetickets.class);
                intent1.putExtra("complaineid", "0");
                com.gotoNextActivity(intent1, true);
            }
        });
        noncomplaince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, Complaincetickets.class);
                intent1.putExtra("complaineid", "1");
                com.gotoNextActivity(intent1, true);
            }
        });
        navtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });


        drawer.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                        menuImage.setVisibility(View.GONE);
                        menuImage2.setVisibility(View.VISIBLE);
                        menu.setVisibility(View.GONE);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                        menuImage.setVisibility(View.VISIBLE);
                        menuImage2.setVisibility(View.GONE);
                        menu.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );

        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerOpen();
                menu.setVisibility(View.GONE);
            }
        });

        menuImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDrawer();
                menu.setVisibility(View.VISIBLE);
            }
        });

        openjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.supticketdata.clear();

                Intent intent1 = new Intent(context, SupervisorPMDashboard.class);
                intent1.putExtra("tabpos", "1");
                com.gotoNextActivity(intent1, true);
            }
        });

        overduejobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.supticketdata.clear();

                Intent intent1 = new Intent(context, SupervisorPMDashboard.class);
                intent1.putExtra("tabpos", "3");
                com.gotoNextActivity(intent1, true);
            }
        });

        closedjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.supticketdata.clear();

                Intent intent1 = new Intent(context, SupervisorPMDashboard.class);
                intent1.putExtra("tabpos", "2");
                com.gotoNextActivity(intent1, true);
            }
        });

        pendingjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.supticketdata.clear();

                Intent intent1 = new Intent(context, SupervisorPMDashboard.class);
                intent1.putExtra("tabpos", "4");

                com.gotoNextActivity(intent1, true);
            }
        });

        getticketdetails();

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref.settoken("");
                pref.setdesignation("");
                MyApplication.clearApplicationData();
                finishAffinity();
                com.gotoNextActivity(new Intent(context, SplashActivity.class), true);
            }
        });
    }

    private void getticketdetails() {
        if (com.isConnected()) {
            com.showProgressDialogue();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            SupTicketsPojoResponse response = new SupTicketsPojoResponse();
            response.setUserId(Integer.parseInt(pref.getuserid()));
            response.setType(1);
            barberOrdersResponseCall = apiService.getsuppmticketdata(pref.gettoken(), response);
            barberOrdersResponseCall.enqueue(new Callback<SupTicketsPojoResponse>() {
                @Override
                public void onResponse(Call<SupTicketsPojoResponse> call, Response<SupTicketsPojoResponse> response) {
                    com.dismissProgressDialogue();
                    int statuscode = response.code();
                    if (statuscode == 200) {
                        parseBarberOrders(response.body());
                    } else {
                        com.dismissProgressDialogue();
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
        if (barberOrdersResponse.getStatusCode() == 200) {

            if (barberOrdersResponse.getPMTechnicianData().size() != 0) {
                totaltickets.setText("" + barberOrdersResponse.getCount());
                open.setText("" + barberOrdersResponse.getOpenCount());
                overdue.setText("" + barberOrdersResponse.getOverdueCount());
                closed.setText("" + barberOrdersResponse.getClosedCount());
                approval.setText("" + barberOrdersResponse.getPendingCount());
                input_complaince.setText("" + barberOrdersResponse.getCompliantCount());
                input_noncomplaince.setText("" + barberOrdersResponse.getNonCompliantCount());

                List<SupPMTechnicianData> list1 = barberOrdersResponse.getPMTechnicianData();
                list.clear();
                list.addAll(list1);
                ticketAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
            } else {
                recyclerView.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }


        } else {
        }

    }


    private void initializeDrawerAdapter() {
        drawerList.setAdapter(new SupNavigationDrawerAdapter(this));
    }


    private void initializeEventListeners() {
        drawerList.setOnItemClickListener(this);
    }


    @Override
    public void drawerOpen() {
        drawer.openDrawer(GravityCompat.START);

    }

    @Override
    public void hideDrawer() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void replaceFragment(int position) {
        replaceWithNewFragment(position);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        replaceWithNewFragment(position);
    }

    private void replaceWithNewFragment(int position) {
        switch (position) {
            case 0:
                com.supticketdata.clear();
                finish();
                Intent intent1 = new Intent(context, SupervisorPMDashboard.class);
                intent1.putExtra("tabpos", "0");
                startActivity(intent1);

                break;

            case 1:
                Intent intent2 = new Intent(context, TechnicianActivity.class);

                startActivity(intent2);

                break;

            case 2:
                Intent intent3 = new Intent(context, CreatePlannedEventsActivity.class);

                startActivity(intent3);

                break;

            case 3:
                setLanguage();
                break;
        }
        hideDrawer();
    }

    private void setLanguage() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle(R.string.language_title);
        String[] languages = getResources().getStringArray(R.array.languages);
        adb.setItems(languages, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String[] codes = new String[]{"en", "hi"};
                String language = codes[which];
                pref.setlanguage(language);

                restartActivity();
            }
        });
        adb.show();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {

            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            com.showExitAlert();
        }
    }


    public void checkPermissionsMarsh() {
        if (Build.VERSION.SDK_INT >= 23) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED)
                    && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                DashboardActivity.super.requestAppPermissions(new
                                String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR,
                                Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_SMS}, R.string
                                .runtime_permissions_txt
                        , REQUEST_PERMISSIONS);
            }
        }
    }

    @Override
    public void onPermissionsGranted(final int requestCode) {
        Toast.makeText(this, "Permissions Received.", Toast.LENGTH_LONG).show();
    }

    @SuppressLint("StaticFieldLeak")
    private class GetXMLTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap map = null;
            for (String url : urls) {
                map = downloadImage(url);
            }
            return map;
        }

        // Sets the Bitmap returned by doInBackground
        @Override
        protected void onPostExecute(Bitmap result) {
            profile_image.setImageBitmap(result);

        }

        // Creates Bitmap from InputStream and returns it
        private Bitmap downloadImage(String url) {
            Log.d("token1", url);
            Bitmap bitmap = null;
            InputStream stream = null;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 1;

            try {
                stream = getHttpConnection(url);
                bitmap = BitmapFactory.decodeStream(stream, null, bmOptions);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return bitmap;
        }

        // Makes HttpURLConnection and returns InputStream
        private InputStream getHttpConnection(String urlString) throws IOException {
            InputStream stream = null;
            URL url = new URL(urlString);
            URLConnection connection = url.openConnection();

            try {
                HttpURLConnection httpConnection = (HttpURLConnection) connection;
                httpConnection.setRequestMethod("GET");
                httpConnection.connect();

                if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    stream = httpConnection.getInputStream();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return stream;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getticketdetails();
        //sidemenu_name.setText(comShare.getName());
        //sidemenucontact.setText(comShare.getphone());
        /*if (comShare.getImage().length() > 0) {
            try {
                task = new GetXMLTask();
                task.execute(new String[]{comShare.getImage()});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
    }
}
