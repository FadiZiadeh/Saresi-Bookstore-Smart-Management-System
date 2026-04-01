package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BuyDAO {

    public List<Buy> getAll() throws SQLException {
        List<Buy> list = new ArrayList<>();
        String sql = "SELECT * FROM Buy";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Buy buy = new Buy(
                        rs.getString("Customer_Address"),
                        rs.getString("Branch_Address"),
                        rs.getBigDecimal("Bill")
                );
                list.add(buy);
            }
        }

        return list;
    }

    public void insert(Buy buy) throws SQLException {
        String sql = "INSERT INTO Buy (Customer_Address, Branch_Address, Bill) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, buy.getCustomerAddress());
            pstmt.setString(2, buy.getBranchAddress());
            pstmt.setBigDecimal(3, buy.getBill());
            pstmt.executeUpdate();
        }
    }

    public void update(Buy buy) throws SQLException {
        String sql = "UPDATE Buy SET Bill = ? WHERE Customer_Address = ? AND Branch_Address = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setBigDecimal(1, buy.getBill());
            pstmt.setString(2, buy.getCustomerAddress());
            pstmt.setString(3, buy.getBranchAddress());
            pstmt.executeUpdate();
        }
    }

    public void delete(String customerAddress, String branchAddress) throws SQLException {
        String sql = "DELETE FROM Buy WHERE Customer_Address = ? AND Branch_Address = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, customerAddress);
            pstmt.setString(2, branchAddress);
            pstmt.executeUpdate();
        }
    }
}
