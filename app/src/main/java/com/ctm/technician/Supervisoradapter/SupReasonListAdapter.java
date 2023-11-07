package com.ctm.technician.Supervisoradapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Point;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ctm.technician.R;
import com.ctm.technician.SupervisorActivity.SupChecklistAssetsActivity;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.models.Checklist.TicketHistoryList;
import com.ctm.technician.utils.onItemClick;

import java.util.List;

public class SupReasonListAdapter extends RecyclerView.Adapter<SupReasonListAdapter.MyViewHolder> {

    private List<TicketHistoryList> getstaffMembersLists;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private SupChecklistAssetsActivity context;
    int ticketid, typeid, siteid;
    String sitename, tcstatus;
    private onItemClick click;
    TicketHistoryList response;

    public SupReasonListAdapter(List<TicketHistoryList> staffMembersLists, onItemClick click, String tcstatus, int siteid, String sitename, CommonSharePrefrences comShare, int ticketid, int typeid, SupChecklistAssetsActivity context) {
        this.getstaffMembersLists = staffMembersLists;
        this.comShare = comShare;
        this.context = context;
        this.ticketid = ticketid;
        this.typeid = typeid;
        this.siteid = siteid;
        this.tcstatus = tcstatus;
        this.click = click;

        this.sitename = sitename;
    }

    @Override
    public SupReasonListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_servicessup, parent, false);
        return new SupReasonListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        response = getstaffMembersLists.get(position);
        holder.serviceName.setText("" + response.getStatus());

        if ((response.getStatus().equals("Submitted"))) {
            holder.v1.setBackgroundColor(context.getResources().getColor(R.color.blue));
            holder.serviceName.setText(response.getStatus() + " by " + "" + response.getAssignedTo().trim() + "\nat " + response.getRecCreateDate().trim());
        } else if (response.getStatus().equals("Reassigned")) {
            holder.v1.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.visibility.setVisibility(View.VISIBLE);

            holder.serviceName.setText("Re-Assigned" + " by " + "" + response.getAssignedTo().trim() + "\nat " + response.getRecCreateDate().trim());


        } else {
            holder.v1.setBackgroundColor(context.getResources().getColor(R.color.green));

            holder.serviceName.setText(response.getStatus() + " by " + "" + response.getAssignedTo().trim() + "\nat " + response.getRecCreateDate().trim());

        }


    }

    public Dialog showAlertDialogOK(String message) {
        final Dialog dialog = new Dialog(context);
        try {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom_dialogsup);
            dialog.getWindow().setLayout(getWidth() - 40, LinearLayout.LayoutParams.WRAP_CONTENT);
            //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            TextView txtCustomAlert_Message = (TextView) dialog.findViewById(R.id.txt_CustomAlert_Message);
            TextView txtCustomAlert_OK = (TextView) dialog.findViewById(R.id.txt_CustomAlert_OK);
            RelativeLayout r1 = (RelativeLayout) dialog.findViewById(R.id.r1);

            txtCustomAlert_Message.setText(message);

            txtCustomAlert_OK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return dialog;
    }

    public int getWidth() {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_width = size.x;
        return screen_width;
    }

    @SuppressLint("NewApi")
    public int getHeight() {
        Display display = ((Activity) context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screen_height = size.y;
        return screen_height;
    }

    @Override
    public int getItemCount() {
        return getstaffMembersLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView serviceName;
        View v1;
        RelativeLayout r1;
        ImageView visibility;

        public MyViewHolder(View view) {
            super(view);

            serviceName = (TextView) view.findViewById(R.id.checklistdetail);
            v1 = (View) view.findViewById(R.id.view1);
            visibility = (ImageView) view.findViewById(R.id.visibility);
            visibility.setImageResource(R.mipmap.infosup);

            visibility.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertDialogOK(getstaffMembersLists.get(getLayoutPosition()).getComments());


                }
            });


        }


    }


}