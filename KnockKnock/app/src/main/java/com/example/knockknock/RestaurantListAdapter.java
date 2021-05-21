package com.example.knockknock;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class RestaurantListAdapter extends BaseAdapter {

    private Context context;
    private List<Restaurant> restaurantList;
    private List<Restaurant> saveList;

    public RestaurantListAdapter(Context context, List<Restaurant> restaurantList, List<Restaurant> saveList) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.saveList=saveList;
    }

    @Override
    public int getCount() {

        return restaurantList.size();
    }

    @Override
    public Object getItem(int i) {
        return restaurantList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= View.inflate(context, R.layout.restuarant_list, null);
        TextView restaurantbtn = (TextView)v.findViewById(R.id.restaurantbtn);

        restaurantbtn.setText(restaurantList.get(i).getRes_name());

        v.setTag(restaurantList.get(i).getRes_name());
        return v;

    }


}