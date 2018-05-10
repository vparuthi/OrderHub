package com.example.veraj.ordersummary;

import java.util.ArrayList;

public class SortedOrders implements java.io.Serializable {
    private String date;
    private String total_price;
    private String province;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public String getProvince_shippedto() {
        return province;
    }

    public void setProvince_shippedto(String province) {
        this.province = province;
    }

    public static ArrayList<String> multiCheck(ArrayList<String> mNames){
        ArrayList<String> countedNames = new ArrayList<>();
        int order_count = 1;

        for (int i =1; i < mNames.size(); i ++){
            if(mNames.get(i).equals(mNames.get(i -1)) || (i == mNames.size()-1 && mNames.get(i).equals(mNames.get(i -1)))){ //check if the element i and the previous are the same
                order_count++;
            }
            if(i != mNames.size()-1 && mNames.get(i).equals(mNames.get(i -1)) && !mNames.get(i).equals(mNames.get(i + 1))) { //check if its the last iteration of a product from that province
                countedNames.add(order_count + " orders from " + mNames.get(i));
                order_count = 1;
            }
            else if(mNames.get(i).contains("Unknown")){ //products with no billing_address
                countedNames.add(mNames.get(i));
            }
            else if(i != mNames.size()-1 && !mNames.get(i).equals(mNames.get(i -1)) && !mNames.get(i).equals(mNames.get(i + 1))){ //check if there is only one order from a province
                order_count = 1;
                countedNames.add(order_count + " orders from " + mNames.get(i));
            }
            else if(i == mNames.size()-1){
                if(!mNames.get(i).equals(mNames.get(i -1)) && !mNames.get(i).equals(mNames.get(i + 1))){ //if the last element is the only one from its province
                    order_count =1 ;
                    countedNames.add(order_count + " orders from " + mNames.get(i));
                }
            }
        }
        return countedNames;
    }
}
