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

public class CustomerView {

    private final TableView<Customer> table = new TableView<>();
    private final ObservableList<Customer> customerList = FXCollections.observableArrayList();

    private final TextField addressField = new TextField();
    private final TextField nameField = new TextField();
    private final TextField phoneField = new TextField();

    private final CustomerDAO dao = new CustomerDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Customer, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));

        TableColumn<Customer, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Customer, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));

        table.getColumns().addAll(addressCol, nameCol, phoneCol);
        table.setItems(customerList);

        addressField.setPromptText("Address");
        nameField.setPromptText("Name");
        phoneField.setPromptText("Phone");

        HBox inputBox = new HBox(10, addressField, nameField, phoneField);

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
            customerList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load customers", e);
        }
    }

    private void handleAdd() {
        try {
            Customer customer = new Customer(
                    addressField.getText(),
                    nameField.getText(),
                    phoneField.getText()
            );
            dao.insert(customer);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add customer", e);
        }
    }

    private void handleUpdate() {
        Customer selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            selected.setName(nameField.getText());
            selected.setPhone(phoneField.getText());
            dao.update(selected);
            loadData();
        } catch (SQLException e) {
            showError("Failed to update customer", e);
        }
    }

    private void handleDelete() {
        Customer selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getAddress());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete customer", e);
        }
    }

    private void clearFields() {
        addressField.clear();
        nameField.clear();
        phoneField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}

