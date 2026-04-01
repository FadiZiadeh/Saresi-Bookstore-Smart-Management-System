package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {


    public void insert(Branch branch) throws SQLException {
        String sql = "INSERT INTO Branch (Address, Income, Landline) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, branch.getAddress());
            stmt.setBigDecimal(2, branch.getIncome());
            stmt.setString(3, branch.getLandline());
            stmt.executeUpdate();
        }
    }


    public Branch getByAddress(String address) throws SQLException {
        String sql = "SELECT * FROM Branch WHERE Address = ?";
        Branch branch = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                branch = new Branch(
                        rs.getString("Address"),
                        rs.getBigDecimal("Income"),
                        rs.getString("Landline")
                );
            }
        }

        return branch;
    }


    public List<Branch> getAll() throws SQLException {
        List<Branch> list = new ArrayList<>();
        String sql = "SELECT * FROM Branch";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Branch branch = new Branch(
                        rs.getString("Address"),
                        rs.getBigDecimal("Income"),
                        rs.getString("Landline")
                );
                list.add(branch);
            }
        }

        return list;
    }


    public void update(Branch branch) throws SQLException {
        String sql = "UPDATE Branch SET Income = ?, Landline = ? WHERE Address = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, branch.getIncome());
            stmt.setString(2, branch.getLandline());
            stmt.setString(3, branch.getAddress());
            stmt.executeUpdate();
        }
    }


    public void delete(String address) throws SQLException {
        String sql = "DELETE FROM Branch WHERE Address = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, address);
            stmt.executeUpdate();
        }
    }
}
