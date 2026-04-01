package com.example.database1;

import java.math.BigDecimal;

public class CustomerSales {
    private String customerName;
    private BigDecimal totalSpent;

    public CustomerSales(String customerName, BigDecimal totalSpent) {
        this.customerName = customerName;
        this.totalSpent = totalSpent;
    }

    public String getCustomerName() { return customerName; }
    public BigDecimal getTotalSpent() { return totalSpent; }
}
