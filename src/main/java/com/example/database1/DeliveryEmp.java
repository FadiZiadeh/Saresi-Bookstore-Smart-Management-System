package com.example.database1;

public class DeliveryEmp {
    private String ssn;

    public DeliveryEmp() {}

    public DeliveryEmp(String ssn) {
        this.ssn = ssn;
    }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    @Override
    public String toString() {
        return "DeliveryEmp{" +
                "ssn='" + ssn + '\'' +
                '}';
    }
}
