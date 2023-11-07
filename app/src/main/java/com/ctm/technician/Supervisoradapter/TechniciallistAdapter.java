package com.ctm.technician.Supervisoradapter;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ctm.technician.R;
import com.ctm.technician.Supervisoemodels.Tecnicianmodel.Technicianlistmodel;
import com.ctm.technician.SupervisorActivity.TechnicianActivity;
import com.ctm.technician.SupervisorActivity.TechniciandetailsActivity;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import java.util.ArrayList;
import java.util.List;

public class TechniciallistAdapter extends RecyclerView.Adapter<TechniciallistAdapter.MyViewHolder> {

    private List<Technicianlistmodel> getstaffMembersLists;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private TechnicianActivity context;
    private ArrayList<Technicianlistmodel> filterList;

    public TechniciallistAdapter(List<Technicianlistmodel> staffMembersLists, CommonSharePrefrences comShare, TechnicianActivity context) {
        this.getstaffMembersLists = staffMembersLists;
        this.comShare = comShare;
        this.context = context;
        this.filterList = new ArrayList<Technicianlistmodel>();
        this.filterList.addAll(getstaffMembersLists);
    }

    @Override
    public TechniciallistAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supervisortechnicians_list, parent, false);
        return new TechniciallistAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TechniciallistAdapter.MyViewHolder holder, int position) {
        Technicianlistmodel response = getstaffMembersLists.get(position);
        holder.name.setText(response.getUserName());
        holder.mobile.setText("" + response.getContact());
        holder.sites.setText("" + response.getSiteCount());

    }

    @Override
    public int getItemCount() {
        return getstaffMembersLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, mobile, sites, viewdetail;


        public MyViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            mobile = (TextView) view.findViewById(R.id.mobile);
            sites = (TextView) view.findViewById(R.id.sites);
            viewdetail = (TextView) view.findViewById(R.id.viewdetails);

            viewdetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TechniciandetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("technician_name", getstaffMembersLists.get(getLayoutPosition()).getUserName());
                    intent.putExtra("number", getstaffMembersLists.get(getLayoutPosition()).getContact());
                    intent.putExtra("tickets_count", "6");
                    intent.putExtra("technician_id", String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getTechnicianId()));
                    comShare.settechnicianid(String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getTechnicianId()));
                    context.startActivity(intent);


                }
            });


        }


    }

    public void filter(String charText) {
        charText = charText.toUpperCase();
        getstaffMembersLists.clear();
        if (charText.length() == 0) {
            getstaffMembersLists.addAll(filterList);
        } else {
            for (Technicianlistmodel retailer : filterList) {
                if (retailer.getUserName().startsWith(charText) || retailer.getUserName().toUpperCase().contains(charText)) {
                    getstaffMembersLists.add(retailer);
                }
            }
        }
        notifyDataSetChanged();
    }



}