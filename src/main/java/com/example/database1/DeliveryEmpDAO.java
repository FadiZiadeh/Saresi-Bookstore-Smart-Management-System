package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryEmpDAO {

    public List<DeliveryEmp> getAll() throws SQLException {
        List<DeliveryEmp> list = new ArrayList<>();
        String sql = "SELECT * FROM Delivery_Emp";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DeliveryEmp d = new DeliveryEmp(rs.getString("SSN"));
                list.add(d);
            }
        }

        return list;
    }

    public void insert(DeliveryEmp d) throws SQLException {
        String sql = "INSERT INTO Delivery_Emp (SSN) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, d.getSsn());
            pstmt.executeUpdate();
        }
    }

    public void delete(String ssn) throws SQLException {
        String sql = "DELETE FROM Delivery_Emp WHERE SSN = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ssn);
            pstmt.executeUpdate();
        }
    }
}

