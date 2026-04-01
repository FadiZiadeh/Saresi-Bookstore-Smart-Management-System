package com.example.database1;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class ProductsBySupplierAtBranchView {

    private final ProductDAO dao = new ProductDAO();
    private final TableView<Product> table = new TableView<>();
    private final ObservableList<Product> products = FXCollections.observableArrayList();

    public Parent getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        TextField supplierField = new TextField();
        supplierField.setPromptText("Enter Supplier Name");

        TextField branchField = new TextField();
        branchField.setPromptText("Enter Branch Address");

        Button searchBtn = new Button("Search Products");

        searchBtn.setOnAction(e -> {
            String supplier = supplierField.getText().trim();
            String branch = branchField.getText().trim();

            if (supplier.isEmpty() || branch.isEmpty()) {
                showAlert("Please enter both supplier name and branch address.");
                return;
            }

            try {
                List<Product> result = dao.getProductsBySupplierAtBranch(supplier, branch);
                products.setAll(result);
            } catch (SQLException ex) {
                showAlert("Error loading products: " + ex.getMessage());
            }
        });

        TableColumn<Product, Integer> idCol = new TableColumn<>("Product ID");
        idCol.setCellValueFactory(data -> SimpleIntegerProperty
                .integerProperty(new SimpleIntegerProperty(data.getValue().getProductId()).asObject()).asObject());

        TableColumn<Product, String> nameCol = new TableColumn<>("Product Name");
        nameCol.setCellValueFactory(data -> SimpleStringProperty
                .stringExpression(new SimpleStringProperty(data.getValue().getName())));

        table.getColumns().addAll(idCol, nameCol);
        table.setItems(products);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root.getChildren().addAll(supplierField, branchField, searchBtn, table);
        return root;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}

