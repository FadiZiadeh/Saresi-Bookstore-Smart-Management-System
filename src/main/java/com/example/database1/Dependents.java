package com.example.database1;

public class Dependents {
    private String ssn;
    private String name;
    private String relationship;

    public Dependents() {}

    public Dependents(String ssn, String name, String relationship) {
        this.ssn = ssn;
        this.name = name;
        this.relationship = relationship;
    }

    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRelationship() { return relationship; }
    public void setRelationship(String relationship) { this.relationship = relationship; }

    @Override
    public String toString() {
        return "Dependents{" +
                "ssn='" + ssn + '\'' +
                ", name='" + name + '\'' +
                ", relationship='" + relationship + '\'' +
                '}';
    }
}

