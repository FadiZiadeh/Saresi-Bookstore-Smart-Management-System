package com.example.database1;

public class Supplier {
    private String name;
    private String ssn;
    private String company;
    private String address;

    public Supplier() {}

    public Supplier(String name, String ssn, String company, String address) {
        this.name = name;
        this.ssn = ssn;
        this.company = company;
        this.address = address;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return "Supplier{" +
                "name='" + name + '\'' +
                ", ssn='" + ssn + '\'' +
                ", company='" + company + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

