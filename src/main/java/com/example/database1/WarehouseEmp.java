package com.example.database1;

public class WarehouseEmp {
    private String ssn;
    private String warehouseAddress;

    public WarehouseEmp() {}

    public WarehouseEmp(String ssn, String warehouseAddress) {
        this.ssn = ssn;
        this.warehouseAddress = warehouseAddress;
    }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getWarehouseAddress() { return warehouseAddress; }
    public void setWarehouseAddress(String warehouseAddress) { this.warehouseAddress = warehouseAddress; }

    @Override
    public String toString() {
        return "WarehouseEmp{" +
                "ssn='" + ssn + '\'' +
                ", warehouseAddress='" + warehouseAddress + '\'' +
                '}';
    }
}
