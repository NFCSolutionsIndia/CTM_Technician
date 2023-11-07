package com.ctm.technician.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ctm.technician.DetailActivity;
import com.ctm.technician.R;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.models.Checklist.PMAssetControl;
import com.ctm.technician.models.Checklist.PMAssetsubmitdata;
import com.ctm.technician.utils.ConstantsHelper;
import com.ctm.technician.utils.onItemClick;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChecklistAdapter extends RecyclerView.Adapter<ChecklistAdapter.MyViewHolder> implements View.OnClickListener {

    private List<PMAssetControl> getstaffMembersLists;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private DetailActivity context;
    String position = "", tcstatus;
    int assetCheckListStatus;
    private onItemClick click;
    private List<PMAssetControl> serviceResponseList = new ArrayList<>();
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int CAMERA_IMAGE_REQUEST = 1;
    static String imagepathh = "";

    public ChecklistAdapter(List<PMAssetControl> staffMembersLists, String tcstatus, int assetCheckListStatus, onItemClick click, CommonSharePrefrences comShare, DetailActivity context) {
        getstaffMembersLists = staffMembersLists;

        this.comShare = comShare;
        this.context = context;
        this.click = click;
        this.tcstatus = tcstatus;
        this.assetCheckListStatus = assetCheckListStatus;
        serviceResponseList.addAll(staffMembersLists);
        com.datalist.clear();

    }


    @Override
    public ChecklistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.checklistdata, parent, false);


        final MyViewHolder holder = new MyViewHolder(parent);
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(holder);


        click.OnItemClick();

        return new ChecklistAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final ChecklistAdapter.MyViewHolder holder, final int position) {
        final PMAssetControl response = getstaffMembersLists.get(position);
        PMAssetsubmitdata submitData = new PMAssetsubmitdata();

        submitData.setPmTicketDetailId(response.getPmTicketDetailId());
        submitData.setPMAssetCheckStandardValue(response.getPmAssetCheckStandardValue());
        com.datalist.add(submitData);


        if (response.getControlType().equals("TextBox")) {
            holder.question.setText("" + response.getPmAssetCheckLabel());
            holder.textusedmw.setVisibility(View.VISIBLE);
            holder.datetext.setVisibility(View.GONE);
            holder.l4.setVisibility(View.GONE);

            holder.textusedmw.setText("" + response.getPmAssetCheckStandardValue());

            if (comShare.getdesignation().equals("Technician")) {
                if (assetCheckListStatus == 1) {
                    if (!tcstatus.equals("Open") && !tcstatus.equals("Reassign") && !tcstatus.equals("Overdue")) {

                        holder.textusedmw.setClickable(false);
                        holder.textusedmw.setFocusable(false);
                        holder.textusedmw.setEnabled(false);

                    }
                }
            } else {

                holder.textusedmw.setClickable(false);
                holder.textusedmw.setFocusable(false);
                holder.textusedmw.setEnabled(false);

            }


            holder.textusedmw.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    com.datalist.get(position).setPMAssetCheckStandardValue(s.toString());
                    click.OnItemClick();
                }
            });


        } else if (response.getControlType().equals("Number")) {
            holder.question.setText("" + response.getPmAssetCheckLabel());
            holder.textusedmw.setVisibility(View.VISIBLE);
            holder.datetext.setVisibility(View.GONE);
            holder.l4.setVisibility(View.GONE);
            holder.textusedmw.setInputType(InputType.TYPE_CLASS_NUMBER);

            holder.textusedmw.setText("" + response.getPmAssetCheckStandardValue());

            if (comShare.getdesignation().equals("Technician")) {
                if (assetCheckListStatus == 1) {
                    if (!tcstatus.equals("Open") && !tcstatus.equals("Reassign") && !tcstatus.equals("Overdue")) {

                        holder.textusedmw.setClickable(false);
                        holder.textusedmw.setFocusable(false);
                        holder.textusedmw.setEnabled(false);

                    }
                }
            } else {

                holder.textusedmw.setClickable(false);
                holder.textusedmw.setFocusable(false);
                holder.textusedmw.setEnabled(false);

            }


            holder.textusedmw.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    com.datalist.get(position).setPMAssetCheckStandardValue(s.toString());
                    click.OnItemClick();
                }
            });


        } else if (response.getControlType().equals("Stepper")) {
            holder.question.setText("" + response.getPmAssetCheckLabel());
            holder.textusedmw.setVisibility(View.GONE);
            holder.datetext.setVisibility(View.GONE);

            holder.l4.setVisibility(View.GONE);
            holder.l5.setVisibility(View.VISIBLE);
            holder.steppercount.setText("" + response.getPmAssetCheckStandardValue());


            if (comShare.getdesignation().equals("Technician")) {
                if (assetCheckListStatus == 1) {
                    if (!tcstatus.equals("Open") && !tcstatus.equals("Reassign") && !tcstatus.equals("Overdue")) {

                        holder.add.setClickable(false);
                        holder.add.setFocusable(false);
                        holder.add.setEnabled(false);

                        holder.subtract.setClickable(false);
                        holder.subtract.setFocusable(false);
                        holder.subtract.setEnabled(false);

                    }
                }
            } else {

                holder.add.setClickable(false);
                holder.add.setFocusable(false);
                holder.add.setEnabled(false);

                holder.subtract.setClickable(false);
                holder.subtract.setFocusable(false);
                holder.subtract.setEnabled(false);

            }
            holder.add.setTag(Integer.valueOf(position));

            holder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    holder.textusedmw.setFocusable(false);


                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    int count = 0;

                    try {
                        count = NumberFormat.getInstance().parse(holder.steppercount.getText().toString()).intValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    count++;
                    //count =  + 1;
                    holder.steppercount.setText("" + count);


                    com.datalist.get(position).setPMAssetCheckStandardValue(holder.steppercount.getText().toString());
                    click.OnItemClick();
                    return;

                }
            });

            holder.subtract.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );
                    holder.textusedmw.setFocusable(false);

                    int count = 0;
                    try {
                        count = NumberFormat.getInstance().parse(holder.steppercount.getText().toString()).intValue();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (count < 0) {
                        count = 0;
                        holder.steppercount.setText(Integer.parseInt(holder.steppercount.getText().toString()) + "");
                    }
                    if (count > 0) {
                        count = Integer.parseInt(holder.steppercount.getText().toString()) - 1;
                        holder.steppercount.setText(count + "");
                    }
                    com.datalist.get(position).setPMAssetCheckStandardValue(holder.steppercount.getText().toString());

                    click.OnItemClick();
                    return;


                }
            });

        } else if (response.getControlType().equals("RadioButton")) {
            holder.datetext.setVisibility(View.GONE);
            holder.l3.setVisibility(View.VISIBLE);
            holder.l4.setVisibility(View.GONE);

            final RadioButton[] rb = new RadioButton[response.getPmAssetsControlValueLists().size()];
            holder.rg.setOrientation(RadioGroup.HORIZONTAL);
            for (int i = 0; i < response.getPmAssetsControlValueLists().size(); i++) {
                rb[i] = new RadioButton(context);
                rb[i].setId(response.getPmAssetsControlValueLists().get(i).getPmCheckControlId());
                rb[i].setText("" + response.getPmAssetsControlValueLists().get(i).getControlValue());
                if (response.getPmAssetCheckStandardValue().equals(response.getPmAssetsControlValueLists().get(i).getControlValue())) {
                    rb[i].setChecked(true);
                }
                holder.rg.addView(rb[i]);
                if (comShare.getdesignation().equals("Technician")) {

                    if (assetCheckListStatus == 1) {
                        if (!tcstatus.equals("Open") && !tcstatus.equals("Reassign") && !tcstatus.equals("Overdue")) {

                            rb[i].setClickable(false);
                            rb[i].setEnabled(false);
                        }
                    }
                } else {

                    rb[i].setClickable(false);
                    rb[i].setEnabled(false);
                }

            }


            holder.l3.addView(holder.rg);
            holder.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {


                    View radioButton = holder.rg.findViewById(checkedId);

                    int radioId = holder.rg.indexOfChild(radioButton);
                    RadioButton btn = (RadioButton) holder.rg.getChildAt(radioId);
                    String selection = (String) btn.getText();
                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );

                    com.datalist.get(position).setPMAssetCheckStandardValue(selection);
                    click.OnItemClick();


                }
            });
            click.OnItemClick();


            holder.question.setText("" + response.getPmAssetCheckLabel());
            holder.textusedmw.setVisibility(View.GONE);

        } else if (response.getControlType().equals("DropDown")) {

            holder.l2.setVisibility(View.VISIBLE);
            holder.datetext.setVisibility(View.GONE);
            holder.l4.setVisibility(View.GONE);

            final String[] items = new String[response.getPmAssetsControlValueLists().size()];


            Spinner spinner = new Spinner(context);
            spinner.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            spinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, items));

            holder.l2.addView(spinner);

            for (int i = 0; i < response.getPmAssetsControlValueLists().size(); i++) {

                items[i] = response.getPmAssetsControlValueLists().get(i).getControlValue();
            }
            if (!response.getPmAssetCheckStandardValue().equals("")) {

                spinner.setSelection(Arrays.asList(items).indexOf(response.getPmAssetCheckStandardValue()));
            } else {


            }
            if (comShare.getdesignation().equals("Technician")) {

                if (assetCheckListStatus == 1) {
                    if (!tcstatus.equals("Open") && !tcstatus.equals("Reassign") && !tcstatus.equals("Overdue")) {

                        spinner.setEnabled(false);
                        spinner.setClickable(false);
                    }
                }
            } else {
                spinner.setEnabled(false);
                spinner.setClickable(false);
            }

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );

                    com.datalist.get(position).setPMAssetCheckStandardValue(items[i]);
                    click.OnItemClick();


                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });

            click.OnItemClick();

            holder.question.setText("" + response.getPmAssetCheckLabel());
            holder.textusedmw.setVisibility(View.GONE);

        } else if (response.getControlType().equals("Date")) {
            holder.question.setText("" + response.getPmAssetCheckLabel());
            holder.textusedmw.setVisibility(View.GONE);
            holder.l4.setVisibility(View.GONE);

            holder.datetext.setVisibility(View.VISIBLE);
            holder.l1.setVisibility(View.VISIBLE);
            context.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
            );

            holder.datetext.setText("" + response.getPmAssetCheckStandardValue());

            holder.datetext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );

                    final Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog dpd = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            monthOfYear++;
                            int day = dayOfMonth;
                            int month = monthOfYear;
                            String date_convertion = year + "-" + month + "-" + day;
                            holder.datetext.setText(localDate(date_convertion));

                            com.datalist.get(position).setPMAssetCheckStandardValue(localDate(date_convertion));
                            click.OnItemClick();
                        }
                    }, year, month, day);
                    //dpd.getDatePicker().setMinDate(c.getTimeInMillis());
                    dpd.show();


                }
            });
            click.OnItemClick();
            if (comShare.getdesignation().equals("Technician")) {

                if (assetCheckListStatus == 1) {
                    if (!tcstatus.equals("Open") && !tcstatus.equals("Reassign") && !tcstatus.equals("Overdue")) {
                        holder.datetext.setClickable(false);
                        holder.datetext.setFocusable(false);
                        holder.datetext.setEnabled(false);
                    }
                }
            } else {
                holder.datetext.setClickable(false);
                holder.datetext.setFocusable(false);
                holder.datetext.setEnabled(false);
            }

        } else if (response.getControlType().equals("Image")) {


            holder.question.setText("" + response.getPmAssetCheckLabel());

            holder.l1.setVisibility(View.GONE);
            holder.l4.setVisibility(View.VISIBLE);
            holder.textusedmw.setVisibility(View.GONE);
            holder.datetext.setVisibility(View.GONE);

            if (response.getPmAssetCheckStandardValue().equals("")) {
                holder.image.setImageResource(R.mipmap.rectimg);

            } else {

                holder.image.setImageBitmap(ConstantsHelper.getBitmap(context, response.getPmAssetCheckStandardValue()));
            }



            if (!com.datalist.get(position).getPMAssetCheckStandardValue().equals("")) {
                com.datalist.remove(response.getPmAssetCheckStandardValue());
                com.datalist.get(position).setPMAssetCheckStandardValue(localDate(response.getPmAssetCheckStandardValue()));

            } else {
                com.datalist.get(position).setPMAssetCheckStandardValue(response.getPmAssetCheckStandardValue());
            }




            click.OnItemClick();

            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                    );

                    LayoutInflater layoutInflater = LayoutInflater.from(context);
                    View promptView = layoutInflater.inflate(R.layout.dialogbox_album, null);

                    final AlertDialog alertDialog = new AlertDialog.Builder(context).create();


                    Button camera = (Button) promptView.findViewById(R.id.button_camera);
                    Button album = (Button) promptView.findViewById(R.id.button_album);


                    album.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            context.getWindow().setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                            );

                            ((DetailActivity) context).opengallery(position, com.datalist.get(position).getPMAssetCheckStandardValue());

                            alertDialog.dismiss();
                            click.OnItemClick();
                        }
                    });

                    alertDialog.setView(promptView);
                    alertDialog.show();
                    camera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (context.getPackageManager().hasSystemFeature(
                                    PackageManager.FEATURE_CAMERA)) {


                                context.getWindow().setSoftInputMode(
                                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                                );

                                ((DetailActivity) context).captureImage(position, com.datalist.get(position).getPMAssetCheckStandardValue());

                            }
                            click.OnItemClick();
                            alertDialog.dismiss();
                        }
                    });


                }
            });
            click.OnItemClick();

            if (response.getPmAssetCheckStandardValue() != "") {

                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        context.getWindow().setSoftInputMode(
                                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                        );

                        final Dialog settingsDialog = new Dialog(context);
                        settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        settingsDialog.setContentView(context.getLayoutInflater().inflate(R.layout.imageview, null));

                        ImageView viewimg = (ImageView) settingsDialog.findViewById(R.id.viewimg);
                        ImageView close = (ImageView) settingsDialog.findViewById(R.id.clolse);
                        viewimg.setImageBitmap(ConstantsHelper.getBitmap(context, response.getPmAssetCheckStandardValue()));
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


            if (comShare.getdesignation().equals("Technician")) {

                if (assetCheckListStatus == 1) {
                    if (!tcstatus.equals("Open") && !tcstatus.equals("Reassign") && !tcstatus.equals("Overdue")) {
                        holder.btn.setClickable(false);
                        holder.btn.setFocusable(false);
                        holder.btn.setBackgroundResource(R.drawable.buttonborderdisable);

                        holder.btn.setEnabled(false);
                    }
                }
            } else {
                holder.btn.setClickable(false);
                holder.btn.setFocusable(false);
                holder.btn.setBackgroundResource(R.drawable.buttonborderdisable);
                holder.btn.setEnabled(false);
            }
        } else {
            holder.question.setVisibility(View.GONE);
            holder.textusedmw.setVisibility(View.GONE);
            holder.l1.setVisibility(View.GONE);
            holder.l2.setVisibility(View.GONE);
            holder.l4.setVisibility(View.GONE);
        }

        click.OnItemClick();
    }


    @Override
    public int getItemCount() {
        return getstaffMembersLists.size();
    }

    @Override
    public void onClick(View v) {
        final MyViewHolder viewHolder = (MyViewHolder) v.getTag();


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView question;
        LinearLayout cardview, l1, l2, l3, l5;
        RelativeLayout l4;
        EditText textusedmw, datetext;
        RelativeLayout card;
        ImageView siteimage;
        RadioGroup rg;
        RadioButton radioBtn;
        ImageView image, subtract, add;
        TextView btn, steppercount;

        public MyViewHolder(View view) {
            super(view);

            question = (TextView) view.findViewById(R.id.question);
            textusedmw = (EditText) view.findViewById(R.id.textusedmw);
            datetext = (EditText) view.findViewById(R.id.datetext);
            cardview = (LinearLayout) view.findViewById(R.id.card_view);
            l1 = (LinearLayout) view.findViewById(R.id.l1);
            l2 = (LinearLayout) view.findViewById(R.id.l2);
            l3 = (LinearLayout) view.findViewById(R.id.l3);
            l5 = (LinearLayout) view.findViewById(R.id.l5);
            l4 = (RelativeLayout) view.findViewById(R.id.l4);
            rg = new RadioGroup(context); //create the RadioGroup
            int checkedRadioButtonId = rg.getCheckedRadioButtonId();
            radioBtn = (RadioButton) view.findViewById(checkedRadioButtonId);
            btn = (TextView) view.findViewById(R.id.btn);
            image = (ImageView) view.findViewById(R.id.img);
            subtract = (ImageView) view.findViewById(R.id.subtract);
            add = (ImageView) view.findViewById(R.id.add);
            steppercount = (TextView) view.findViewById(R.id.steppercount);

        }
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


}