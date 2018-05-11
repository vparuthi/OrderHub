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

public class YearAdapterClass extends RecyclerView.Adapter<YearAdapterClass.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageNames;
    private ArrayList<String> price_list;
    private ArrayList<String> province_list;

    private Context mContext;

    YearAdapterClass(Context context, ArrayList<String> imageNames, ArrayList<String> province, ArrayList<String> price) {
        mImageNames = imageNames;
        mContext = context;
        price_list = price;
        province_list = province;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_yearlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder - Year View: called.");
        holder.imageName.setText(mImageNames.get(position));
        holder.price_text.setText(price_list.get(position));
        holder.province_text.setText(province_list.get(position));

    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView imageName;
        TextView price_text;
        TextView province_text;
        RelativeLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            imageName = itemView.findViewById(R.id.date);
            price_text = itemView.findViewById(R.id.price);
            province_text = itemView.findViewById(R.id.province);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}