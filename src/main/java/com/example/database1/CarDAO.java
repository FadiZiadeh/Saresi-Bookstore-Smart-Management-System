package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {


    public void insert(Car car) throws SQLException {
        String sql = "INSERT INTO Car (Plate_num, Purchase_Date, Address) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, car.getPlateNum());
            stmt.setDate(2, Date.valueOf(car.getPurchaseDate()));
            stmt.setString(3, car.getAddress());
            stmt.executeUpdate();
        }
    }


    public Car getByPlate(String plateNum) throws SQLException {
        String sql = "SELECT * FROM Car WHERE Plate_num = ?";
        Car car = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, plateNum);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                car = new Car(
                        rs.getString("Plate_num"),
                        rs.getDate("Purchase_Date").toLocalDate(),
                        rs.getString("Address")
                );
            }
        }

        return car;
    }


    public List<Car> getAll() throws SQLException {
        List<Car> list = new ArrayList<>();
        String sql = "SELECT * FROM Car";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Car car = new Car(
                        rs.getString("Plate_num"),
                        rs.getDate("Purchase_Date").toLocalDate(),
                        rs.getString("Address")
                );
                list.add(car);
            }
        }

        return list;
    }


    public void update(Car car) throws SQLException {
        String sql = "UPDATE Car SET Purchase_Date = ?, Address = ? WHERE Plate_num = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(car.getPurchaseDate()));
            stmt.setString(2, car.getAddress());
            stmt.setString(3, car.getPlateNum());
            stmt.executeUpdate();
        }
    }


    public void delete(String plateNum) throws SQLException {
        String sql = "DELETE FROM Car WHERE Plate_num = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, plateNum);
            stmt.executeUpdate();
        }
    }
}
