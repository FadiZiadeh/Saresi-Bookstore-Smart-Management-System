package com.example.database1;

public class SupplierProductCount {
    private String supplierName;
    private int productCount;

    public SupplierProductCount(String supplierName, int productCount) {
        this.supplierName = supplierName;
        this.productCount = productCount;
    }

    public String getSupplierName() { return supplierName; }
    public int getProductCount() { return productCount; }
}
