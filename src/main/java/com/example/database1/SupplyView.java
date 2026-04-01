package com.example.database1;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class SupplyView {

    private final TableView<Supply> table = new TableView<>();
    private final ObservableList<Supply> supplyList = FXCollections.observableArrayList();

    private final TextField supplierNameField = new TextField();
    private final TextField supplierSsnField = new TextField();
    private final TextField productIdField = new TextField();

    private final SupplyDAO dao = new SupplyDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Supply, String> nameCol = new TableColumn<>("Supplier Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSupplierName()));

        TableColumn<Supply, String> ssnCol = new TableColumn<>("Supplier SSN");
        ssnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSupplierSsn()));

        TableColumn<Supply, String> productCol = new TableColumn<>("Product ID");
        productCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getProductId())));

        table.getColumns().addAll(nameCol, ssnCol, productCol);
        table.setItems(supplyList);

        supplierNameField.setPromptText("Supplier Name");
        supplierSsnField.setPromptText("Supplier SSN");
        productIdField.setPromptText("Product ID");

        HBox inputBox = new HBox(10, supplierNameField, supplierSsnField, productIdField);

        Button addBtn = new Button("➕ Add");
        addBtn.setOnAction(e -> handleAdd());

        Button deleteBtn = new Button("🗑️ Delete");
        deleteBtn.setOnAction(e -> handleDelete());

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> loadData());

        HBox buttonBox = new HBox(10, addBtn, deleteBtn, refreshBtn);

        VBox root = new VBox(10, table, inputBox, buttonBox);
        root.setPadding(new Insets(15));

        loadData();
        return root;
    }

    private void loadData() {
        try {
            supplyList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load supplies", e);
        }
    }

    private void handleAdd() {
        try {
            Supply supply = new Supply(
                    supplierNameField.getText(),
                    supplierSsnField.getText(),
                    Integer.parseInt(productIdField.getText())
            );
            dao.insert(supply);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add supply", e);
        }
    }

    private void handleDelete() {
        Supply selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getSupplierName(), selected.getSupplierSsn(), selected.getProductId());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete supply", e);
        }
    }

    private void clearFields() {
        supplierNameField.clear();
        supplierSsnField.clear();
        productIdField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}

