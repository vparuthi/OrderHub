package com.example.veraj.ordersummary;

public class Order implements java.io.Serializable {
    private String date;
    private String total_price;
    private String province;
    private String fullName;
    private String financial_status;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getfullName() {
        return fullName;
    }

    public void setfullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFinancial_status() {
        return financial_status;
    }

    public void setFinancial_status(String financial_status) {
        this.financial_status = financial_status;
    }

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

}
