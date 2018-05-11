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

public class DetailedProvinceOrdersAdapter extends RecyclerView.Adapter<DetailedProvinceOrdersAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> order;
    private Context mContext;

    DetailedProvinceOrdersAdapter(Context context, ArrayList<String> orders) {
        order = orders;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_provinceitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        holder.provinceName.setText(order.get(position));

    }

    @Override
    public int getItemCount() {
        return order.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView provinceName;
        RelativeLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            provinceName = itemView.findViewById(R.id.province);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}