package com.example.database1;

import java.math.BigDecimal;

public class Buy {
    private String customerAddress;
    private String branchAddress;
    private BigDecimal bill;

    public Buy() {}

    public Buy(String customerAddress, String branchAddress, BigDecimal bill) {
        this.customerAddress = customerAddress;
        this.branchAddress = branchAddress;
        this.bill = bill;
    }

    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    public String getBranchAddress() { return branchAddress; }
    public void setBranchAddress(String branchAddress) { this.branchAddress = branchAddress; }

    public BigDecimal getBill() { return bill; }
    public void setBill(BigDecimal bill) { this.bill = bill; }

    @Override
    public String toString() {
        return "Buy{" +
                "customerAddress='" + customerAddress + '\'' +
                ", branchAddress='" + branchAddress + '\'' +
                ", bill=" + bill +
                '}';
    }
}
