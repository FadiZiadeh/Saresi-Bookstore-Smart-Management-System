package com.example.database1;


import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportView {

    private final ReportDAO dao = new ReportDAO();

    public Parent getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        try {
            Label empCount = new Label("Total Employees: " + dao.getCount("Employee"));
            Label branchCount = new Label("Total Branches: " + dao.getCount("Branch"));
            Label warehouseCount = new Label("Total Warehouses: " + dao.getCount("Warehouse"));
            Label productCount = new Label("Total Products: " + dao.getCount("Product"));
            Label customerCount = new Label("Total Customers: " + dao.getCount("Customer"));
            Label carCount = new Label("Total Cars: " + dao.getCount("Car"));
            Label supplierCount = new Label("Total Suppliers: " + dao.getCount("Supplier"));

            Label buyCount = new Label("Total Buys: " + dao.getCount("Buy"));
            BigDecimal totalBills = dao.getSumOfBills();
            Label totalBillsLabel = new Label("Total Value of Bills: " + totalBills.toPlainString());

            Label deliveryEmpCount = new Label("Active Delivery Employees: " + dao.getActiveDeliveryEmpCount());

            root.getChildren().addAll(
                    empCount, branchCount, warehouseCount,
                    productCount, customerCount, carCount,
                    supplierCount, buyCount, totalBillsLabel,
                    deliveryEmpCount
            );

        } catch (SQLException e) {
            Label errorLabel = new Label("Failed to load report data: " + e.getMessage());
            root.getChildren().add(errorLabel);
        }

        return root;
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
}
