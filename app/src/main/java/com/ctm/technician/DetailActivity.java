package com.ctm.technician;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.ctm.technician.adapter.ChecklistAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Checklist.Checklistsubmit;
import com.ctm.technician.models.Checklist.PMAssetControl;
import com.ctm.technician.models.Checklist.PMTicketssubmitlist;
import com.ctm.technician.models.Checklist.UpdatePmticket;
import com.ctm.technician.models.Tickets.Checklistvalue;
import com.ctm.technician.utils.ConnectivityReceiver;
import com.ctm.technician.utils.onItemClick;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends LocationActivity implements View.OnClickListener, onItemClick, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ConnectivityReceiver.ConnectivityReceiverListener {
    Button btnSubmit, btnstart;
    int ticketid, typeid, siteid, siteassetid, assetCheckListStatus;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ChecklistAdapter chklistAdapter;
    private RecyclerView recyclerView;
    LinearLayout pm, linearbtn;
    TextView tower, techname, techcontact, site_name, browse, nodata, inputid, plandate, lastpmdate, input_sitename, inputsite_id, reason, status, sitedescription, input_date;
    private static final int PICK_CAMERA_REQUEST = 14;
    private static final int PICK_IMAGE_REQUEST = 11;
    List<String> strings = new ArrayList<>(Arrays.asList("", "", ""));
    ImageView imageview_profilepic;
    LinearLayout technicianlayout, linearsite;
    String json, siteuniqueid, siteassetname, sitename, tcstatus, tech_name, tech_cont, site_uniqueid;
    TextView title, radiustext;
    RelativeLayout appbar;
    ImageView backimage;
    Integer position;
    String imageTempName, distance;
    private static final int REQUEST_CAMERA = 113;
    private List<PMAssetControl> getstaffMembersLists = new ArrayList<>();
    ArrayList<String> selectedPhotos = new ArrayList<>();
    boolean gps_enabled = false;
    String latitude1, longitude1;
    AlertDialog.Builder builder;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailscreen);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        com.assetcontrollist.clear();
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);
        backimage = (ImageView) findViewById(R.id.back_image);
        btnSubmit = (Button) findViewById(R.id.btnsubmit);
        btnstart = (Button) findViewById(R.id.btnstart);
        browse = (TextView) findViewById(R.id.browse);
        tower = (TextView) findViewById(R.id.tower);
        title = (TextView) findViewById(R.id.title);
        techname = (TextView) findViewById(R.id.techname);
        radiustext = (TextView) findViewById(R.id.radiustext);
        techcontact = (TextView) findViewById(R.id.techcontact);
        site_name = (TextView) findViewById(R.id.site_name);
        pm = (LinearLayout) findViewById(R.id.pm);
        linearbtn = (LinearLayout) findViewById(R.id.linearbtn);
        linearsite = (LinearLayout) findViewById(R.id.linearsite);
        appbar = (RelativeLayout) findViewById(R.id.appbar);
        technicianlayout = (LinearLayout) findViewById(R.id.technicianlayout);
        imageview_profilepic = (ImageView) findViewById(R.id.imageview_profilepic);

        if (!comShare.getdesignation().equals("Technician")) {
            btnSubmit.setVisibility(View.GONE);
            linearbtn.setVisibility(View.GONE);
        }

        ticketid = Integer.parseInt(getIntent().getStringExtra("ticket_id"));
        typeid = Integer.parseInt(getIntent().getStringExtra("type_id"));
        assetCheckListStatus = Integer.parseInt(getIntent().getStringExtra("assetCheckListStatus"));
        siteassetid = Integer.parseInt(getIntent().getStringExtra("siteasset_id"));
        distance = getIntent().getStringExtra("distance");

        siteassetname = getIntent().getStringExtra("asset_name");
        siteuniqueid = getIntent().getStringExtra("site_uniqueid");
        sitename = getIntent().getStringExtra("site_name");
        siteid = Integer.parseInt(getIntent().getStringExtra("site_id"));
        tcstatus = getIntent().getStringExtra("tcstatus");
        tower.setText(siteassetname);
        title.setText(siteassetname + " Checklist");
        site_name.setText("#" + siteuniqueid + "," + sitename);

        nodata = (TextView) findViewById(R.id.no_data_text);
        inputid = (TextView) findViewById(R.id.input_id);
        plandate = (TextView) findViewById(R.id.pmplandate);
        lastpmdate = (TextView) findViewById(R.id.lastpmdate);
        input_sitename = (TextView) findViewById(R.id.input_sitename);
        inputsite_id = (TextView) findViewById(R.id.inputsite_id);
        reason = (TextView) findViewById(R.id.reason);
        status = (TextView) findViewById(R.id.status);
        title = (TextView) findViewById(R.id.title);
        sitedescription = (TextView) findViewById(R.id.description);
        input_date = (TextView) findViewById(R.id.input_date);
        backimage = (ImageView) findViewById(R.id.back_image);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        recyclerView.addItemDecoration(
                new DividerItemDecoration(this, layoutManager.getOrientation()) {
                    @Override
                    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                        int position = parent.getChildAdapterPosition(view);
                        if (position == parent.getAdapter().getItemCount() - 1) {
                            outRect.setEmpty();
                        } else {
                            super.getItemOffsets(outRect, view, parent, state);
                        }

                    }
                }
        );

        if (comShare.getdesignation().equals("Technician")) {
            technicianlayout.setVisibility(View.GONE);
            appbar.setBackgroundColor(getResources().getColor(R.color.white));
            title.setTextColor(getResources().getColor(R.color.activecolor));
            backimage.setImageResource(R.mipmap.arrowblue);
        } else {
            tech_name = getIntent().getStringExtra("techname");
            tech_cont = getIntent().getStringExtra("techcontact");
            techcontact.setText(tech_cont);
            techname.setText(tech_name);
            technicianlayout.setVisibility(View.VISIBLE);
            appbar.setBackgroundColor(getResources().getColor(R.color.black));
            title.setTextColor(getResources().getColor(R.color.white));
            backimage.setImageResource(R.mipmap.whitearrow);
        }
        Checklistdata();
        initializeViews();
        initializeOnClickListeners();
    }

    private void initializeOnClickListeners() {
        btnSubmit.setOnClickListener(this);
        btnstart.setOnClickListener(this);
    }

    private void initializeViews() {

        com.assetcontrollist.clear();

        backimage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        browse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICK_CAMERA_REQUEST);
            }
        });
    }


    @Override
    public void onClick(View view) {
        com.HidingSoftKeyBoard(view);
        switch (view.getId()) {
            case R.id.btnsubmit:
                Checklistsubmit();
                break;
            case R.id.btnstart:
                break;
        }
    }

    private void Checklistdata() {
        if (Networking.isNetworkAvailable(this)) {
            com.showProgressDialogue();
            Checklistvalue response1 = new Checklistvalue();
            response1.setPMTicketId(ticketid);
            response1.setAssetId(siteassetid);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<PMTicketssubmitlist> getMyServicesResponseCall = apiService.getpmticketchecklistdata(comShare.gettoken(), response1);
            getMyServicesResponseCall.enqueue(new Callback<PMTicketssubmitlist>() {
                @Override
                public void onResponse(Call<PMTicketssubmitlist> call, Response<PMTicketssubmitlist> response) {
                    PMTicketssubmitlist sitesResponse = response.body();
                    int statusCode = response.code();
                    com.dismissProgressDialogue();
                    if (statusCode == 200) {
                        parseBarberOrders(sitesResponse);
                    } else {
                        com.showAlertDialogOK(getResources().getString(R.string.something));
                    }
                }

                @Override
                public void onFailure(Call<PMTicketssubmitlist> call, Throwable t) {
                    com.dismissProgressDialogue();
                    com.showAlertDialogOK(getResources().getString(R.string.strErrorMessage));
                }
            });
        } else {
            com.showinternetdialog();
        }
    }

    private void parseBarberOrders(PMTicketssubmitlist barberOrdersResponse) {
        if (barberOrdersResponse.getStatusCode() == 200) {
            getstaffMembersLists = barberOrdersResponse.getPmCheckLists();
            chklistAdapter = new ChecklistAdapter(barberOrdersResponse.getPmCheckLists(), tcstatus, assetCheckListStatus, this, comShare, DetailActivity.this);
            recyclerView.setAdapter(chklistAdapter);
            if (comShare.getdesignation().equals("Technician")) {
                if (assetCheckListStatus == 1) {
                    linearsite.setVisibility(View.VISIBLE);
                    if (tcstatus.equals("Reassign")) {
                        radiustext.setText("You are " + distance + " away from site radius");
                    } else {
                        radiustext.setText("You have submitted the ticket at " + distance + " away from site radius");
                    }
                } else {
                    linearsite.setVisibility(View.VISIBLE);
                    radiustext.setText("You are " + distance + " away from site radius");
                }
            } else {
                if (assetCheckListStatus == 1) {
                    linearsite.setVisibility(View.VISIBLE);
                    radiustext.setText("Technician submitted the ticket at " + distance + " away from site radius");
                } else {
                    linearsite.setVisibility(View.GONE);
                }
            }
        } else {
            nodata.setText("No Ticktes Found");
        }
    }


    private void Checklistsubmit() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c.getTime());

        LocationManager lm1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = lm1.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled) {
            com.showAlertDialogOK(getResources().getString(R.string.nogps));
            return;
        } else {
            GPSTracker gps = new GPSTracker(DetailActivity.this);
            if (gps.canGetLocation()) {
                latitude1 = String.valueOf(gps.getLatitude());
                longitude1 = String.valueOf(gps.getLongitude());
            } else {
                com.showAlertDialogOK(getResources().getString(R.string.nogps));
                return;
            }
        }

        if (Networking.isNetworkAvailable(this)) {
            com.showProgressDialogue();
            Checklistsubmit response = new Checklistsubmit();
            response.setPmTicketId(ticketid);
            response.setAssetId(siteassetid);
            response.setUserId(Integer.parseInt(comShare.getuserid()));
            response.setCreatedDate(formattedDate);
            response.setLatitude("" + latitude1);
            response.setLongitude("" + longitude1);
            response.setType(1);
            response.setPmAssetsControlValueLists(com.datalist);
            Gson gson = new Gson();
            json = gson.toJson(response);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdatePmticket> getMyServicesResponseCall = apiService.updatepmticket(comShare.gettoken(), response);
            getMyServicesResponseCall.enqueue(new Callback<UpdatePmticket>() {
                @Override
                public void onResponse(Call<UpdatePmticket> call, Response<UpdatePmticket> response) {
                    UpdatePmticket sitesResponse = response.body();
                    int statusCode = response.code();
                    com.dismissProgressDialogue();
                    if (statusCode == 200) {
                        parsesubmitresponse(sitesResponse);
                    } else {
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
            Toast.makeText(this, barberOrdersResponse.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, barberOrdersResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_CAMERA_REQUEST && resultCode == RESULT_OK && null != data) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                getstaffMembersLists.get(position).setPmAssetCheckStandardValue(getStringImage(photo));
                com.datalist.get(position).setPMAssetCheckStandardValue(getStringImage(photo));
                OnItemClick();
                chklistAdapter.notifyItemChanged(position);

            }
            if (resultCode == RESULT_OK &&
                    (requestCode == PICK_IMAGE_REQUEST || requestCode == PhotoPreview.REQUEST_CODE)) {
                List<String> photos = null;
                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                }
                selectedPhotos.clear();
                strings.clear();
                if (photos != null) {
                    selectedPhotos.addAll(photos);
                }
                String imgUrl = selectedPhotos.get(0);


                for (int i = 0; i < selectedPhotos.size(); i++) {
                    Uri uri = Uri.fromFile(new File(selectedPhotos.get(i).toString()));

                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        InputStream is = null;
                        is = new FileInputStream(selectedPhotos.get(i).toString());
                        BitmapFactory.decodeStream(is, null, options);
                        is.close();
                        is = new FileInputStream(selectedPhotos.get(i).toString());
                        options.inSampleSize = Math.max(options.outWidth / 480, options.outHeight / 288);
                        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);

                        String picturePath = getStringImage(bitmap);

                        getstaffMembersLists.get(position).setPmAssetCheckStandardValue(picturePath);
                        com.datalist.get(position).setPMAssetCheckStandardValue(picturePath);
                        OnItemClick();

                        chklistAdapter.notifyItemChanged(position);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void opengallery(int pos, String pmAssetCheckStandardValue) {
        try {
            position = pos;
            selectedPhotos.clear();
            PhotoPicker.builder()
                    .setPhotoCount(1)
                    .setShowCamera(false)
                    .setSelected(selectedPhotos)
                    .setPreviewEnabled(false)
                    .start(this, PICK_IMAGE_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void captureImage(int pos, String imageName) {
        try {
            position = pos;
            imageTempName = imageName;
            selectCaptureImage(PICK_CAMERA_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void selectCaptureImage(int i) {
        try {


            String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            if (!hasPermissions(DetailActivity.this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(DetailActivity.this, PERMISSIONS, REQUEST_CAMERA);
                return;
            }
            String filePath = Environment.getExternalStorageDirectory() + "/s1.jpeg";
            File file = new File(filePath);
            Uri output = Uri.fromFile(file);
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, i);
        } catch (Exception e) {
        }


    }

    public String getStringImage(Bitmap bmp) {

        BitmapFactory.Options options = null;
        options = new BitmapFactory.Options();
        options.inScaled = false;
        options.inSampleSize = 1;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        strings.add(encodedImage);
        return encodedImage;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void OnItemClick() {
        for (int i = 0; i < com.datalist.size(); i++) {
            if (com.datalist.get(i).getPMAssetCheckStandardValue().equals("")) {
                btnSubmit.setBackground(getResources().getDrawable(R.color.light_gray));
                btnSubmit.setTextColor(getResources().getColor(R.color.black));
                btnSubmit.setSelected(false);
                btnSubmit.setClickable(false);
                btnSubmit.setVisibility(View.VISIBLE);
                return;
            } else {
                btnSubmit.setBackground(getResources().getDrawable(R.drawable.btngradiantheader));
                btnSubmit.setTextColor(getResources().getColor(R.color.white));
                btnSubmit.setSelected(true);
                btnSubmit.setClickable(true);
                btnSubmit.setVisibility(View.VISIBLE);
            }
            if (!comShare.getdesignation().equals("Technician")) {
                btnSubmit.setVisibility(View.GONE);
                linearbtn.setVisibility(View.GONE);
            }
            if (!tcstatus.equals("Open") && !tcstatus.equals("Reassign") && !tcstatus.equals("Overdue")) {
                btnSubmit.setVisibility(View.GONE);
                linearbtn.setVisibility(View.GONE);
            }
        }

    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            com.showinternetdialog();
        }
    }


}


