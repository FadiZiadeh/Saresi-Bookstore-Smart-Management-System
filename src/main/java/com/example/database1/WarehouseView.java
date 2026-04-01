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

public class WarehouseView {

    private final TableView<Warehouse> table = new TableView<>();
    private final ObservableList<Warehouse> warehouseList = FXCollections.observableArrayList();

    private final TextField addressField = new TextField();
    private final TextField landlineField = new TextField();
    private final TextField spaceField = new TextField();
    private final TextField storageField = new TextField();

    private final WarehouseDAO dao = new WarehouseDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Warehouse, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));

        TableColumn<Warehouse, String> landlineCol = new TableColumn<>("Landline");
        landlineCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLandline()));

        TableColumn<Warehouse, String> spaceCol = new TableColumn<>("Space");
        spaceCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getSpace())));

        TableColumn<Warehouse, String> storageCol = new TableColumn<>("Storage");
        storageCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStorage()));

        table.getColumns().addAll(addressCol, landlineCol, spaceCol, storageCol);
        table.setItems(warehouseList);

        addressField.setPromptText("Address");
        landlineField.setPromptText("Landline");
        spaceField.setPromptText("Space");
        storageField.setPromptText("Storage");

        HBox inputBox = new HBox(10, addressField, landlineField, spaceField, storageField);

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
            warehouseList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load warehouses", e);
        }
    }

    private void handleAdd() {
        try {
            Warehouse warehouse = new Warehouse(
                    addressField.getText(),
                    landlineField.getText(),
                    Integer.parseInt(spaceField.getText()),
                    storageField.getText()
            );
            dao.insert(warehouse);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add warehouse", e);
        }
    }

    private void handleUpdate() {
        Warehouse selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            selected.setLandline(landlineField.getText());
            selected.setSpace(Integer.parseInt(spaceField.getText()));
            selected.setStorage(storageField.getText());
            dao.update(selected);
            loadData();
        } catch (SQLException e) {
            showError("Failed to update warehouse", e);
        }
    }

    private void handleDelete() {
        Warehouse selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getAddress());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete warehouse", e);
        }
    }

    private void clearFields() {
        addressField.clear();
        landlineField.clear();
        spaceField.clear();
        storageField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
