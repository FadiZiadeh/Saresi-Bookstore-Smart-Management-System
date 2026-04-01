package com.example.database1;

public class Drives {
    private String ssn;
    private String plateNum;
    private int orderId;

    public Drives() {}

    public Drives(String ssn, String plateNum, int orderId) {
        this.ssn = ssn;
        this.plateNum = plateNum;
        this.orderId = orderId;
    }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getPlateNum() { return plateNum; }
    public void setPlateNum(String plateNum) { this.plateNum = plateNum; }

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    @Override
    public String toString() {
        return "Drives{" +
                "ssn='" + ssn + '\'' +
                ", plateNum='" + plateNum + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}

