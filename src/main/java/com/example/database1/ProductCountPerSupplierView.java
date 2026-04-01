package com.example.database1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class ProductCountPerSupplierView {

    private final ReportDAO dao = new ReportDAO();
    private final TableView<SupplierProductCount> table = new TableView<>();
    private final ObservableList<SupplierProductCount> data = FXCollections.observableArrayList();

    public Parent getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        TextField branchField = new TextField();
        branchField.setPromptText("Enter Branch Address");

        Button searchBtn = new Button("Get Product Counts");

        searchBtn.setOnAction(e -> {
            String branch = branchField.getText().trim();
            if (branch.isEmpty()) {
                showAlert("Please enter a branch address.");
                return;
            }
            try {
                List<SupplierProductCount> result = dao.getProductCountPerSupplierAtBranch(branch);
                data.setAll(result);
            } catch (SQLException ex) {
                showAlert("Error fetching data: " + ex.getMessage());
            }
        });

        TableColumn<SupplierProductCount, String> supplierCol = new TableColumn<>("Supplier");
        supplierCol.setCellValueFactory(cellData -> javafx.beans.property.SimpleStringProperty.stringExpression(
                javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue().getSupplierName())
        ));

        TableColumn<SupplierProductCount, Integer> countCol = new TableColumn<>("Product Count");
        countCol.setCellValueFactory(cellData -> javafx.beans.property.SimpleIntegerProperty.integerExpression(
                javafx.beans.binding.Bindings.createIntegerBinding(() -> cellData.getValue().getProductCount())
        ).asObject());

        table.getColumns().addAll(supplierCol, countCol);
        table.setItems(data);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        root.getChildren().addAll(branchField, searchBtn, table);
        return root;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
