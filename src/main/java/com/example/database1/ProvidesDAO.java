package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProvidesDAO {

    public List<Provides> getAll() throws SQLException {
        List<Provides> list = new ArrayList<>();
        String sql = "SELECT * FROM Provides";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Provides provides = new Provides(
                        rs.getString("Warehouse_Address"),
                        rs.getInt("Product_ID")
                );
                list.add(provides);
            }
        }

        return list;
    }

    public void insert(Provides provides) throws SQLException {
        String sql = "INSERT INTO Provides (Warehouse_Address, Product_ID) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, provides.getWarehouseAddress());
            pstmt.setInt(2, provides.getProductId());
            pstmt.executeUpdate();
        }
    }

    public void delete(String warehouseAddress, int productId) throws SQLException {
        String sql = "DELETE FROM Provides WHERE Warehouse_Address = ? AND Product_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, warehouseAddress);
            pstmt.setInt(2, productId);
            pstmt.executeUpdate();
        }
    }
}
