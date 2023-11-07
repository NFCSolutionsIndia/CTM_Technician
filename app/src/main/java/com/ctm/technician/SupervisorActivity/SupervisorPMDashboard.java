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
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.technician.BaseActivity;
import com.ctm.technician.MyApplication;
import com.ctm.technician.NavDrawerCallback;
import com.ctm.technician.R;
import com.ctm.technician.SplashActivity;
import com.ctm.technician.Supervisorfragments.SupervisorTickets;
import com.ctm.technician.Supervisorfragments.SupervisorclosedTickets;
import com.ctm.technician.Supervisorfragments.SupervisoropenTickets;
import com.ctm.technician.Supervisorfragments.SupervisoroverdueTickets;
import com.ctm.technician.Supervisorfragments.SupervisorpendingTickets;
import com.ctm.technician.adapter.SupNavigationDrawerAdapter;
import com.ctm.technician.constants.AddCount;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.utils.BadgedTabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SupervisorPMDashboard extends BaseActivity implements AdapterView.OnItemClickListener, NavDrawerCallback, AddCount {
    Context context = this;
    private DrawerLayout drawer;
    private ListView drawerList;
    private static final int REQUEST_PERMISSIONS = 20;
    ImageView profile_image;
    TextView sidemenu_name, sidemenucontact;
    private ImageView menuImage, menuImage2;
    private ViewPager viewPager;
    private BadgedTabLayout tabLayout;
    private CommonFunctions com;
    private CommonSharePrefrences pref;
    Spinner sort;
    LinearLayout signout;
    int tabpos = 0;
    RelativeLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homesupervisor);
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
        drawerList = (ListView) findViewById(R.id.left_drawer);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        sidemenu_name = (TextView) findViewById(R.id.sidemenu_name);
        sidemenucontact = (TextView) findViewById(R.id.sidemenu_contact);
        menuImage = (ImageView) findViewById(R.id.menu_image);
        menuImage2 = (ImageView) findViewById(R.id.menu_image2);
        menu = (RelativeLayout) findViewById(R.id.menu);
        sidemenu_name.setText(pref.getusername());
        sidemenucontact.setText(pref.getUsermobile());
        signout = (LinearLayout) findViewById(R.id.signout);

        tabpos = Integer.parseInt(getIntent().getStringExtra("tabpos"));

        sort = (Spinner) findViewById(R.id.sort_button);
        List<String> list = new ArrayList<String>();

        list.add("Sort by");
        list.add("Site1");
        list.add("Site2");
        list.add("Site3");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sort.setAdapter(dataAdapter);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (BadgedTabLayout) findViewById(R.id.tabs);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setScrollPosition(tabpos, tabpos, true);
        viewPager.setCurrentItem(tabpos);

        menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, DashboardActivity.class);
                startActivity(intent1);
                finish();
            }
        });


    /*    sidemenu_name.setText(comShare.getName());
        sidemenucontact.setText(comShare.getphone());
    */
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

      /*  menuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerOpen();
                menu.setVisibility(View.GONE);


            }
        });*/
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

      /*  menuImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideDrawer();
                menu.setVisibility(View.VISIBLE);


            }
        });*/


    }

    private void setupViewPager(final ViewPager viewPager) {
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());


        viewPagerAdapter.addFragment(new SupervisorTickets(), "All");
        viewPagerAdapter.addFragment(new SupervisoropenTickets(), "Open");
        viewPagerAdapter.addFragment(new SupervisorclosedTickets(), "Closed");
        viewPagerAdapter.addFragment(new SupervisoroverdueTickets(), "Overdue");
        viewPagerAdapter.addFragment(new SupervisorpendingTickets(), "Pending");

        viewPager.setAdapter(viewPagerAdapter);
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

    public void addCount(String all, String open, String closed, String Overdue, String Pending) {


        if (!all.equals("0")) {
            tabLayout.setBadgeText(0, all);
        } else {
            tabLayout.setBadgeText(0, "0");
        }
        if (!open.equals("0")) {
            tabLayout.setBadgeText(1, open);
        } else {
            tabLayout.setBadgeText(1, "0");
        }
        if (!closed.equals("0")) {
            tabLayout.setBadgeText(2, closed);
        } else {
            tabLayout.setBadgeText(2, "0");
        }
        if (!Overdue.equals("0")) {
            tabLayout.setBadgeText(3, Overdue);
        } else {
            tabLayout.setBadgeText(3, "0");
        }
        if (!Pending.equals("0")) {
            tabLayout.setBadgeText(4, Pending);
        } else {
            tabLayout.setBadgeText(4, "0");
        }

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
                break;
            case 1:
                Intent intent5 = new Intent(context, TechnicianActivity.class);
                startActivity(intent5);
                break;

            case 2:
                Intent intent = new Intent(context, CreatePlannedEventsActivity.class);
                startActivity(intent);
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
            Intent intent1 = new Intent(context, DashboardActivity.class);
            startActivity(intent1);
            finish();
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

                SupervisorPMDashboard.super.requestAppPermissions(new
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
    protected void onStop() {
        super.onStop();
        com.supticketdata.clear();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tabpos = 0;
        tabLayout.setScrollPosition(tabpos, tabpos, true);
        viewPager.setCurrentItem(tabpos);

        finish();
        restartActivity();

    }
}
