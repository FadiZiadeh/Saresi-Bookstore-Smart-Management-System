package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {


    public void insert(Employee emp) throws SQLException {
        String sql = "INSERT INTO Employee (SSN, Name, Address, Phone, B_Date, H_Date, Supervise_SSN) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getSsn());
            stmt.setString(2, emp.getName());
            stmt.setString(3, emp.getAddress());
            stmt.setString(4, emp.getPhone());
            stmt.setString(5, emp.getBDate());
            stmt.setString(6, emp.getHDate());
            stmt.setString(7, emp.getSuperviseSSN());

            stmt.executeUpdate();
        }
    }


    public Employee getBySSN(String ssn) throws SQLException {
        String sql = "SELECT * FROM Employee WHERE SSN = ?";
        Employee emp = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ssn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                emp = new Employee(
                        rs.getString("SSN"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getString("B_Date"),
                        rs.getString("H_Date"),
                        rs.getString("Supervise_SSN")
                );
            }
        }

        return emp;
    }


    public List<Employee> getAll() throws SQLException {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM Employee";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee emp = new Employee(
                        rs.getString("SSN"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getString("B_Date"),
                        rs.getString("H_Date"),
                        rs.getString("Supervise_SSN")
                );
                list.add(emp);
            }
        }

        return list;
    }


    public void update(Employee emp) throws SQLException {
        String sql = "UPDATE Employee SET Name = ?, Address = ?, Phone = ?, B_Date = ?, H_Date = ?, Supervise_SSN = ? WHERE SSN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, emp.getName());
            stmt.setString(2, emp.getAddress());
            stmt.setString(3, emp.getPhone());
            stmt.setString(4, emp.getBDate());
            stmt.setString(5, emp.getHDate());
            stmt.setString(6, emp.getSuperviseSSN());
            stmt.setString(7, emp.getSsn());

            stmt.executeUpdate();
        }
    }


    public void delete(String ssn) throws SQLException {
        String sql = "DELETE FROM Employee WHERE SSN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ssn);
            stmt.executeUpdate();
        }
    }
}
