package com.ctm.technician.Supervisoradapter;

import android.content.Intent;
import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.ctm.technician.DetailActivity;
import com.ctm.technician.R;
import com.ctm.technician.SupervisorActivity.SupChecklistAssetsActivity;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Checklist.PMAssetdata;
import com.ctm.technician.utils.ConstantsHelper;
import com.ctm.technician.utils.onItemClick;
import java.util.List;

public class SupChecklistAssetsListAdapter extends RecyclerView.Adapter<SupChecklistAssetsListAdapter.MyViewHolder> {

    private List<PMAssetdata> getstaffMembersLists;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private SupChecklistAssetsActivity context;
    int ticketid, typeid, siteid;
    String sitename, tcstatus,techname,techcontact,site_uniqueid;
    private onItemClick click;

    public SupChecklistAssetsListAdapter(List<PMAssetdata> staffMembersLists, onItemClick click,String techname,String techcontact, String tcstatus, int siteid,String sitename,String site_uniqueid, CommonSharePrefrences comShare, int ticketid, int typeid, SupChecklistAssetsActivity context) {
        this.getstaffMembersLists = staffMembersLists;
        this.comShare = comShare;
        this.context = context;
        this.ticketid = ticketid;
        this.typeid = typeid;
        this.siteid = siteid;
        this.tcstatus = tcstatus;
        this.techname = techname;
        this.site_uniqueid = site_uniqueid;
        this.techcontact = techcontact;
        this.click = click;

        this.sitename = sitename;
    }

    @Override
    public SupChecklistAssetsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assert_checllist, parent, false);
        return new SupChecklistAssetsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SupChecklistAssetsListAdapter.MyViewHolder holder, int position) {
        PMAssetdata response = getstaffMembersLists.get(position);
        holder.sitetime.setText("" + response.getSiteAssetId());
        holder.siteassetname.setText("" + response.getSiteAssetName());

        try {

            holder.assetimg.setImageBitmap(ConstantsHelper.getBitmap(context, response.getPhoto()));

        } catch (NullPointerException e) {

        }
        for (int i = 0; i < getstaffMembersLists.size(); i++) {
            if (response.getAssetCheckListStatus() == 1) {
                click.OnItemClick();

            }
        }

        if (response.getAssetCheckListStatus() == 1) {
            holder.checked.setVisibility(View.VISIBLE);

            holder.cardview.setBackgroundResource(R.drawable.suproundedgradiantline);
            holder.siteassetname.setTextColor(Color.WHITE);


        }

    }

    @Override
    public int getItemCount() {
        return getstaffMembersLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView assetname, sitetime, siteassetname;
        LinearLayout cardview;
        ImageView assetimg,checked;


        public MyViewHolder(View view) {
            super(view);

            assetname = (TextView) view.findViewById(R.id.assetname);
            sitetime = (TextView) view.findViewById(R.id.input_time);
            cardview = (LinearLayout) view.findViewById(R.id.card_view);
            siteassetname = (TextView) view.findViewById(R.id.siteassetname);
            assetimg = (ImageView) view.findViewById(R.id.img);
            checked = (ImageView) view.findViewById(R.id.checked);

            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(context, DetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        Log.e("datatataaa", ticketid + "" + typeid);
                        intent.putExtra("ticket_id", "" + ticketid);
                        intent.putExtra("type_id", "" + typeid);
                        intent.putExtra("assetCheckListStatus", String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getAssetCheckListStatus()));
                        intent.putExtra("tcstatus", tcstatus);
                        intent.putExtra("distance", getstaffMembersLists.get(getLayoutPosition()).getDistance());

                        intent.putExtra("siteasset_id", String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getSiteAssetId()));
                        intent.putExtra("site_id", "" + siteid);
                        intent.putExtra("site_name", sitename);
                        intent.putExtra("site_uniqueid", site_uniqueid);
                        intent.putExtra("asset_name", String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getSiteAssetName()));
                        intent.putExtra("techcontact", techcontact);
                        intent.putExtra("techname", techname);
                        context.startActivity(intent);

                }

            });
        }


    }


}