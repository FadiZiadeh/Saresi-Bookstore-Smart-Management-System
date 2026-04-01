package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplyDAO {

    public List<Supply> getAll() throws SQLException {
        List<Supply> list = new ArrayList<>();
        String sql = "SELECT * FROM Supply";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Supply supply = new Supply(
                        rs.getString("Supplier_Name"),
                        rs.getString("Supplier_SSN"),
                        rs.getInt("Product_ID")
                );
                list.add(supply);
            }
        }

        return list;
    }

    public void insert(Supply supply) throws SQLException {
        String sql = "INSERT INTO Supply (Supplier_Name, Supplier_SSN, Product_ID) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, supply.getSupplierName());
            pstmt.setString(2, supply.getSupplierSsn());
            pstmt.setInt(3, supply.getProductId());
            pstmt.executeUpdate();
        }
    }

    public void delete(String supplierName, String supplierSSN, int productId) throws SQLException {
        String sql = "DELETE FROM Supply WHERE Supplier_Name = ? AND Supplier_SSN = ? AND Product_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, supplierName);
            pstmt.setString(2, supplierSSN);
            pstmt.setInt(3, productId);
            pstmt.executeUpdate();
        }
    }
}

