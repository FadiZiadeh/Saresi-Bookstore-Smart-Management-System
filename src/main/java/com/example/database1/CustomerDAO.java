package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {


    public void insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO Customer (Address, Name, Phone) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getAddress());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getPhone());
            stmt.executeUpdate();
        }
    }


    public Customer getByAddress(String address) throws SQLException {
        String sql = "SELECT * FROM Customer WHERE Address = ?";
        Customer customer = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new Customer(
                        rs.getString("Address"),
                        rs.getString("Name"),
                        rs.getString("Phone")
                );
            }
        }

        return customer;
    }


    public List<Customer> getAll() throws SQLException {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getString("Address"),
                        rs.getString("Name"),
                        rs.getString("Phone")
                );
                list.add(customer);
            }
        }

        return list;
    }


    public void update(Customer customer) throws SQLException {
        String sql = "UPDATE Customer SET Name = ?, Phone = ? WHERE Address = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getPhone());
            stmt.setString(3, customer.getAddress());
            stmt.executeUpdate();
        }
    }


    public void delete(String address) throws SQLException {
        String sql = "DELETE FROM Customer WHERE Address = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            stmt.executeUpdate();
        }
    }
}
