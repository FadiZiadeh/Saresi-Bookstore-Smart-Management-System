package com.example.database1;

public class Warehouse {
    private String address;
    private String landline;
    private int space;
    private String storage;

    public Warehouse() {}

    public Warehouse(String address, String landline, int space, String storage) {
        this.address = address;
        this.landline = landline;
        this.space = space;
        this.storage = storage;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getLandline() { return landline; }
    public void setLandline(String landline) { this.landline = landline; }

    public int getSpace() { return space; }
    public void setSpace(int space) { this.space = space; }

    public String getStorage() { return storage; }
    public void setStorage(String storage) { this.storage = storage; }

    @Override
    public String toString() {
        return "Warehouse{" +
                "address='" + address + '\'' +
                ", landline='" + landline + '\'' +
                ", space=" + space +
                ", storage='" + storage + '\'' +
                '}';
    }
}
