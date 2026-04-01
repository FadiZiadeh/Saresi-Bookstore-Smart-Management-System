package com.example.database1;

public class WarehouseProductCount {
    private String warehouseAddress;
    private int productCount;

    public WarehouseProductCount(String warehouseAddress, int productCount) {
        this.warehouseAddress = warehouseAddress;
        this.productCount = productCount;
    }

    public String getWarehouseAddress() { return warehouseAddress; }
    public int getProductCount() { return productCount; }
}
