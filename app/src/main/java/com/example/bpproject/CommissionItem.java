package com.example.bpproject;

public class CommissionItem {
    private String serviceName;
    private String price;
    private int imageResId;

    public CommissionItem(String serviceName, String price, int imageResId) {
        this.serviceName = serviceName;
        this.price = price;
        this.imageResId = imageResId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResId() {
        return imageResId;
    }
}
