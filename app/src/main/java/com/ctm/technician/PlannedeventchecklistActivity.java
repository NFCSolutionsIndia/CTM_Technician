package com.ctm.technician;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.technician.adapter.PMReasonListAdapter;
import com.ctm.technician.apis.ApiClient;
import com.ctm.technician.apis.ApiInterface;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Checklist.PEChecklistsubmit;
import com.ctm.technician.models.Checklist.PMTicketdetails;
import com.ctm.technician.models.Checklist.TicketHistoryList;
import com.ctm.technician.models.Checklist.UpdatePmticket;
import com.ctm.technician.models.Tickets.Checklistvalue;
import com.ctm.technician.utils.ConnectivityReceiver;
import com.ctm.technician.utils.ConstantsHelper;
import com.ctm.technician.utils.onItemClick;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static me.iwf.photopicker.utils.PermissionsConstant.REQUEST_CAMERA;

public class PlannedeventchecklistActivity extends LocationActivity implements View.OnClickListener, onItemClick, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private ImageView backImage;
    private RecyclerView recyclerview2;
    Button btnsubmit;
    EditText reassignreason, submitreassignreason;
    View historyv1;
    TextView labelsubmit, labelrason, radiustext, inputid, plandate, lastpmdate, input_sitename, heading, status, sitedescription, input_date;
    String site_uniqueid, sitename, cretaeddate, header, description, lastdate, pmplandate, tcstatus;
    int ticketid, typeid, siteid;
    private LinearLayout addServices, tickethistory, listtickets, linearsite;
    AlertDialog.Builder builder;
    RelativeLayout r1, linearbtn;
    ImageView dot;
    TextView btn;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    private static final int PICK_CAMERA_REQUEST = 14;
    List<String> strings = new ArrayList<>(Arrays.asList("", "", ""));
    private String profilepic;
    LinearLayout layoutreason;
    private PMReasonListAdapter reasonAdapter;
    private String latitude = "", longitude = "";
    protected GoogleApiClient mGoogleApiClient;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int PICK_IMAGE_REQUEST = 11;
    ImageView img;
    String viewtechimage;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plannedeventchecklistcreen);
        com = new CommonFunctions(this);
        comShare = CommonSharePrefrences.getInstance(this);
        initializeViews();
        initializeOnClickListeners();
        getsitedetails();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeViews() {
        backImage = (ImageView) findViewById(R.id.back_image);
        historyv1 = (View) findViewById(R.id.historyv1);
        addServices = (LinearLayout) findViewById(R.id.add_layout);
        reassignreason = (EditText) findViewById(R.id.reassignreason);
        submitreassignreason = (EditText) findViewById(R.id.submitreassignreason);
        r1 = (RelativeLayout) findViewById(R.id.r1);
        labelrason = (TextView) findViewById(R.id.labelrason);
        labelsubmit = (TextView) findViewById(R.id.labelsubmit);
        tickethistory = (LinearLayout) findViewById(R.id.tickethistory);
        linearbtn = (RelativeLayout) findViewById(R.id.linearbtn);
        listtickets = (LinearLayout) findViewById(R.id.listtickets);
        layoutreason = (LinearLayout) findViewById(R.id.layoutreason);
        linearsite = (LinearLayout) findViewById(R.id.linearsite);

        dot = (ImageView) findViewById(R.id.dot);
        btn = (TextView) findViewById(R.id.btn);
        img = (ImageView) findViewById(R.id.img);
        addServices.removeAllViews();
        siteid = Integer.parseInt(getIntent().getStringExtra("site_id"));
        ticketid = Integer.parseInt(getIntent().getStringExtra("ticket_id"));
        typeid = Integer.parseInt(getIntent().getStringExtra("type_id"));
        typeid = Integer.parseInt(getIntent().getStringExtra("type_id"));
        sitename = getIntent().getStringExtra("site_name");
        site_uniqueid = getIntent().getStringExtra("site_uniqueid");
        cretaeddate = getIntent().getStringExtra("created_date");
        header = getIntent().getStringExtra("heading");
        description = getIntent().getStringExtra("description");
        lastdate = getIntent().getStringExtra("lastpmdate");
        pmplandate = getIntent().getStringExtra("pmplandate");
        tcstatus = getIntent().getStringExtra("status");
        inputid = (TextView) findViewById(R.id.input_id);
        radiustext = (TextView) findViewById(R.id.radiustext);
        plandate = (TextView) findViewById(R.id.pmplandate);
        lastpmdate = (TextView) findViewById(R.id.lastpmdate);
        input_sitename = (TextView) findViewById(R.id.input_sitename);
        heading = (TextView) findViewById(R.id.heading);
        status = (TextView) findViewById(R.id.status);
        sitedescription = (TextView) findViewById(R.id.description);
        input_date = (TextView) findViewById(R.id.input_date);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        inputid.setText("#" + ticketid);
        input_sitename.setText("#" + site_uniqueid + "," + sitename);
        sitedescription.setText(description);
        plandate.setText(pmplandate);
        lastpmdate.setText(lastdate);
        r1.setVisibility(View.VISIBLE);
        input_date.setText(cretaeddate);
        status.setText("\u25CF " + tcstatus);
        heading.setText(header);
        linearsite.setVisibility(View.VISIBLE);

        if (tcstatus.equals("Reassign")) {
            status.setText("\u25CF " + "Re-Assign");
        }
        recyclerview2 = (RecyclerView) findViewById(R.id.recyclerview2);

        LocationManager lm1 = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        try {
            gps_enabled = lm1.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled) {
            checkGpsStatus();
        } else {
            GPSTracker gps = new GPSTracker(this);
            if (gps.canGetLocation()) {
                latitude = String.valueOf(gps.getLatitude());
                longitude = String.valueOf(gps.getLongitude());
            } else {
                com.showAlertDialogOK(getResources().getString(R.string.nogps));
                return;
            }
        }
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview2.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerview2.setHasFixedSize(true);
        recyclerview2.setNestedScrollingEnabled(false);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameragallery();
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );

                final Dialog settingsDialog = new Dialog(PlannedeventchecklistActivity.this);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.imageview, null));

                ImageView viewimg = (ImageView) settingsDialog.findViewById(R.id.viewimg);
                ImageView close = (ImageView) settingsDialog.findViewById(R.id.clolse);
                viewimg.setImageBitmap(ConstantsHelper.getBitmap(PlannedeventchecklistActivity.this, viewtechimage));

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingsDialog.cancel();
                    }
                });

                settingsDialog.show();
            }
        });

    }

    private void initializeOnClickListeners() {
        backImage.setOnClickListener(this);
        btnsubmit.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // getsitedetails();

    }

    private void cameragallery() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View promptView = layoutInflater.inflate(R.layout.dialogbox_album, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        Button camera = (Button) promptView.findViewById(R.id.button_camera);
        Button album = (Button) promptView.findViewById(R.id.button_album);


        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openGallary();
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(promptView);
        alertDialog.show();
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectCaptureImage(PICK_CAMERA_REQUEST);
                alertDialog.dismiss();
            }
        });

    }

    private void openGallary() {
        try {
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

    public void selectCaptureImage(int i) {
        String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (!hasPermissions(PlannedeventchecklistActivity.this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(PlannedeventchecklistActivity.this, PERMISSIONS, REQUEST_CAMERA);
            return;
        }
        String filePath = Environment.getExternalStorageDirectory() + "/s1.jpeg";
        File file = new File(filePath);
        Uri output = Uri.fromFile(file);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, i);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_image:
                finish();
                break;
            case R.id.btnsubmit:
                if (TextUtils.isEmpty(submitreassignreason.getText().toString())) {
                    Toast.makeText(this, "Comment cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(profilepic)) {
                    Toast.makeText(this, "Image cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                Checklistsubmit();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void checkGpsStatus() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //Creating LocationSettingsRequest.Builder
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        builder.setAlwaysShow(true); // this is the key ingredient

        //To check whether current location settings are satisfied:
        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {  //Check the location settings from LocationSettingsResult

                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates LS_state = locationSettingsResult.getLocationSettingsStates(); //Retriving the current state of location by calling getLocationSettingsStates()
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        getsitedetails();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(PlannedeventchecklistActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    private void getsitedetails() {
        if (com.isConnected()) {
            com.showProgressDialogue();
            Checklistvalue response1 = new Checklistvalue();
            response1.setPMTicketId(ticketid);
            response1.setLatitude("" + latitude);
            response1.setLongitude("" + longitude);
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<PMTicketdetails> call = apiService.getPMTicketDetails(comShare.gettoken(), response1);
            call.enqueue(new Callback<PMTicketdetails>() {
                @Override
                public void onResponse(Call<PMTicketdetails> call, Response<PMTicketdetails> response) {
                    com.dismissProgressDialogue();
                    if (response.code() == 200) {
                        parsesitedetailsResponse(response.body());
                    } else {
                        com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                    }
                }

                @Override
                public void onFailure(Call<PMTicketdetails> call, Throwable t) {
                    com.dismissProgressDialogue();

                    com.showAlertDialogOK(com.getString(R.string.strErrorMessage));
                }
            });
        } else {
            com.showinternetdialog();
        }
    }

    private List<TicketHistoryList> reverseListOrder(List<TicketHistoryList> status) {
        Iterator<TicketHistoryList> it = status.iterator();
        List<TicketHistoryList> destination = new ArrayList<>();
        while (it.hasNext()) {
            destination.add(0, it.next());
            it.remove();
        }
        return destination;
    }

    private void parsesitedetailsResponse(PMTicketdetails body) {
        recyclerview2.setVisibility(View.VISIBLE);
        reasonAdapter = new PMReasonListAdapter(reverseListOrder(body.getTicketHistoryList()), this, tcstatus, siteid, sitename, comShare, ticketid, typeid, PlannedeventchecklistActivity.this);
        recyclerview2.setAdapter(reasonAdapter);
        String distance = "" + body.getDistance();


        if (tcstatus.equals("Pending") || tcstatus.equals("Closed")) {
            linearsite.setVisibility(View.VISIBLE);
            radiustext.setText("You have submitted the ticket at " + body.getDistance() + " away from site radius");
        } else {
            linearsite.setVisibility(View.VISIBLE);
            radiustext.setText("You are " + distance + " away from site radius");
        }


        if (TextUtils.isEmpty(body.getCommentPhoto())) {
            img.setClickable(false);
            img.setImageResource(R.mipmap.rectimg);
        } else {
            img.setClickable(true);
            profilepic = body.getCommentPhoto();
            img.setImageBitmap(ConstantsHelper.getBitmap(this, body.getCommentPhoto()));
            viewtechimage = body.getCommentPhoto();
        }


        dot.setVisibility(View.VISIBLE);

        if (body.getTechComment() != null) {
            submitreassignreason.setText(body.getTechComment());
            submitreassignreason.setSelection(submitreassignreason.getText().length());
        }
        if (tcstatus.equals("Open") || tcstatus.equals("Reassign") || tcstatus.equals("Overdue")) {
            linearbtn.setVisibility(View.VISIBLE);
        } else {
            linearbtn.setVisibility(View.GONE);
            submitreassignreason.setFocusable(false);
            submitreassignreason.setClickable(false);
            btn.setFocusable(false);
            btn.setClickable(false);
            btn.setBackground(getResources().getDrawable(R.drawable.buttonborderdisable));
        }
        if (tcstatus.equals("Closed")) {
            labelrason.setVisibility(View.GONE);
            layoutreason.setVisibility(View.GONE);
            reassignreason.setVisibility(View.GONE);

        }
        if (body.getTicketHistoryList() != null) {
            tickethistory.setVisibility(View.VISIBLE);
            listtickets.setVisibility(View.VISIBLE);
            historyv1.setVisibility(View.VISIBLE);
        }
        if (tcstatus.equals("Open")) {
            tickethistory.setVisibility(View.GONE);
        }
        if (tcstatus.equals("Overdue")) {
            tickethistory.setVisibility(View.GONE);
        }
        if (!body.getComments().equals("")) {
            reassignreason.setText(body.getComments());
            labelrason.setVisibility(View.VISIBLE);
            layoutreason.setVisibility(View.VISIBLE);
            reassignreason.setVisibility(View.VISIBLE);
        } else {
            layoutreason.setVisibility(View.GONE);
            labelrason.setVisibility(View.GONE);
            reassignreason.setVisibility(View.GONE);
        }
    }


    private void Checklistsubmit() {
        if (Networking.isNetworkAvailable(this)) {
            com.showProgressDialogue();

            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = df.format(c.getTime());

            PEChecklistsubmit response = new PEChecklistsubmit();

            response.setPmTicketId(ticketid);
            response.setUserId(Integer.parseInt(comShare.getuserid()));
            response.setCreatedDate(formattedDate);
            response.setLatitude(latitude);
            response.setLongitude(longitude);
            response.setType(1);
            String reason = submitreassignreason.getText().toString();
            response.setComments(reason);
            response.setPhoto(profilepic);

            Log.e("photo", profilepic);

            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdatePmticket> getMyServicesResponseCall = apiService.UpdatePETicke(comShare.gettoken(), response);
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
            Intent i = new Intent(this, SuccessActivity.class);
            i.putExtra("submit_id", "" + ticketid);
            i.putExtra("submit_message", String.valueOf(barberOrdersResponse.getMessage()));
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, barberOrdersResponse.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void OnItemClick() {
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {
            com.showinternetdialog();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == PICK_CAMERA_REQUEST && resultCode == RESULT_OK && null != data) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(photo);
                viewtechimage = getStringImage(photo);
                profilepic = getStringImage(photo);
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
                        options.inSampleSize = Math.max(options.outWidth / 660, options.outHeight / 488);
                        Bitmap bitmap = BitmapFactory.decodeStream(is, null, options);
                        img.setImageBitmap(bitmap);
                        img.setClickable(true);
                        viewtechimage = getStringImage(bitmap);

                        profilepic = getStringImage(bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
