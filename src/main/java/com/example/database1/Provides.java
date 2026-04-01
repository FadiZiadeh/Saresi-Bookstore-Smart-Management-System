package com.example.database1;

public class Provides {
    private String warehouseAddress;
    private int productId;

    public Provides() {}

    public Provides(String warehouseAddress, int productId) {
        this.warehouseAddress = warehouseAddress;
        this.productId = productId;
    }

    public String getWarehouseAddress() { return warehouseAddress; }
    public void setWarehouseAddress(String warehouseAddress) { this.warehouseAddress = warehouseAddress; }

    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    @Override
    public String toString() {
        return "Provides{" +
                "warehouseAddress='" + warehouseAddress + '\'' +
                ", productId=" + productId +
                '}';
    }
}
