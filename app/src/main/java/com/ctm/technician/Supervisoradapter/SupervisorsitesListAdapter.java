package com.ctm.technician.Supervisoradapter;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ctm.technician.R;
import com.ctm.technician.SitesDetailsActivity;
import com.ctm.technician.SupervisorActivity.TechniciandetailsActivity;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;
import com.ctm.technician.models.Sites.sitesListData;
import com.ctm.technician.utils.ConstantsHelper;
import java.util.ArrayList;
import java.util.List;

public class SupervisorsitesListAdapter extends RecyclerView.Adapter<SupervisorsitesListAdapter.MyViewHolder> {

    private List<sitesListData> getstaffMembersLists;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;
    private TechniciandetailsActivity context;
    int tech_id, ticket_count;
    String tech_name, tech_mobile;
    private ArrayList<sitesListData> filterList;


    public SupervisorsitesListAdapter(List<sitesListData> staffMembersLists, CommonSharePrefrences comShare, String techname, int techid, String techmobile, int ticketcount, TechniciandetailsActivity context) {
        this.getstaffMembersLists = staffMembersLists;
        this.comShare = comShare;
        this.context = context;
        tech_id = techid;
        tech_name = techname;
        tech_mobile = techmobile;
        ticket_count = ticketcount;
        this.filterList = new ArrayList<sitesListData>();
        this.filterList.addAll(getstaffMembersLists);
    }

    @Override
    public SupervisorsitesListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.supsites_list, parent, false);
        return new SupervisorsitesListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SupervisorsitesListAdapter.MyViewHolder holder, int position) {
        sitesListData response = getstaffMembersLists.get(position);
        holder.sitename.setText(response.getSiteModel().getSiteName());


        holder.siteid.setText("#" + response.getSiteModel().getSiteUniqueId());



        try {


            String currentString = response.getSiteModel().getInstallationDate();
            String[] separated = currentString.split(" ");


            holder.sitetime.setText("" + separated[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }

       // holder.sitetime.setText("" + response.getSiteModel().getInstallationDate());

        holder.listcount.setText("" + response.getSiteModel().getTicketsCount());

        String img = response.getSiteModel().getPhoto();
        try {
            if (img != null) {
                holder.siteimage.setImageBitmap(ConstantsHelper.getBitmap(context, img));
            } else {
                holder.siteimage.setImageResource(R.drawable.suplogo);
            }

        } catch (NullPointerException e) {

        }
    }

    @Override
    public int getItemCount() {
        return getstaffMembersLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView sitename, sitetime, listcount, siteid;
        LinearLayout cardview;
        RelativeLayout card;
        ImageView siteimage;


        public MyViewHolder(View view) {
            super(view);

            sitename = (TextView) view.findViewById(R.id.sitename);
            siteid = (TextView) view.findViewById(R.id.siteid);
            sitetime = (TextView) view.findViewById(R.id.input_time);
            cardview = (LinearLayout) view.findViewById(R.id.card_view);
            card = (RelativeLayout) view.findViewById(R.id.card);
            siteimage = (ImageView) view.findViewById(R.id.site_image);
            listcount = (TextView) view.findViewById(R.id.listcount);

         /*   cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        Intent intent = new Intent(context, SitesDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("site_id", String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getSiteModel().getSiteId()));
                        context.startActivity(intent);


                }
            });
*/
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SitesDetailsActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    intent.putExtra("site_id", String.valueOf(getstaffMembersLists.get(getLayoutPosition()).getSiteModel().getSiteId()));
                    intent.putExtra("site_uniqid", getstaffMembersLists.get(getLayoutPosition()).getSiteModel().getSiteUniqueId());
                    intent.putExtra("tech_id", String.valueOf(tech_id));
                    intent.putExtra("tech_name", String.valueOf(tech_name));
                    intent.putExtra("tech_mobile", String.valueOf(tech_mobile));
                    intent.putExtra("tech_ticketcount", String.valueOf(ticket_count));
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
            for (sitesListData retailer : filterList) {
                if (retailer.getSiteModel().getSiteUniqueId().startsWith(charText) || retailer.getSiteModel().getSiteName().toUpperCase().contains(charText)) {
                    getstaffMembersLists.add(retailer);
                }
            }
        }
        notifyDataSetChanged();
    }

}