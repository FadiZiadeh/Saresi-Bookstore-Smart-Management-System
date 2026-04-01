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

public class BranchView {

    private final TableView<Branch> table = new TableView<>();
    private final ObservableList<Branch> branchList = FXCollections.observableArrayList();

    private final TextField addressField = new TextField();
    private final TextField incomeField = new TextField();
    private final TextField landlineField = new TextField();

    private final BranchDAO dao = new BranchDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Branch, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));

        TableColumn<Branch, String> incomeCol = new TableColumn<>("Income");
        incomeCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getIncome().toString()));

        TableColumn<Branch, String> landlineCol = new TableColumn<>("Landline");
        landlineCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLandline()));

        table.getColumns().addAll(addressCol, incomeCol, landlineCol);
        table.setItems(branchList);

        addressField.setPromptText("Address");
        incomeField.setPromptText("Income");
        landlineField.setPromptText("Landline");

        HBox inputBox = new HBox(10, addressField, incomeField, landlineField);

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
            branchList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load branches", e);
        }
    }

    private void handleAdd() {
        try {
            Branch branch = new Branch(
                    addressField.getText(),
                    new java.math.BigDecimal(incomeField.getText()),
                    landlineField.getText()
            );
            dao.insert(branch);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add branch", e);
        }
    }

    private void handleUpdate() {
        Branch selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            selected.setIncome(new java.math.BigDecimal(incomeField.getText()));
            selected.setLandline(landlineField.getText());
            dao.update(selected);
            loadData();
        } catch (SQLException e) {
            showError("Failed to update branch", e);
        }
    }

    private void handleDelete() {
        Branch selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getAddress());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete branch", e);
        }
    }

    private void clearFields() {
        addressField.clear();
        incomeField.clear();
        landlineField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
