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

public class DeliveryEmpView {

    private final TableView<DeliveryEmp> table = new TableView<>();
    private final ObservableList<DeliveryEmp> deliveryEmpList = FXCollections.observableArrayList();

    private final TextField ssnField = new TextField();

    private final DeliveryEmpDAO dao = new DeliveryEmpDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<DeliveryEmp, String> ssnCol = new TableColumn<>("SSN");
        ssnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSsn()));

        table.getColumns().add(ssnCol);
        table.setItems(deliveryEmpList);

        ssnField.setPromptText("SSN");

        HBox inputBox = new HBox(10, ssnField);

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
            deliveryEmpList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load delivery employees", e);
        }
    }

    private void handleAdd() {
        try {
            DeliveryEmp emp = new DeliveryEmp(ssnField.getText());
            dao.insert(emp);
            clearFields();
            loadData();
        } catch (SQLException e) {
            showError("Failed to add delivery employee", e);
        }
    }

    private void handleDelete() {
        DeliveryEmp selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getSsn());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete delivery employee", e);
        }
    }

    private void clearFields() {
        ssnField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}
