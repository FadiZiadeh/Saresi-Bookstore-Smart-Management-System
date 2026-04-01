package com.example.database1;

import java.time.LocalDate;

public class Car {
    private String plateNum;
    private LocalDate purchaseDate;
    private String address;

    public Car() {}

    public Car(String plateNum, LocalDate purchaseDate, String address) {
        this.plateNum = plateNum;
        this.purchaseDate = purchaseDate;
        this.address = address;
    }

    public String getPlateNum() { return plateNum; }
    public void setPlateNum(String plateNum) { this.plateNum = plateNum; }

    public LocalDate getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
