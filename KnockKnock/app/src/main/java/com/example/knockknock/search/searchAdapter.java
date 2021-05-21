package com.example.knockknock.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.knockknock.R;
import com.example.knockknock.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class searchAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<Restaurant> restaurantList = new ArrayList<Restaurant>();
    private ArrayList<Restaurant> filteredList = restaurantList;

    Filter listFilter;



    public searchAdapter(Context editText, List<Restaurant> restaurants){
        this.context = context;
        this.restaurantList = restaurantList;
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int position) {
        return restaurantList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.restuarant_list, parent, false);


            TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
            TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;

            Restaurant restaurantList = filteredList.get(position);




        }

        return convertView;
    }

    @Override
    public Filter getFilter() {

        return listFilter;
    }
}
