package com.example.database1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {


    public void insert(Product product) throws SQLException {
        String sql = "INSERT INTO Product (Product_ID, Name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, product.getProductId());
            stmt.setString(2, product.getName());
            stmt.executeUpdate();
        }
    }


    public Product getById(int id) throws SQLException {
        String sql = "SELECT * FROM Product WHERE Product_ID = ?";
        Product product = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product(
                        rs.getInt("Product_ID"),
                        rs.getString("Name")
                );
            }
        }

        return product;
    }


    public List<Product> getAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM Product";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("Product_ID"),
                        rs.getString("Name")
                );
                list.add(product);
            }
        }

        return list;
    }


    public void update(Product product) throws SQLException {
        String sql = "UPDATE Product SET Name = ? WHERE Product_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setInt(2, product.getProductId());
            stmt.executeUpdate();
        }
    }


    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Product WHERE Product_ID = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    public List<Product> getProductsByBranch(String branchAddress) throws SQLException {
        String sql = """
        SELECT p.Product_ID, p.Name
        FROM Product p
        JOIN Provides pr ON p.Product_ID = pr.Product_ID
        JOIN Warehouse w ON pr.Warehouse_Address = w.Address
        JOIN Warehouse_Emp we ON w.Address = we.WarehouseAddress
        JOIN Branch_Emp be ON we.SSN = be.SSN
        WHERE be.Branch_Address LIKE ?
        ORDER BY p.Name
        """;

        List<Product> products = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + branchAddress + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("Product_ID"));
                    p.setName(rs.getString("Name"));
                    products.add(p);
                }
            }
        }
        return products;
    }

    public List<Product> getProductsBySupplierAtBranch(String supplierName, String branchAddress) throws SQLException {
        String sql = """
        SELECT p.Product_ID, p.Name, w.Address AS WarehouseAddress
        FROM Product p
        JOIN Supply s ON p.Product_ID = s.Product_ID
        JOIN Supplier sup ON s.Supplier_Name = sup.Name AND s.Supplier_SSN = sup.SSN
        JOIN Provides pr ON p.Product_ID = pr.Product_ID
        JOIN Warehouse w ON pr.Warehouse_Address = w.Address
        JOIN Warehouse_Emp we ON w.Address = we.WarehouseAddress
        JOIN Branch_Emp be ON we.SSN = be.SSN
        WHERE sup.Name LIKE ? AND be.Branch_Address LIKE ?
        ORDER BY w.Address
        """;

        List<Product> products = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "%" + supplierName + "%");
            pstmt.setString(2, "%" + branchAddress + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("Product_ID"));
                    p.setName(rs.getString("Name"));

                    products.add(p);
                }
            }
        }
        return products;
    }


}
