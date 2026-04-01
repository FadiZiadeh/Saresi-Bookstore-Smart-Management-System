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

public class ProductView {

    private final TableView<Product> table = new TableView<>();
    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    private final TextField idField = new TextField();
    private final TextField nameField = new TextField();

    private final ProductDAO dao = new ProductDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Product, String> idCol = new TableColumn<>("Product ID");
        idCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getProductId())));

        TableColumn<Product, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        table.getColumns().addAll(idCol, nameCol);
        table.setItems(productList);

        idField.setPromptText("Product ID");
        nameField.setPromptText("Name");

        HBox inputBox = new HBox(10, idField, nameField);

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
            productList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load products", e);
        }
    }

    private void handleAdd() {
        try {
            Product product = new Product(
                    Integer.parseInt(idField.getText()),
                    nameField.getText()
            );
            dao.insert(product);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add product", e);
        }
    }

    private void handleUpdate() {
        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            selected.setName(nameField.getText());
            dao.update(selected);
            loadData();
        } catch (SQLException e) {
            showError("Failed to update product", e);
        }
    }

    private void handleDelete() {
        Product selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getProductId());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete product", e);
        }
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}

