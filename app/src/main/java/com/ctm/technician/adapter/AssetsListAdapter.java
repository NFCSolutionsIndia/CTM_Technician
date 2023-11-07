package com.ctm.technician.adapter;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ctm.technician.AssetDetailsActivity;
import com.ctm.technician.R;
import com.ctm.technician.SitesDetailsActivity;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.constants.Networking;
import com.ctm.technician.models.Sites.sitesAssetdata;
import com.ctm.technician.utils.ConstantsHelper;

import java.util.List;


public class AssetsListAdapter extends RecyclerView.Adapter<AssetsListAdapter.MyViewHolder> {

    private List<sitesAssetdata> getstaffMembersLists;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private SitesDetailsActivity context;
    String clustername;

    public AssetsListAdapter(List<sitesAssetdata> staffMembersLists, String cluster, CommonSharePrefrences comShare, SitesDetailsActivity context) {
        this.getstaffMembersLists = staffMembersLists;
        this.comShare = comShare;
        this.context = context;
        this.clustername = cluster;
    }

    @Override
    public AssetsListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assert_list, parent, false);
        return new AssetsListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AssetsListAdapter.MyViewHolder holder, int position) {
        sitesAssetdata response = getstaffMembersLists.get(position);
        holder.assetname.setText(response.getSiteAssetName());
        holder.sitetime.setText("" + response.getSiteAssetId());
        holder.siteassetname.setText("" + response.getSiteAssetName());

        try {

            holder.assetimage.setImageBitmap(ConstantsHelper.getBitmap(context, "" + response.getAssestImage().getPhoto()));

        } catch (NullPointerException e) {

        }
    }

    @Override
    public int getItemCount() {
        return getstaffMembersLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView assetname, sitetime, siteassetname;
        LinearLayout cardview;
        ImageView assetimage;

        public MyViewHolder(View view) {
            super(view);

            assetname = (TextView) view.findViewById(R.id.assetname);
            sitetime = (TextView) view.findViewById(R.id.input_time);
            cardview = (LinearLayout) view.findViewById(R.id.card_view);
            siteassetname = (TextView) view.findViewById(R.id.siteassetname);
            assetimage = (ImageView) view.findViewById(R.id.assetimage);

            cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, AssetDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("siteasset_id", String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getSiteAssetId()));
                    intent.putExtra("site_id", String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getSiteId()));
                    intent.putExtra("asset_name", String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getSiteAssetName()));
                    intent.putExtra("cluster_name", clustername);
                    context.startActivity(intent);


                }

            });
        }


    }


}