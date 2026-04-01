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

public class DrivesView {

    private final TableView<Drives> table = new TableView<>();
    private final ObservableList<Drives> drivesList = FXCollections.observableArrayList();

    private final TextField ssnField = new TextField();
    private final TextField plateNumField = new TextField();
    private final TextField orderIdField = new TextField();

    private final DrivesDAO dao = new DrivesDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Drives, String> ssnCol = new TableColumn<>("SSN");
        ssnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSsn()));

        TableColumn<Drives, String> plateCol = new TableColumn<>("Plate Number");
        plateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPlateNum()));

        TableColumn<Drives, String> orderCol = new TableColumn<>("Order ID");
        orderCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getOrderId())));

        table.getColumns().addAll(ssnCol, plateCol, orderCol);
        table.setItems(drivesList);

        ssnField.setPromptText("SSN");
        plateNumField.setPromptText("Plate Number");
        orderIdField.setPromptText("Order ID");

        HBox inputBox = new HBox(10, ssnField, plateNumField, orderIdField);

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
            drivesList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load drives data", e);
        }
    }

    private void handleAdd() {
        try {
            Drives drive = new Drives(
                    ssnField.getText(),
                    plateNumField.getText(),
                    Integer.parseInt(orderIdField.getText())
            );
            dao.insert(drive);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add drives record", e);
        }
    }

    private void handleDelete() {
        Drives selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getSsn(), selected.getPlateNum(), selected.getOrderId());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete drives record", e);
        }
    }

    private void clearFields() {
        ssnField.clear();
        plateNumField.clear();
        orderIdField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}

