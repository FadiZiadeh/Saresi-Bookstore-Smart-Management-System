package com.example.database1;

public class Supply {
    private String supplierName;
    private String supplierSsn;
    private int productId;

    public Supply() {}

    public Supply(String supplierName, String supplierSsn, int productId) {
        this.supplierName = supplierName;
        this.supplierSsn = supplierSsn;
        this.productId = productId;
    }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getSupplierSsn() { return supplierSsn; }
    public void setSupplierSsn(String supplierSsn) { this.supplierSsn = supplierSsn; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    @Override
    public String toString() {
        return "Supply{" +
                "supplierName='" + supplierName + '\'' +
                ", supplierSsn='" + supplierSsn + '\'' +
                ", productId=" + productId +
                '}';
    }
}

