package com.example.akin_.letseventapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    private ArrayList<String> sidePictures;

    public ListViewAdapter_Requests(Context context,
                                    List<UserActions_Class> useractionsList,
                                    ArrayList<String> arrayListPicture) {
        mContext = context;
        this.useractionsList = useractionsList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<UserActions_Class>();
        this.arraylist.addAll(useractionsList);
        sidePictures = new ArrayList<String>();
        sidePictures.addAll(arrayListPicture);
    }

    public class ViewHolder {
        /*
        TextView requestType;
        TextView requestBy;
        TextView requestTo;
        */
        TextView textViewRequest;
        ImageView imageViewSidePicture;
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
            view = inflater.inflate(R.layout.listview_actionsitem2, null);
            /*
            // Locate the TextViews in listview_item.xml
            holder.requestType = (TextView) view.findViewById(R.id.requestType);
            holder.requestBy = (TextView) view.findViewById(R.id.requestBy);
            holder.requestTo = (TextView) view.findViewById(R.id.requestTo);
            */

            holder.textViewRequest = (TextView) view.findViewById(R.id.textViewRequest);
            holder.imageViewSidePicture = (ImageView) view.findViewById(R.id.imageViewSidePicture);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        /*
        //set results to into TextViews
        holder.requestType.setText(useractionsList.get(position).getTypeOfAction());
        holder.requestBy.setText(useractionsList.get(position).getActionBy());
        holder.requestTo.setText(useractionsList.get(position).getActionTo());
        */

        if (sidePictures.get(position).toString().equals("Male")){
            holder.imageViewSidePicture.setImageResource(R.drawable.male);
        } else if (sidePictures.get(position).toString().equals("Female")) {
            holder.imageViewSidePicture.setImageResource(R.drawable.female);
        } else if (sidePictures.get(position).toString().equals("Facebook")) {
            holder.imageViewSidePicture.setImageResource(R.drawable.facebook);
        }

        if (useractionsList.get(position).getTypeOfAction().equals("Attend")){
            holder.textViewRequest.setText(useractionsList.get(position).getActionBy() + " is attended to "
                    +useractionsList.get(position).getActionTo() + " event.");

        } else if (useractionsList.get(position).getTypeOfAction().equals("Comment")) {
            holder.textViewRequest.setText(useractionsList.get(position).getActionBy() + " commented about "
                    +useractionsList.get(position).getActionTo() + " event.");

        } else if (useractionsList.get(position).getTypeOfAction().equals("FriendRequest")) {
            holder.textViewRequest.setText(useractionsList.get(position).getActionBy() + " sent frend request to "
                    +useractionsList.get(position).getActionTo() + ".");

        }

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
