package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DrivesDAO {

    public List<Drives> getAll() throws SQLException {
        List<Drives> list = new ArrayList<>();
        String sql = "SELECT * FROM Drives";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Drives d = new Drives(
                        rs.getString("SSN"),
                        rs.getString("Plate_num"),
                        rs.getInt("OrderID")
                );
                list.add(d);
            }
        }

        return list;
    }

    public void insert(Drives d) throws SQLException {
        String sql = "INSERT INTO Drives (SSN, Plate_num, OrderID) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, d.getSsn());
            pstmt.setString(2, d.getPlateNum());
            pstmt.setInt(3, d.getOrderId());
            pstmt.executeUpdate();
        }
    }

    public void delete(String ssn, String plateNum, int orderId) throws SQLException {
        String sql = "DELETE FROM Drives WHERE SSN = ? AND Plate_num = ? AND OrderID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ssn);
            pstmt.setString(2, plateNum);
            pstmt.setInt(3, orderId);
            pstmt.executeUpdate();
        }
    }
}
