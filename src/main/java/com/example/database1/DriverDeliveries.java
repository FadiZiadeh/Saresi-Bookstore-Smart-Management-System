package com.example.database1;

public class DriverDeliveries {
    private String ssn;
    private String name;
    private int deliveryCount;

    public DriverDeliveries(String ssn, String name, int deliveryCount) {
        this.ssn = ssn;
        this.name = name;
        this.deliveryCount = deliveryCount;
    }

    public String getSsn() { return ssn; }
    public String getName() { return name; }
    public int getDeliveryCount() { return deliveryCount; }
}