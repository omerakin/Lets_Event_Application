package com.example.akin_.letseventapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by akin_ on 23.12.2015.
 */
public class ListViewFriend_List extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;

    private ArrayList<String> ObjectIdofFriendList;
    private ArrayList<String> arrayListPicture;
    private String ObjectIdOfFriend;
    private List<ParseObject> FriendNameLastnameParseObject;

    public ListViewFriend_List(Context context,
                               ArrayList<String> ObjectIdofFriendList,
                               ArrayList<String> arrayListPicture) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        this.ObjectIdofFriendList = ObjectIdofFriendList;
        this.arrayListPicture = arrayListPicture;
    }

    public class ViewHolder {

        TextView textViewRequest;
        ImageView imageViewSidePicture;
    }

    @Override
    public int getCount() { return ObjectIdofFriendList.size(); }

    @Override
    public Object getItem(int position) { return ObjectIdofFriendList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_actionsitem2, null);
            holder.textViewRequest = (TextView) convertView.findViewById(R.id.textViewRequest);
            holder.imageViewSidePicture = (ImageView) convertView.findViewById(R.id.imageViewSidePicture);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageViewSidePicture.setImageResource(R.drawable.kid_icon);
        holder.textViewRequest.setText(getNameandLastnameofObjectId(ObjectIdofFriendList.get(position)));

        System.out.print("deneme");
        return convertView;
    }

    private String getNameandLastnameofObjectId(final String s) {

        ObjectIdOfFriend="";
        ParseQuery<ParseObject> query = ParseQuery.getQuery("TestAccount");
        try {
            FriendNameLastnameParseObject = query.find();
            for (ParseObject pAction : FriendNameLastnameParseObject) {
                String pObjectId = pAction.getObjectId();
                String pName;
                String pLastname;
                if (s.equals(pObjectId)) {
                    pName = (String) pAction.get("Name");
                    pLastname = (String) pAction.get("Lastname");
                    pName = pName.substring(0,1).toUpperCase() + pName.substring(1);
                    pLastname = pLastname.substring(0,1).toUpperCase() + pLastname.substring(1);
                    ObjectIdOfFriend = pName + " " + pLastname;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ObjectIdOfFriend;
    }
}
