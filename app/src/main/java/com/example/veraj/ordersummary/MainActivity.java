package com.example.veraj.ordersummary;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    public static final String KEY = "001";
    public static final String KEY2 = "002";
    private static final String TAG = "com.example.veraj.ordersummary";
    TextView heading;
    TextView number_of_orders;
    TextView province_heading;
    TextView price;
    private RequestQueue mQueue;
    private ArrayList<String> provinceNames = new ArrayList<>();
    private ArrayList<String> provinceTotals = new ArrayList<>();
    private ArrayList<String> tenYearNames = new ArrayList<>();
    private ArrayList<String> priceList = new ArrayList<>();
    private ArrayList<String> provinceList = new ArrayList<>();

    private int order_count = 1;
    private int yearly_count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heading = findViewById(R.id.year_heading);
        province_heading = findViewById(R.id.province_heading);
        final ArrayList<SortedOrders> sortedOrders;

        mQueue = Volley.newRequestQueue(this);
        sortedOrders = jsonParse();

        province_heading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "OnClick: Province Heading");
                Intent myIntent = new Intent(MainActivity.this, ProvinceActivity.class);
                myIntent.putExtra(KEY, sortedOrders);
                myIntent.putExtra(KEY2, provinceNames);
                MainActivity.this.startActivity(myIntent);
            }
        });
    }


    private ArrayList<SortedOrders> jsonParse() {
        final ArrayList<SortedOrders> sortedOrders = new ArrayList<>();
        String url = "https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int count = 0;
                number_of_orders = findViewById(R.id.recent_heading);
                number_of_orders.setBackgroundColor(Color.parseColor("#e5e5e5"));


                try {
                    JSONArray jsonArray = response.getJSONArray("orders");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObj = jsonArray.getJSONObject(i);
                        if (jsonObj.getString("created_at").contains("2017")){ //check how many orders in 2017
                            yearly_count++;
                        }
                        if (yearly_count == 0){
                            number_of_orders.setText("No orders in 2017");
                        }else{
                            number_of_orders.setText("Total orders in 2017:    "+ yearly_count);
                        }
                        if(yearly_count>0){ //adding data to objects
                            sortedOrders.add(new SortedOrders());
                            sortedOrders.get(i).setTotal_price("$"+jsonObj.getString("total_price"));
                            sortedOrders.get(i).setDate(jsonObj.getString("created_at"));
                            sortedOrders.get(i).setFinancial_status(jsonObj.getString("financial_status"));
                            if(sortedOrders.get(i).getFinancial_status().equals("paid")){
                                sortedOrders.get(i).setFinancial_status("Paid");
                            }
                        }

                        if (jsonObj.has("billing_address")) {
                            provinceNames.add(jsonObj.getJSONObject("billing_address").getString("province"));
                            count++;
                            sortedOrders.get(i).setProvince_shippedto(jsonObj.getJSONObject("billing_address").getString("province"));
                            sortedOrders.get(i).setfullName(jsonObj.getJSONObject("customer").getString("first_name") + " "+ jsonObj.getJSONObject("customer").getString("last_name"));
                        }
                        else {
                            provinceNames.add("Unknown Location. ID: " + jsonObj.getString("id"));
                            sortedOrders.get(i).setfullName("Unknown");
                            sortedOrders.get(i).setProvince_shippedto("Unknown Location. ID: " + jsonObj.getString("id"));
                            Log.i(TAG, "Unknown location found");
                        }

                        sortedOrders.get(i).setDate(sortedOrders.get(i).getDate().substring(0, Math.min(sortedOrders.get(i).getDate().length(), 10)));

                    }
                    Collections.sort(sortedOrders, new Comparator<SortedOrders>() {
                        public int compare(SortedOrders v1, SortedOrders v2) { //sort the orders by most recent
                            return v1.getDate().compareTo(v2.getDate());
                        }
                    });
                    Collections.reverse(sortedOrders);


                    for (int i = 0; i < 10; i++){ //adding the 10 most recent orders to ArrayList for Orders by Year
                        tenYearNames.add(sortedOrders.get(i).getDate());
                        provinceList.add(sortedOrders.get(i).getProvince_shippedto());
                        priceList.add(sortedOrders.get(i).getTotal_price());
                    }

                    Collections.sort(provinceNames); //Alphabetical sort of orders



                    for (int i = 0; i < provinceNames.size() - 1; i++) { //check how many are from each respective province
                        if (provinceNames.get(i).equals(provinceNames.get(i + 1))) {
                            order_count++;
                        } else {
                            provinceTotals.add(order_count + " orders from " + provinceNames.get(i));
                            order_count = 1;
                        }
                    }
                    provinceTotals.add(order_count + " orders from " + provinceNames.get(provinceNames.size() - 1)); //checks last item in ArryList
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
        return sortedOrders;
    }

    private void initRecyclerView(){ //Calls the adapter to fill RecyclerViews
        Log.d(TAG, "initRecyclerView: Province");
        RecyclerView recyclerView = findViewById(R.id.province_orders);
        DetailedProvinceOrdersAdapter adapter = new DetailedProvinceOrdersAdapter(this, provinceTotals);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Log.d(TAG, "initRecyclerView: Year");
        RecyclerView yearlyView = findViewById(R.id.year_orders);
        YearAdapterClass yearAdapterClass = new YearAdapterClass(this, tenYearNames, provinceList, priceList);
        yearlyView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        yearlyView.setAdapter(yearAdapterClass);
        yearlyView.setLayoutManager(new LinearLayoutManager(this));

    }
}
