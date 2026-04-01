package com.example.database1;

public class Employee {
        private String ssn;
        private String name;
        private String address;
        private String phone;
        private String bDate;
        private String hDate;
        private String superviseSSN;

        public Employee() {}

        public Employee(String ssn, String name, String address, String phone, String bDate, String hDate, String superviseSSN) {
            this.ssn = ssn;
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.bDate = bDate;
            this.hDate = hDate;
            this.superviseSSN = superviseSSN;
        }

        public String getSsn() { return ssn; }
        public void setSsn(String ssn) { this.ssn = ssn; }

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }

        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }

        public String getBDate() { return bDate; }
        public void setBDate(String bDate) { this.bDate = bDate; }

        public String getHDate() { return hDate; }
        public void setHDate(String hDate) { this.hDate = hDate; }

        public String getSuperviseSSN() { return superviseSSN; }
        public void setSuperviseSSN(String superviseSSN) { this.superviseSSN = superviseSSN; }

        @Override
        public String toString() {
            return "Employee{" +
                    "ssn='" + ssn + '\'' +
                    ", name='" + name + '\'' +
                    ", address='" + address + '\'' +
                    ", phone='" + phone + '\'' +
                    ", bDate='" + bDate + '\'' +
                    ", hDate='" + hDate + '\'' +
                    ", superviseSSN='" + superviseSSN + '\'' +
                    '}';
        }
    }

