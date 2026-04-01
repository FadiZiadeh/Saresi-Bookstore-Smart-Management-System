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

public class ProvidesView {

    private final TableView<Provides> table = new TableView<>();
    private final ObservableList<Provides> providesList = FXCollections.observableArrayList();

    private final TextField warehouseField = new TextField();
    private final TextField productIdField = new TextField();

    private final ProvidesDAO dao = new ProvidesDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Provides, String> warehouseCol = new TableColumn<>("Warehouse Address");
        warehouseCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getWarehouseAddress()));

        TableColumn<Provides, String> productIdCol = new TableColumn<>("Product ID");
        productIdCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getProductId())));

        table.getColumns().addAll(warehouseCol, productIdCol);
        table.setItems(providesList);

        warehouseField.setPromptText("Warehouse Address");
        productIdField.setPromptText("Product ID");

        HBox inputBox = new HBox(10, warehouseField, productIdField);

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
            providesList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load provides records", e);
        }
    }

    private void handleAdd() {
        try {
            Provides provides = new Provides(
                    warehouseField.getText(),
                    Integer.parseInt(productIdField.getText())
            );
            dao.insert(provides);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add provides record", e);
        }
    }

    private void handleDelete() {
        Provides selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getWarehouseAddress(), selected.getProductId());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete provides record", e);
        }
    }

    private void clearFields() {
        warehouseField.clear();
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

