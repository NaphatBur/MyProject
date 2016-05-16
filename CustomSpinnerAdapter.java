package com.app.ordering.orderingsystem;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 5/4/2016.
 */
public class CustomSpinnerAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<String> mData;
    private LayoutInflater mInflater;

    public CustomSpinnerAdapter(Context context, ArrayList<String> data){
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.list_item_adapter, parent, false);
            TextView txtTable = (TextView) convertView.findViewById(R.id.name);
            txtTable.setText(mData.get(position));
        }
        return convertView;
    }
}
