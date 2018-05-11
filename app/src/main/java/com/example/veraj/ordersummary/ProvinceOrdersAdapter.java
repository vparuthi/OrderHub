package com.example.veraj.ordersummary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ProvinceOrdersAdapter extends RecyclerView.Adapter<ProvinceOrdersAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> order;
    private ArrayList<String> province_heading;
    private Context mContext;

    ProvinceOrdersAdapter(Context context, ArrayList<String> orders, ArrayList<String> headings) {
        order = orders;
        mContext = context;
        province_heading = headings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_detailedprovince, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(order.get(position));
        holder.headings.setText(province_heading.get(position));
    }

    @Override
    public int getItemCount() {
        return order.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView imageName;
        TextView headings;
        RelativeLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            imageName = itemView.findViewById(R.id.province);
            headings = itemView.findViewById(R.id.heading);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}