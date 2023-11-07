package com.ctm.technician.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ctm.technician.AssetDetailsActivity;
import com.ctm.technician.R;
import com.ctm.technician.SitesActivity;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.models.Sites.Assetdetailslist;

import java.util.List;


public class Assetdetailslistadapter extends RecyclerView.Adapter<Assetdetailslistadapter.MyViewHolder> {

    private List<Assetdetailslist> getstaffMembersLists;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private AssetDetailsActivity context;

    public Assetdetailslistadapter(List<Assetdetailslist> staffMembersLists, CommonSharePrefrences comShare, AssetDetailsActivity context) {
        this.getstaffMembersLists = staffMembersLists;
        this.comShare = comShare;
        this.context = context;
    }

    @Override
    public Assetdetailslistadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_asset_detailslist, parent, false);
        return new Assetdetailslistadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Assetdetailslistadapter.MyViewHolder holder, int position) {
        Assetdetailslist response = getstaffMembersLists.get(position);

        holder.listlabel.setText("" + response.getColumnName());
        holder.listname.setText("" + response.getColumnValue());

    }

    @Override
    public int getItemCount() {
        return getstaffMembersLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView listlabel, listname;


        public MyViewHolder(View view) {
            super(view);

            listlabel = (TextView) view.findViewById(R.id.listlabel);
            listname = (TextView) view.findViewById(R.id.listname);


        }


    }


}