package com.example.database1;

import com.example.database1.DBConnection;
import com.example.database1.Warehouse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO {


    public void insert(Warehouse warehouse) throws SQLException {
        String sql = "INSERT INTO Warehouse (Address, Landline, Space, Storage) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, warehouse.getAddress());
            stmt.setString(2, warehouse.getLandline());
            stmt.setInt(3, warehouse.getSpace());
            stmt.setString(4, warehouse.getStorage());
            stmt.executeUpdate();
        }
    }


    public Warehouse getByAddress(String address) throws SQLException {
        String sql = "SELECT * FROM Warehouse WHERE Address = ?";
        Warehouse warehouse = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                warehouse = new Warehouse(
                        rs.getString("Address"),
                        rs.getString("Landline"),
                        rs.getInt("Space"),
                        rs.getString("Storage")
                );
            }
        }

        return warehouse;
    }


    public List<Warehouse> getAll() throws SQLException {
        List<Warehouse> list = new ArrayList<>();
        String sql = "SELECT * FROM Warehouse";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Warehouse warehouse = new Warehouse(
                        rs.getString("Address"),
                        rs.getString("Landline"),
                        rs.getInt("Space"),
                        rs.getString("Storage")
                );
                list.add(warehouse);
            }
        }

        return list;
    }


    public void update(Warehouse warehouse) throws SQLException {
        String sql = "UPDATE Warehouse SET Landline = ?, Space = ?, Storage = ? WHERE Address = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, warehouse.getLandline());
            stmt.setInt(2, warehouse.getSpace());
            stmt.setString(3, warehouse.getStorage());
            stmt.setString(4, warehouse.getAddress());
            stmt.executeUpdate();
        }
    }


    public void delete(String address) throws SQLException {
        String sql = "DELETE FROM Warehouse WHERE Address = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            stmt.executeUpdate();
        }
    }
}
