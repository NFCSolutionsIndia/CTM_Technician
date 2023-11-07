package com.ctm.technician;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ctm.technician.constants.AddCount;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.fragments.Open;
import com.ctm.technician.fragments.Overdue;
import com.ctm.technician.fragments.Pending;
import com.ctm.technician.fragments.Today;
import com.ctm.technician.fragments.closed;
import com.ctm.technician.utils.BadgedTabLayout;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends LocationActivity implements AdapterView.OnItemClickListener, NavDrawerCallback, AddCount {
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
    LinearLayout signout, navtop;
    RelativeLayout menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        navtop = (LinearLayout) findViewById(R.id.navtop);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        profile_image = (ImageView) findViewById(R.id.profile_image);
        sidemenu_name = (TextView) findViewById(R.id.sidemenu_name);
        sidemenucontact = (TextView) findViewById(R.id.sidemenu_contact);
        menuImage = (ImageView) findViewById(R.id.menu_image);
        menuImage2 = (ImageView) findViewById(R.id.menu_image2);
        menu = (RelativeLayout) findViewById(R.id.menu);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (BadgedTabLayout) findViewById(R.id.tabs);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        sidemenu_name.setText(pref.getusername());
        sidemenucontact.setText(pref.getUsermobile());

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
                        menu.setVisibility(View.GONE);

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
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
                menu.setVisibility(View.VISIBLE);

                hideDrawer();

            }
        });

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


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.addFragment(new Today(), "All");
        viewPagerAdapter.addFragment(new Open(), "Open");
        viewPagerAdapter.addFragment(new closed(), "Closed");
        viewPagerAdapter.addFragment(new Overdue(), "Overdue");
        viewPagerAdapter.addFragment(new Pending(), "Pending");
        viewPager.setAdapter(viewPagerAdapter);
    }

    private void initializeDrawerAdapter() {
        drawerList.setAdapter(new NavigationDrawerAdapter(this));
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
                break;
            case 1:
                Intent intent3 = new Intent(context, SitesActivity.class);
                startActivity(intent3);
                break;

            case 2:
                Intent intent1 = new Intent(context, ActivityHistory.class);
                startActivity(intent1);
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

    @Override
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

                HomeActivity.super.requestAppPermissions(new
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
        finish();
        restartActivity();
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
