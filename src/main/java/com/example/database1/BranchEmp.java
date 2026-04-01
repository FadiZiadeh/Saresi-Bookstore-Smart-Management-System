package com.example.database1;

public class BranchEmp {
    private String ssn;
    private String branchAddress;

    public BranchEmp() {}

    public BranchEmp(String ssn, String branchAddress) {
        this.ssn = ssn;
        this.branchAddress = branchAddress;
    }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getBranchAddress() { return branchAddress; }
    public void setBranchAddress(String branchAddress) { this.branchAddress = branchAddress; }

    @Override
    public String toString() {
        return "BranchEmp{" +
                "ssn='" + ssn + '\'' +
                ", branchAddress='" + branchAddress + '\'' +
                '}';
    }
}
