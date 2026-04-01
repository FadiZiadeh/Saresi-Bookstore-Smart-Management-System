package com.example.database1;

public class Customer {
    private String address;
    private String name;
    private String phone;

    public Customer() {}

    public Customer(String address, String name, String phone) {
        this.address = address;
        this.name = name;
        this.phone = phone;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    @Override
    public String toString() {
        return "Customer{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
