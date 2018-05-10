package com.example.veraj.ordersummary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ProvinceActivity extends AppCompatActivity{

    public static final String KEY = "001";
    public static final String KEY2 = "002";
    private static final String TAG = "com.example.veraj.ordersummary";
    private ArrayList<SortedOrders> sortedOrders;
    private ArrayList<String> mNames;
    private ArrayList<String> headingnames = new ArrayList<String>();

    private ArrayList<String> provincelist = new ArrayList<>();
    int order_count = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        String group ="";
        Intent intent = getIntent();
        sortedOrders= (ArrayList<SortedOrders>)getIntent().getSerializableExtra(KEY);
        mNames = (ArrayList<String>)getIntent().getSerializableExtra(KEY2);


        Collections.sort(sortedOrders, new Comparator<SortedOrders>() {
            public int compare(SortedOrders v1, SortedOrders v2) {
                return v1.getProvince_shippedto().compareTo(v2.getProvince_shippedto());
            }
        });

        for (int i = 0; i < sortedOrders.size() - 1; i++) {
            if (mNames.get(i).equals(mNames.get(i + 1))) {
                order_count++;
            } else {
//                group += mNames.get(i) + "\n\n\n";
                headingnames.add(mNames.get(i));
                for (int j = i; j> i -order_count; j--){
                    group += (sortedOrders.get(i).getfullName()+ "                  " +  "                   "+ sortedOrders.get(j).getTotal_price() + System.lineSeparator()+ "\n" + sortedOrders.get(j).getDate() +" | "+  sortedOrders.get(i).getFinancial_status() + System.lineSeparator()+ System.lineSeparator()+ System.lineSeparator());

                }
                provincelist.add(group);
                group = "";

                order_count = 1;
            }
        }



        fillRecyclerView();

    }

    private void fillRecyclerView(){
        Log.d(TAG, "fillRecyclerView: recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        ProvinceAdapterClass adapter = new ProvinceAdapterClass(this, provincelist, headingnames);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
