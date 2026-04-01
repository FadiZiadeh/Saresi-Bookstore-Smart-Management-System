package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {


    public void insert(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO Supplier (Name, SSN, Company, Address) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getSsn());
            stmt.setString(3, supplier.getCompany());
            stmt.setString(4, supplier.getAddress());
            stmt.executeUpdate();
        }
    }


    public Supplier getByKey(String name, String ssn) throws SQLException {
        String sql = "SELECT * FROM Supplier WHERE Name = ? AND SSN = ?";
        Supplier supplier = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, ssn);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                supplier = new Supplier(
                        rs.getString("Name"),
                        rs.getString("SSN"),
                        rs.getString("Company"),
                        rs.getString("Address")
                );
            }
        }

        return supplier;
    }


    public List<Supplier> getAll() throws SQLException {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM Supplier";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Supplier supplier = new Supplier(
                        rs.getString("Name"),
                        rs.getString("SSN"),
                        rs.getString("Company"),
                        rs.getString("Address")
                );
                list.add(supplier);
            }
        }

        return list;
    }


    public void update(Supplier supplier) throws SQLException {
        String sql = "UPDATE Supplier SET Company = ?, Address = ? WHERE Name = ? AND SSN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, supplier.getCompany());
            stmt.setString(2, supplier.getAddress());
            stmt.setString(3, supplier.getName());
            stmt.setString(4, supplier.getSsn());
            stmt.executeUpdate();
        }
    }


    public void delete(String name, String ssn) throws SQLException {
        String sql = "DELETE FROM Supplier WHERE Name = ? AND SSN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, ssn);
            stmt.executeUpdate();
        }
    }
}