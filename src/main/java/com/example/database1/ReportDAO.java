package com.example.database1;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReportDAO {

    public int getCount(String tableName) throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM " + tableName;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public BigDecimal getSumOfBills() throws SQLException {
        String sql = "SELECT SUM(Bill) AS total FROM Buy";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getBigDecimal("total") != null ? rs.getBigDecimal("total") : BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    public int getProductCountPerWarehouse(String warehouseAddress) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT Product_ID) AS total FROM Provides WHERE Warehouse_Address = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, warehouseAddress);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public int getProductCountPerSupplier(String supplierName, String supplierSSN) throws SQLException {
        String sql = "SELECT COUNT(DISTINCT Product_ID) AS total FROM Supply WHERE Supplier_Name = ? AND Supplier_SSN = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, supplierName);
            pstmt.setString(2, supplierSSN);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        }
        return 0;
    }

    public int getActiveDeliveryEmpCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM Delivery_Emp";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public int getCarCount() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM Car";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    public BigDecimal getTotalSales(LocalDate startDate, LocalDate endDate) throws SQLException {
        String sql = "SELECT SUM(Bill) AS TotalSales FROM Buy WHERE PurchaseDate BETWEEN ? AND ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("TotalSales") != null ? rs.getBigDecimal("TotalSales") : BigDecimal.ZERO;
                }
            }
        }
        return BigDecimal.ZERO;
    }

    public List<SupplierProductCount> getProductCountPerSupplierAtBranch(String branchAddress) throws SQLException {
        String sql = """
            SELECT sup.Name AS SupplierName, COUNT(DISTINCT p.Product_ID) AS ProductCount
            FROM Product p
            JOIN Supply s ON p.Product_ID = s.Product_ID
            JOIN Supplier sup ON s.Supplier_Name = sup.Name AND s.Supplier_SSN = sup.SSN
            JOIN Provides pr ON p.Product_ID = pr.Product_ID
            JOIN Warehouse w ON pr.Warehouse_Address = w.Address
            JOIN Warehouse_Emp we ON w.Address = we.WarehouseAddress
            JOIN Branch_Emp be ON we.SSN = be.SSN
            WHERE be.Branch_Address LIKE ?
            GROUP BY sup.Name
            ORDER BY sup.Name
            """;

        List<SupplierProductCount> results = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + branchAddress + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String supplierName = rs.getString("SupplierName");
                    int count = rs.getInt("ProductCount");
                    results.add(new SupplierProductCount(supplierName, count));
                }
            }
        }

        return results;
    }
    public List<WarehouseProductCount> getProductCountPerWarehouseAtBranch(String branchAddress) throws SQLException {
        String sql = """
            SELECT w.Address AS WarehouseAddress, COUNT(DISTINCT p.Product_ID) AS ProductCount
            FROM Product p
            JOIN Provides pr ON p.Product_ID = pr.Product_ID
            JOIN Warehouse w ON pr.Warehouse_Address = w.Address
            JOIN Warehouse_Emp we ON w.Address = we.WarehouseAddress
            JOIN Branch_Emp be ON we.SSN = be.SSN
            WHERE be.Branch_Address LIKE ?
            GROUP BY w.Address
            ORDER BY w.Address
            """;

        List<WarehouseProductCount> results = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + branchAddress + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String warehouseAddress = rs.getString("WarehouseAddress");
                    int count = rs.getInt("ProductCount");
                    results.add(new WarehouseProductCount(warehouseAddress, count));
                }
            }
        }

        return results;
    }
    public List<CustomerSales> getTotalSalesByCustomer() throws SQLException {
        String sql = """
        SELECT c.Name AS CustomerName, SUM(b.Bill) AS TotalSpent
        FROM Customer c
        JOIN Buy b ON c.Address = b.Customer_Address
        GROUP BY c.Name
        ORDER BY TotalSpent DESC
        """;

        List<CustomerSales> result = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String name = rs.getString("CustomerName");
                BigDecimal total = rs.getBigDecimal("TotalSpent");
                result.add(new CustomerSales(name, total));
            }
        }
        return result;
    }
    public List<DriverDeliveries> getActiveDeliveriesPerDriver() throws SQLException {
        String sql = """
        SELECT de.SSN, e.Name, COUNT(*) AS ActiveDeliveries
        FROM Delivery_Emp de
        JOIN Employee e ON de.SSN = e.SSN
        JOIN Drives d ON de.SSN = d.SSN
        GROUP BY de.SSN, e.Name
        ORDER BY ActiveDeliveries DESC
        """;

        List<DriverDeliveries> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String ssn = rs.getString("SSN");
                String name = rs.getString("Name");
                int count = rs.getInt("ActiveDeliveries");
                list.add(new DriverDeliveries(ssn, name, count));
            }
        }
        return list;
    }
}

