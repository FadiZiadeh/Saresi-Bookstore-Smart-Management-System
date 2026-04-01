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

public class CarView {

    private final TableView<Car> table = new TableView<>();
    private final ObservableList<Car> carList = FXCollections.observableArrayList();

    private final TextField plateField = new TextField();
    private final DatePicker datePicker = new DatePicker();
    private final TextField addressField = new TextField();

    private final CarDAO dao = new CarDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Car, String> plateCol = new TableColumn<>("Plate Number");
        plateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPlateNum()));

        TableColumn<Car, String> dateCol = new TableColumn<>("Purchase Date");
        dateCol.setCellValueFactory(data -> new SimpleStringProperty(
                data.getValue().getPurchaseDate().toString()
        ));

        TableColumn<Car, String> addressCol = new TableColumn<>("Warehouse Address");
        addressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));

        table.getColumns().addAll(plateCol, dateCol, addressCol);
        table.setItems(carList);

        plateField.setPromptText("Plate Number");
        datePicker.setPromptText("Purchase Date");
        addressField.setPromptText("Warehouse Address");

        HBox inputBox = new HBox(10, plateField, datePicker, addressField);

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
            carList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load cars", e);
        }
    }

    private void handleAdd() {
        try {
            Car car = new Car(
                    plateField.getText(),
                    datePicker.getValue(),
                    addressField.getText()
            );
            dao.insert(car);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add car", e);
        }
    }

    private void handleUpdate() {
        Car selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            selected.setPurchaseDate(datePicker.getValue());
            selected.setAddress(addressField.getText());
            dao.update(selected);
            loadData();
        } catch (SQLException e) {
            showError("Failed to update car", e);
        }
    }

    private void handleDelete() {
        Car selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getPlateNum());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete car", e);
        }
    }

    private void clearFields() {
        plateField.clear();
        datePicker.setValue(null);
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

