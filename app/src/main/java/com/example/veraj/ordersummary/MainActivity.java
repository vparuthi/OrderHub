package com.example.veraj.ordersummary;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.*;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    public static final String KEY = "001";
    public static final String KEY2 = "002";

    private RequestQueue mQueue;
    TextView heading;
    TextView number_of_orders;
    TextView province_heading;
    TextView price;
    private static final String TAG = "com.example.veraj.ordersummary";
    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> countedNames = new ArrayList<>();
    private ArrayList<String> tenYearNames = new ArrayList<>();
    private ArrayList<String> price_list = new ArrayList<>();
    private ArrayList<String> province_list = new ArrayList<>();

    private ArrayList<String> mImageUrls = new ArrayList<>();
    private int order_count = 1;
    private int yearly_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heading = (TextView) findViewById(R.id.year_heading);
        province_heading = (TextView) findViewById(R.id.province_heading);
        final ArrayList<SortedOrders> sorted_items;

        mQueue = Volley.newRequestQueue(this);
        sorted_items = jsonParse();

        province_heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, ProvinceActivity.class);
                myIntent.putExtra(KEY, sorted_items);
                myIntent.putExtra(KEY2, mNames);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }


    private ArrayList<SortedOrders> jsonParse() {
        final ArrayList<SortedOrders> sorted_items = new ArrayList<SortedOrders>();

        String url = "https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int count = 0;
                number_of_orders = (TextView) findViewById(R.id.recent_heading);
                number_of_orders.setBackgroundColor(Color.parseColor("#e5e5e5"));


                try {
                    JSONArray jsonArray = response.getJSONArray("orders");
                    SortedOrders[] sortedOrders = new SortedOrders[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        if (jsonObj.getString("created_at").contains("2017")){ //check how many orders in 2017
//                            tenYearNames.add(jsonObj.getString("created_at"));
                            yearly_count++;
                        }
                        if (yearly_count == 0){
                            number_of_orders.setText("No orders in 2017");
                        }else{
                            number_of_orders.setText("Total orders in 2017:    "+ yearly_count);
                        }
                        if(yearly_count>0){
                            sorted_items.add(new SortedOrders());
                            sorted_items.get(i).setTotal_price(jsonObj.getString("total_price"));
                            sorted_items.get(i).setDate(jsonObj.getString("created_at"));
                            sorted_items.get(i).setFinancial_status(jsonObj.getString("financial_status"));
                            if(sorted_items.get(i).getFinancial_status().equals("paid")){
                                sorted_items.get(i).setFinancial_status("Paid");
                            }
                            sortedOrders[i] = new SortedOrders();
                            sortedOrders[i].setDate(jsonObj.getString("created_at"));
                            sortedOrders[i].setTotal_price(jsonObj.getString("total_price"));
                        }

                        if (jsonObj.has("billing_address")) {
                            mNames.add(jsonObj.getJSONObject("billing_address").getString("province"));
                            count++;
                            sortedOrders[i].setProvince_shippedto(jsonObj.getJSONObject("billing_address").getString("province"));
                            sorted_items.get(i).setProvince_shippedto(jsonObj.getJSONObject("billing_address").getString("province"));
                            sorted_items.get(i).setfullName(jsonObj.getJSONObject("customer").getString("first_name") + " "+ jsonObj.getJSONObject("customer").getString("last_name"));
                        }
                        else {
                            mNames.add("Unknown Location. ID: " + jsonObj.getString("id"));
                            sorted_items.get(i).setfullName("Unknown");
                            sortedOrders[i].setProvince_shippedto("Unknown Location. ID: " + jsonObj.getString("id"));
                            sorted_items.get(i).setProvince_shippedto("Unknown Location. ID: " + jsonObj.getString("id"));

                            Log.i(TAG, "Object with unknown location found");
                        }

                        sorted_items.get(i).setDate(sorted_items.get(i).getDate().substring(0, Math.min(sorted_items.get(i).getDate().length(), 10)));

                    }
                    Collections.sort(sorted_items, new Comparator<SortedOrders>() {
                        public int compare(SortedOrders v1, SortedOrders v2) {
                            return v1.getDate().compareTo(v2.getDate());
                        }
                    });
                    Collections.reverse(sorted_items);
//                    if (yearly_count>10){
//                        tenYearNames.subList(10,tenYearNames.size()).clear();
//                    }

                    for (int i = 0; i < 10; i++){
//                        sorted_items.get(i).setDate(sorted_items.get(i).getDate().substring(0, Math.min(sorted_items.get(i).getDate().length(), 10)));
                        tenYearNames.add(sorted_items.get(i).getDate());
                        province_list.add(sorted_items.get(i).getProvince_shippedto());
                        price_list.add(sorted_items.get(i).getTotal_price());
                    }

                    Collections.sort(mNames);



                    for (int i = 0; i < mNames.size() - 1; i++) {
                        if (mNames.get(i).equals(mNames.get(i + 1))) {
                            order_count++;
                        } else {
                            countedNames.add(order_count + " orders from " + mNames.get(i));
                            order_count = 1;
                        }
                    }

                    countedNames.add(order_count + " orders from " + mNames.get(mNames.size() - 1));

                    System.out.println(tenYearNames);

                    initRecyclerView();


                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
        return sorted_items;
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.province_orders);
        AdapterClass adapter = new AdapterClass(this, countedNames);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView yearlyView = findViewById(R.id.year_list);
        YearAdapterClass yearAdapterClass = new YearAdapterClass(this, tenYearNames, province_list, price_list);
        yearlyView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        yearlyView.setAdapter(yearAdapterClass);
        yearlyView.setLayoutManager(new LinearLayoutManager(this));

    }
}
