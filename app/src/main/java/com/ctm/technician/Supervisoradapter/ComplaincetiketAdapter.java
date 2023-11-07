package com.ctm.technician.Supervisoradapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctm.technician.R;
import com.ctm.technician.Supervisoemodels.Tickets.SupPMTechnicianData;
import com.ctm.technician.SupervisorActivity.SupChecklistAssetsActivity;
import com.ctm.technician.SupervisorActivity.SupPlannedeventchecklistAssetsActivity;
import com.ctm.technician.constants.CommonSharePrefrences;

import java.util.List;

public class ComplaincetiketAdapter extends RecyclerView.Adapter<ComplaincetiketAdapter.MyViewHolder> {
    Context context;
    private CommonSharePrefrences comShare;
    private List<SupPMTechnicianData> jobsList;


    public ComplaincetiketAdapter(Context activity, List<SupPMTechnicianData> listOfOrdersWithServices) {
        this.context = activity;
        this.comShare = comShare;
        jobsList = listOfOrdersWithServices;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supervisorticket_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.ticketid.setText("#" + String.valueOf(jobsList.get(position).getPMTicketData().getPmTicketId()));
        holder.description.setText(String.valueOf(jobsList.get(position).getPMTicketData().getTicketDescription()));

        holder.heading.setText(String.valueOf(jobsList.get(position).getPMTicketData().getTicketHeading()));

        if (jobsList.get(position).getPMTicketData().getRecCreateDate() == null) {
            holder.crateddate.setText("Not defined");
        } else {
            holder.crateddate.setText(String.valueOf(jobsList.get(position).getPMTicketData().getRecCreateDate()));

        }
        if (jobsList.get(position).getPMTicketData().getTicketType().equals("PM")) {
            holder.tickettypelinear.setVisibility(View.VISIBLE);
            holder.tickettypelinear2.setVisibility(View.GONE);
        } else {
            holder.tickettypelinear.setVisibility(View.GONE);

            holder.tickettypelinear2.setVisibility(View.VISIBLE);


        }



        holder.siteid.setText(String.valueOf(jobsList.get(position).getPMTicketData().getSiteId()));
        holder.sitename.setText(String.valueOf(jobsList.get(position).getPMTicketData().getSiteName()));
        holder.status.setText("\u25CF " + String.valueOf(jobsList.get(position).getPMTicketData().getPmTicketStatusName()));
        holder.techname.setText(String.valueOf(jobsList.get(position).getUserName()));
        holder.techcontact.setText(String.valueOf(jobsList.get(position).getContact()));


        if (jobsList.get(position).getPMTicketData().getPmTicketStatusName().equals("Open")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.opentextcolor));
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.opentextcolor));

        }
        if (jobsList.get(position).getPMTicketData().getPmTicketStatusName().equals("Reassign")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.opentextcolor));
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.opentextcolor));
            holder.status.setText("\u25CF " + "Re-Assign");

        }
        if (jobsList.get(position).getPMTicketData().getPmTicketStatusName().equals("Pending")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.pendingtextcolor));
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.pendingtextcolor));

        }
        if (jobsList.get(position).getPMTicketData().getPmTicketStatusName().equals("Closed")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.closedtextcolor));
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.closedtextcolor));

        }
        if (jobsList.get(position).getPMTicketData().getPmTicketStatusName().equals("Overdue")) {
            holder.status.setTextColor(context.getResources().getColor(R.color.overduetextcolor));
            holder.view1.setBackgroundColor(context.getResources().getColor(R.color.overduetextcolor));

        }
    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tickettype, title, ticketid, heading, description, status, crateddate, techcontact, sitename, siteid, techname;
        LinearLayout card_view, tickettypelinear, tickettypelinear2;
        View view1;

        public MyViewHolder(View view) {
            super(view);
            ticketid = (TextView) view.findViewById(R.id.input_id);
            heading = (TextView) view.findViewById(R.id.heading);
            status = (TextView) view.findViewById(R.id.status);
            crateddate = (TextView) view.findViewById(R.id.recCreateDate);
            description = (TextView) view.findViewById(R.id.description);
            siteid = (TextView) view.findViewById(R.id.siteid);
            techname = (TextView) view.findViewById(R.id.techname);
            tickettype = (TextView) view.findViewById(R.id.tickettype);
            techcontact = (TextView) view.findViewById(R.id.techcontact);
            tickettypelinear = (LinearLayout) view.findViewById(R.id.tickettypelinear);
            tickettypelinear2 = (LinearLayout) view.findViewById(R.id.tickettypelinear2);
            view1 = (View) view.findViewById(R.id.view1);

            sitename = (TextView) view.findViewById(R.id.sitename);

            card_view = (LinearLayout) view.findViewById(R.id.card_view);


            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {





                    if (jobsList.get(getLayoutPosition()).getPMTicketData().getTicketType().equals("PM")) {
                        Intent intent = new Intent(context, SupChecklistAssetsActivity.class);
                        intent.putExtra("ticket_id", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getPmTicketId()));
                        intent.putExtra("type_id", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getPmTypeId()));
                        intent.putExtra("site_id", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getSiteId()));
                        intent.putExtra("site_name", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getSiteName()));
                        intent.putExtra("site_uniqueid", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getSiteUniqueId()));
                        intent.putExtra("created_date", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getRecCreateDate()));
                        intent.putExtra("description", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getTicketDescription()));
                        intent.putExtra("heading", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getTicketHeading()));
                        intent.putExtra("lastpmdate", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getDateTimeOfSitePM()));
                        intent.putExtra("pmplandate", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getDateAsPerPMPlan()));
                        intent.putExtra("status", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getPmTicketStatusName()));
                        intent.putExtra("tech_name", String.valueOf(jobsList.get(getLayoutPosition()).getUserName()));
                        intent.putExtra("tech_contact", String.valueOf(jobsList.get(getLayoutPosition()).getContact()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {

                        Intent intent = new Intent(context, SupPlannedeventchecklistAssetsActivity.class);
                        intent.putExtra("ticket_id", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getPmTicketId()));
                        intent.putExtra("type_id", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getPmTypeId()));
                        intent.putExtra("site_id", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getSiteId()));
                        intent.putExtra("site_name", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getSiteName()));
                        intent.putExtra("site_uniqueid", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getSiteUniqueId()));

                        intent.putExtra("created_date", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getRecCreateDate()));
                        intent.putExtra("description", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getTicketDescription()));
                        intent.putExtra("heading", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getTicketHeading()));
                        intent.putExtra("lastpmdate", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getDateTimeOfSitePM()));
                        intent.putExtra("pmplandate", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getDateAsPerPMPlan()));
                        intent.putExtra("status", String.valueOf(jobsList.get(getLayoutPosition()).getPMTicketData().getPmTicketStatusName()));
                        intent.putExtra("tech_name", String.valueOf(jobsList.get(getLayoutPosition()).getUserName()));
                        intent.putExtra("tech_contact", String.valueOf(jobsList.get(getLayoutPosition()).getContact()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }









                }
            });


        }
    }
}
