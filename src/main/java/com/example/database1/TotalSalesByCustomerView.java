package com.example.database1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class TotalSalesByCustomerView {

    private final ReportDAO dao = new ReportDAO();
    private final TableView<CustomerSales> table = new TableView<>();
    private final ObservableList<CustomerSales> data = FXCollections.observableArrayList();

    public Parent getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Button loadBtn = new Button("Load Total Sales by Customer");
        loadBtn.setOnAction(e -> {
            try {
                List<CustomerSales> sales = dao.getTotalSalesByCustomer();
                data.setAll(sales);
            } catch (SQLException ex) {
                showAlert("Error loading sales: " + ex.getMessage());
            }
        });

        TableColumn<CustomerSales, String> nameCol = new TableColumn<>("Customer Name");
        nameCol.setCellValueFactory(cellData -> javafx.beans.property.SimpleStringProperty.stringExpression(
                javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue().getCustomerName())
        ));

        TableColumn<CustomerSales, String> totalCol = new TableColumn<>("Total Spent");
        totalCol.setCellValueFactory(cellData -> javafx.beans.property.SimpleStringProperty.stringExpression(
                javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue().getTotalSpent().toPlainString())
        ));

        table.getColumns().addAll(nameCol, totalCol);
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root.getChildren().addAll(loadBtn, table);
        return root;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
