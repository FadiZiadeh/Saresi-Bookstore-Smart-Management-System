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

public class EmployeeView {

    private final TableView<Employee> table = new TableView<>();
    private final ObservableList<Employee> employeeList = FXCollections.observableArrayList();

    private final TextField ssnField = new TextField();
    private final TextField nameField = new TextField();
    private final TextField addressField = new TextField();
    private final TextField phoneField = new TextField();
    private final TextField bdateField = new TextField();
    private final TextField hdateField = new TextField();
    private final TextField superviseField = new TextField();

    private final EmployeeDAO dao = new EmployeeDAO();

    public Parent getView() {
        table.getColumns().clear();

        TableColumn<Employee, String> ssnCol = new TableColumn<>("SSN");
        ssnCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSsn()));

        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));

        TableColumn<Employee, String> addressCol = new TableColumn<>("Address");
        addressCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAddress()));

        TableColumn<Employee, String> phoneCol = new TableColumn<>("Phone");
        phoneCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPhone()));

        TableColumn<Employee, String> bdateCol = new TableColumn<>("Birth Date");
        bdateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getBDate()));

        TableColumn<Employee, String> hdateCol = new TableColumn<>("Hire Date");
        hdateCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getHDate()));

        TableColumn<Employee, String> superviseCol = new TableColumn<>("Supervisor SSN");
        superviseCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getSuperviseSSN()));

        table.getColumns().addAll(ssnCol, nameCol, addressCol, phoneCol, bdateCol, hdateCol, superviseCol);
        table.setItems(employeeList);

        ssnField.setPromptText("SSN");
        nameField.setPromptText("Name");
        addressField.setPromptText("Address");
        phoneField.setPromptText("Phone");
        bdateField.setPromptText("Birth Date (YYYY-MM-DD)");
        hdateField.setPromptText("Hire Date (YYYY-MM-DD)");
        superviseField.setPromptText("Supervisor SSN (optional)");

        HBox form1 = new HBox(10, ssnField, nameField, addressField, phoneField);
        HBox form2 = new HBox(10, bdateField, hdateField, superviseField);

        Button addBtn = new Button("➕ Add");
        addBtn.setOnAction(e -> handleAdd());

        Button updateBtn = new Button("✏️ Update");
        updateBtn.setOnAction(e -> handleUpdate());

        Button deleteBtn = new Button("🗑️ Delete");
        deleteBtn.setOnAction(e -> handleDelete());

        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> loadData());

        HBox buttons = new HBox(10, addBtn, updateBtn, deleteBtn, refreshBtn);

        VBox root = new VBox(10, table, form1, form2, buttons);
        root.setPadding(new Insets(15));

        loadData();
        return root;
    }

    private void loadData() {
        try {
            employeeList.setAll(dao.getAll());
        } catch (SQLException e) {
            showError("Error loading data", e);
        }
    }

    private void handleAdd() {
        try {
            Employee emp = new Employee(
                    ssnField.getText(),
                    nameField.getText(),
                    addressField.getText(),
                    phoneField.getText(),
                    bdateField.getText(),
                    hdateField.getText(),
                    superviseField.getText().isEmpty() ? null : superviseField.getText()
            );
            dao.insert(emp);
            clearFields();
            loadData();
        } catch (SQLException e) {
            showError("Error adding employee", e);
        }
    }

    private void handleUpdate() {
        Employee emp = table.getSelectionModel().getSelectedItem();
        if (emp == null) return;

        try {
            emp.setName(nameField.getText());
            emp.setAddress(addressField.getText());
            emp.setPhone(phoneField.getText());
            emp.setBDate(bdateField.getText());
            emp.setHDate(hdateField.getText());
            emp.setSuperviseSSN(superviseField.getText());
            dao.update(emp);
            loadData();
        } catch (SQLException e) {
            showError("Error updating employee", e);
        }
    }

    private void handleDelete() {
        Employee emp = table.getSelectionModel().getSelectedItem();
        if (emp == null) return;

        try {
            dao.delete(emp.getSsn());
            loadData();
        } catch (SQLException e) {
            showError("Error deleting employee", e);
        }
    }

    private void clearFields() {
        ssnField.clear();
        nameField.clear();
        addressField.clear();
        phoneField.clear();
        bdateField.clear();
        hdateField.clear();
        superviseField.clear();
    }

    private void showError(String msg, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Database Error");
        alert.setHeaderText(msg);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
}

