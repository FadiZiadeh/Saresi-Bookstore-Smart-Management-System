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

public class SupplierView {

    private final TableView<Supplier> table = new TableView<>();
    private final ObservableList<Supplier> supplierList = FXCollections.observableArrayList();

    private final TextField nameField = new TextField();
    private final TextField ssnField = new TextField();
    private final TextField companyField = new TextField();
    private final TextField addressField = new TextField();

    private final SupplierDAO dao = new SupplierDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Supplier, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Supplier, String> ssnCol = new TableColumn<>("SSN");
        ssnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSsn()));

        TableColumn<Supplier, String> companyCol = new TableColumn<>("Company");
        companyCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCompany()));

        TableColumn<Supplier, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));

        table.getColumns().addAll(nameCol, ssnCol, companyCol, addressCol);
        table.setItems(supplierList);

        nameField.setPromptText("Name");
        ssnField.setPromptText("SSN");
        companyField.setPromptText("Company");
        addressField.setPromptText("Address");

        HBox inputBox = new HBox(10, nameField, ssnField, companyField, addressField);

        Button addBtn = new Button("➕ Add");
        addBtn.setOnAction(e -> handleAdd());

        Button updateBtn = new Button("✏️ Update");
        updateBtn.setOnAction(e -> handleUpdate());

        Button deleteBtn = new Button("🗑️ Delete");
        deleteBtn.setOnAction(e -> handleDelete());

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> loadData());

        HBox buttonBox = new HBox(10, addBtn, updateBtn, deleteBtn, refreshBtn);

        VBox root = new VBox(10, table, inputBox, buttonBox);
        root.setPadding(new Insets(15));

        loadData();
        return root;
    }

    private void loadData() {
        try {
            supplierList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load suppliers", e);
        }
    }

    private void handleAdd() {
        try {
            Supplier supplier = new Supplier(
                    nameField.getText(),
                    ssnField.getText(),
                    companyField.getText(),
                    addressField.getText()
            );
            dao.insert(supplier);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add supplier", e);
        }
    }

    private void handleUpdate() {
        Supplier selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            selected.setCompany(companyField.getText());
            selected.setAddress(addressField.getText());
            dao.update(selected);
            loadData();
        } catch (SQLException e) {
            showError("Failed to update supplier", e);
        }
    }

    private void handleDelete() {
        Supplier selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getName(), selected.getSsn());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete supplier", e);
        }
    }

    private void clearFields() {
        nameField.clear();
        ssnField.clear();
        companyField.clear();
        addressField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}

