package com.example.akin_.letseventapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yusuf Erdal on 16.12.2015.
 */
public class ListViewAdapter_Comments extends BaseAdapter{
    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<Comment2_Class> commentList = null;
    private ArrayList<Comment2_Class> arraylist;

    public ListViewAdapter_Comments(Context context,
                                    List<Comment2_Class> commentList) {
        mContext = context;
        this.commentList = commentList;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<Comment2_Class>();
        this.arraylist.addAll(commentList);
    }

    public class ViewHolder {
        TextView commentUserName;
        TextView commentCreated;
        TextView commentText;
    }

    @Override
    public int getCount() {
        return commentList.size();
    }

    @Override
    public Comment2_Class getItem(int position) {
        return commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parents){
        final ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.listviewcomment_layout, null);
            // Locate the TextViews in listview_item.xml
            holder.commentUserName = (TextView) view.findViewById(R.id.commentUserName);
            holder.commentCreated = (TextView) view.findViewById(R.id.commentCreated);
            holder.commentText = (TextView) view.findViewById(R.id.commentText);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //set results to into TextViews
        holder.commentUserName.setText(commentList.get(position).getUser());
        holder.commentCreated.setText(commentList.get(position).getCreatedAt());
        holder.commentText.setText(commentList.get(position).getComment());

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
