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
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> order = new ArrayList<>();
    private Context mContext;

    public AdapterClass(Context context, ArrayList<String> orders) {
        order = orders;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        holder.imageName.setText(order.get(position));

    }

    @Override
    public int getItemCount() {
        return order.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            imageName = itemView.findViewById(R.id.province);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}