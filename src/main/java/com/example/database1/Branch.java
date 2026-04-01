package com.example.database1;

import java.math.BigDecimal;

public class Branch {
    private String address;
    private BigDecimal income;
    private String landline;

    public Branch() {}

    public Branch(String address, BigDecimal income, String landline) {
        this.address = address;
        this.income = income;
        this.landline = landline;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public BigDecimal getIncome() { return income; }
    public void setIncome(BigDecimal income) { this.income = income; }

    public String getLandline() { return landline; }
    public void setLandline(String landline) { this.landline = landline; }

    @Override
    public String toString() {
        return "Branch{" +
                "address='" + address + '\'' +
                ", income=" + income +
                ", landline='" + landline + '\'' +
                '}';
    }
}
