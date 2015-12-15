package com.example.akin_.letseventapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf Erdal on 15.12.2015.
 */
public class ListViewAdapter_Requests extends BaseAdapter{
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<UserActions_Class> useractionsList = null;
    private ArrayList<UserActions_Class> arraylist;

    public ListViewAdapter_Requests(Context context,
                           List<UserActions_Class> useractionsList) {
        mContext = context;
        this.useractionsList = useractionsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<UserActions_Class>();
        this.arraylist.addAll(useractionsList);
    }

    public class ViewHolder {
        TextView requestType;
        TextView requestBy;
        TextView requestTo;
    }

    @Override
    public int getCount() {
        return useractionsList.size();
    }

    @Override
    public UserActions_Class getItem(int position) {
        return useractionsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parents){
        final ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listview_actionsitem, null);
            // Locate the TextViews in listview_item.xml
            holder.requestType = (TextView) view.findViewById(R.id.requestType);
            holder.requestBy = (TextView) view.findViewById(R.id.requestBy);
            holder.requestTo = (TextView) view.findViewById(R.id.requestTo);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //set results to into TextViews
        holder.requestType.setText(useractionsList.get(position).getTypeOfAction());
        holder.requestBy.setText(useractionsList.get(position).getActionBy());
        holder.requestTo.setText(useractionsList.get(position).getActionTo());

        // Listen for ListView Item Click
       /* view.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, EventDescriptionActivity.class);
                // Pass all data rank
                intent.putExtra("rank",
                        (useractionsList.get(position).getTypeOfAction()));
                // Pass all data country
                intent.putExtra("country",
                        (useractionsList.get(position).getActionBy()));
                // Pass all data population
                intent.putExtra("population",
                        (useractionsList.get(position).getActionTo()));
                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });*/

        return view;

    }


}
