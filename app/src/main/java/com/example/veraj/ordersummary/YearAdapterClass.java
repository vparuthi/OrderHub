package com.example.veraj.ordersummary;

import android.content.Context;
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

    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> price_list = new ArrayList<>();
    private ArrayList<String> province_list = new ArrayList<>();

    //    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public YearAdapterClass(Context context, ArrayList<String> imageNames, ArrayList<String> province, ArrayList<String> price ) {
        mImageNames = imageNames;
        mContext = context;
        price_list = price;
        province_list = province;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_yearlistitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder - Year View: called.");


        holder.imageName.setText(mImageNames.get(position));
        holder.price_text.setText(price_list.get(position));
        holder.province_text.setText(province_list.get(position));

//        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d(TAG, "onClick: clicked on: " + mImageNames.get(position));
//
//                Toast.makeText(mContext, mImageNames.get(position), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(mContext, GalleryActivity.class);
//                intent.putExtra("image_url", mImages.get(position));
//                intent.putExtra("image_name", mImageNames.get(position));
//                mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView imageName;
        TextView price_text;
        TextView province_text;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
//            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.date);
            price_text = itemView.findViewById(R.id.price);
            province_text = itemView.findViewById(R.id.province);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}