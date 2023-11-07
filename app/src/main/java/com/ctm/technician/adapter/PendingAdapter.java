package com.ctm.technician.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ctm.technician.ChecklistAssetsActivity;
import com.ctm.technician.DetailActivity;
import com.ctm.technician.PlannedeventchecklistActivity;
import com.ctm.technician.R;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Tickets.PMTicketData;

import java.util.ArrayList;
import java.util.List;

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.MyViewHolder> {
    Context context;
    private CommonSharePrefrences comShare;
    private List<PMTicketData> jobsList;
    private ArrayList<PMTicketData> filterList;


    public PendingAdapter(Context activity, List<PMTicketData> listOfOrdersWithServices, CommonSharePrefrences comShare) {
        this.context = activity;
        this.comShare = comShare;
        jobsList = listOfOrdersWithServices;
        this.filterList = new ArrayList<PMTicketData>();
        this.filterList.addAll(jobsList);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_today_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        holder.input_id.setText("# " + String.valueOf(jobsList.get(position).getPmTicketId()));
        holder.description.setText(String.valueOf(jobsList.get(position).getTicketDescription()));
        holder.title.setText(String.valueOf(jobsList.get(position).getTicketHeading()));

        if (jobsList.get(position).getRecCreateDate() == null) {
            holder.recCreateDate.setText("Not defined");
        } else {
            holder.recCreateDate.setText(String.valueOf(jobsList.get(position).getRecCreateDate()));

        }
        if (jobsList.get(position).getTicketType().equals("PM")) {
            holder.tickettypelinear.setVisibility(View.VISIBLE);
            holder.tickettypelinear2.setVisibility(View.GONE);

        } else {
            holder.tickettypelinear.setVisibility(View.GONE);

            holder.tickettypelinear2.setVisibility(View.VISIBLE);
            if (jobsList.get(position).getPriority().equals("High")) {
                if (jobsList.get(position).getPmTicketStatusName().equals("Reassign")) {

                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.zoomin);
                    holder.tickettypelinear2.startAnimation(animation);
                    holder.tickettypelinear2.setBackgroundResource(R.drawable.buttonborderred);
                }
                if (jobsList.get(position).getPmTicketStatusName().equals("Open")) {

                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.zoomin);
                    holder.tickettypelinear2.startAnimation(animation);
                    holder.tickettypelinear2.setBackgroundResource(R.drawable.buttonborderred);

                }

            } else {
                holder.tickettypelinear2.setAnimation(null);
                holder.tickettypelinear2.setBackgroundResource(R.drawable.buttonbordergray);
            }

        }
        holder.input_sitename.setText("#" + String.valueOf(jobsList.get(position).getSiteUniqueId() + "," + String.valueOf(jobsList.get(position).getSiteName())));
        holder.ticketstatus.setText("\u25CF " + String.valueOf(jobsList.get(position).getPmTicketStatusName()));
        if (jobsList.get(position).getPmTicketStatusName().equals("Reassign")) {

            holder.ticketstatus.setText("\u25CF " + "Re-Assign");

        }

    }

    @Override
    public int getItemCount() {
        return jobsList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tech_name, tech_contact, input_id, labelsitename, description, title, input_sitename, input_site_id, recCreateDate, ticketstatus, labeiid;
        LinearLayout card_view, tickettypelinear, tickettypelinear2, techlayout;
        ImageView arrow, arrowsup;


        public MyViewHolder(View view) {
            super(view);
            input_id = (TextView) view.findViewById(R.id.input_id);
            description = (TextView) view.findViewById(R.id.description);
            title = (TextView) view.findViewById(R.id.title);
            recCreateDate = (TextView) view.findViewById(R.id.recCreateDate);
            input_site_id = (TextView) view.findViewById(R.id.input_site_id);
            input_sitename = (TextView) view.findViewById(R.id.input_sitename);
            ticketstatus = (TextView) view.findViewById(R.id.ticketstatus);
            labeiid = (TextView) view.findViewById(R.id.labeiid);
            tech_name = (TextView) view.findViewById(R.id.techname);
            tech_contact = (TextView) view.findViewById(R.id.techcontact);
            labelsitename = (TextView) view.findViewById(R.id.labelsitename);
            arrow = (ImageView) view.findViewById(R.id.arrow);
            arrowsup = (ImageView) view.findViewById(R.id.arrowsup);

            card_view = (LinearLayout) view.findViewById(R.id.card_view);
            tickettypelinear = (LinearLayout) view.findViewById(R.id.tickettypelinear);
            tickettypelinear2 = (LinearLayout) view.findViewById(R.id.tickettypelinear2);
            techlayout = (LinearLayout) view.findViewById(R.id.techlayout);
            if (comShare.getdesignation().equals("Technician")) {
                techlayout.setVisibility(View.GONE);
                arrowsup.setVisibility(View.GONE);
                arrow.setVisibility(View.VISIBLE);
            } else {
                input_id.setTextColor(context.getResources().getColor(R.color.textcolor));
                description.setTextColor(context.getResources().getColor(R.color.textcolor));
                title.setTextColor(context.getResources().getColor(R.color.textcolor));
                input_site_id.setTextColor(context.getResources().getColor(R.color.textcolor));
                recCreateDate.setTextColor(context.getResources().getColor(R.color.textcolor));
                input_sitename.setTextColor(context.getResources().getColor(R.color.textcolor));
                // ticketstatus.setTextColor(context.getResources().getColor(R.color.textcolor));
                labeiid.setTextColor(context.getResources().getColor(R.color.subtextcolor));
                labelsitename.setTextColor(context.getResources().getColor(R.color.subtextcolor));

                arrowsup.setVisibility(View.VISIBLE);
                arrow.setVisibility(View.GONE);
                card_view.setBackground(context.getResources().getDrawable(R.drawable.supgradiantheader));
                techlayout.setVisibility(View.VISIBLE);

            }

            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (jobsList.get(getLayoutPosition()).getTicketType().equals("PM")) {

                        if (comShare.getdesignation().equals("Technician")) {
                            Intent intent = new Intent(context, ChecklistAssetsActivity.class);
                            intent.putExtra("ticket_id", String.valueOf(jobsList.get(getLayoutPosition()).getPmTicketId()));
                            intent.putExtra("type_id", String.valueOf(jobsList.get(getLayoutPosition()).getPmTypeId()));
                            intent.putExtra("site_id", String.valueOf(jobsList.get(getLayoutPosition()).getSiteId()));
                            intent.putExtra("site_name", String.valueOf(jobsList.get(getLayoutPosition()).getSiteName()));
                            intent.putExtra("site_uniqueid", String.valueOf(jobsList.get(getLayoutPosition()).getSiteUniqueId()));

                            intent.putExtra("created_date", String.valueOf(jobsList.get(getLayoutPosition()).getRecCreateDate()));
                            intent.putExtra("description", String.valueOf(jobsList.get(getLayoutPosition()).getTicketDescription()));
                            intent.putExtra("heading", String.valueOf(jobsList.get(getLayoutPosition()).getTicketHeading()));
                            intent.putExtra("lastpmdate", String.valueOf(jobsList.get(getLayoutPosition()).getDateTimeOfSitePM()));
                            intent.putExtra("pmplandate", String.valueOf(jobsList.get(getLayoutPosition()).getDateAsPerPMPlan()));
                            intent.putExtra("status", String.valueOf(jobsList.get(getLayoutPosition()).getPmTicketStatusName()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                    } else {

                        Intent intent = new Intent(context, PlannedeventchecklistActivity.class);
                        intent.putExtra("ticket_id", String.valueOf(jobsList.get(getLayoutPosition()).getPmTicketId()));
                        intent.putExtra("type_id", String.valueOf(jobsList.get(getLayoutPosition()).getPmTypeId()));
                        intent.putExtra("site_id", String.valueOf(jobsList.get(getLayoutPosition()).getSiteId()));
                        intent.putExtra("site_name", String.valueOf(jobsList.get(getLayoutPosition()).getSiteName()));
                        intent.putExtra("created_date", String.valueOf(jobsList.get(getLayoutPosition()).getRecCreateDate()));
                        intent.putExtra("description", String.valueOf(jobsList.get(getLayoutPosition()).getTicketDescription()));
                        intent.putExtra("heading", String.valueOf(jobsList.get(getLayoutPosition()).getTicketHeading()));
                        intent.putExtra("lastpmdate", String.valueOf(jobsList.get(getLayoutPosition()).getDateTimeOfSitePM()));
                        intent.putExtra("site_uniqueid", String.valueOf(jobsList.get(getLayoutPosition()).getSiteUniqueId()));

                        intent.putExtra("pmplandate", String.valueOf(jobsList.get(getLayoutPosition()).getDateAsPerPMPlan()));
                        intent.putExtra("status", String.valueOf(jobsList.get(getLayoutPosition()).getPmTicketStatusName()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);


                    }


                }
            });


        }
    }
    public void filter(String charText) {
        charText = charText.toUpperCase();
        jobsList.clear();
        if (charText.length() == 0) {
            jobsList.addAll(filterList);
        } else {
            for (PMTicketData retailer : filterList) {
                if (retailer.getSiteUniqueId().contains(charText) || retailer.getSiteName().toUpperCase().contains(charText)) {
                    jobsList.add(retailer);
                }
            }
        }
        notifyDataSetChanged();
    }
}
