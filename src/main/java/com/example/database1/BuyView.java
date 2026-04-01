package com.example.database1;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.sql.SQLException;

public class BuyView {

    private final TableView<Buy> table = new TableView<>();
    private final ObservableList<Buy> buyList = FXCollections.observableArrayList();

    private final TextField customerAddressField = new TextField();
    private final TextField branchAddressField = new TextField();
    private final TextField billField = new TextField();

    private final BuyDAO dao = new BuyDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Buy, String> customerCol = new TableColumn<>("Customer Address");
        customerCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomerAddress()));

        TableColumn<Buy, String> branchCol = new TableColumn<>("Branch Address");
        branchCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBranchAddress()));

        TableColumn<Buy, String> billCol = new TableColumn<>("Bill");
        billCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBill().toPlainString()));

        table.getColumns().addAll(customerCol, branchCol, billCol);
        table.setItems(buyList);

        customerAddressField.setPromptText("Customer Address");
        branchAddressField.setPromptText("Branch Address");
        billField.setPromptText("Bill");

        HBox inputBox = new HBox(10, customerAddressField, branchAddressField, billField);

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
            buyList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Failed to load buys", e);
        }
    }

    private void handleAdd() {
        try {
            Buy buy = new Buy(
                    customerAddressField.getText(),
                    branchAddressField.getText(),
                    new BigDecimal(billField.getText())
            );
            dao.insert(buy);
            clearFields();
            loadData();
        } catch (Exception e) {
            showError("Failed to add buy", e);
        }
    }

    private void handleUpdate() {
        Buy selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            selected.setBill(new BigDecimal(billField.getText()));
            dao.update(selected);
            loadData();
        } catch (SQLException e) {
            showError("Failed to update buy", e);
        }
    }

    private void handleDelete() {
        Buy selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        try {
            dao.delete(selected.getCustomerAddress(), selected.getBranchAddress());
            loadData();
        } catch (SQLException e) {
            showError("Failed to delete buy", e);
        }
    }

    private void clearFields() {
        customerAddressField.clear();
        branchAddressField.clear();
        billField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}

