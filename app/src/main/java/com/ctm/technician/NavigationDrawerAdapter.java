package com.ctm.technician;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ctm.technician.constants.CommonFunctions;
import com.ctm.technician.constants.CommonSharePrefrences;

public class NavigationDrawerAdapter extends BaseAdapter {
    private Context context;
    private String[] drawerItems;
    private String[] drawerImages;
    private CommonFunctions com;
    private CommonSharePrefrences comShare;

    public NavigationDrawerAdapter(Context context) {
        this.context = context;
        com = new CommonFunctions(context);
        comShare = CommonSharePrefrences.getInstance(context);

        Resources resources = context.getResources();


        drawerItems = resources.getStringArray(R.array.drawerItems);
        drawerImages = resources.getStringArray(R.array.drawerImages);

    }

    @Override
    public int getCount() {
        return drawerItems.length;
    }

    @Override
    public Object getItem(int position) {
        return drawerItems[position];
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public Object getDrawerIcon(int position) {
        return drawerImages[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.landingpage_new_leftmenu_item, parent, false);


            holder = new ViewHolder();
            holder.notifDesc = (TextView) convertView.findViewById(R.id.txt_landingpage_leftMenu_item);
            holder.notifIcon = (ImageView) convertView.findViewById(R.id.img_landingpage_leftMenu_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String drawerIcon = (String) getDrawerIcon(position);
        if (!drawerIcon.isEmpty()) {
            holder.notifIcon.setImageResource(context.getResources().getIdentifier(drawerIcon.substring(0, drawerIcon.length()), "mipmap", context.getPackageName()));
        }
        holder.notifDesc.setText((String) getItem(position));
        return convertView;
    }

    class ViewHolder {
        TextView notifDesc;
        ImageView notifIcon;
    }
}
