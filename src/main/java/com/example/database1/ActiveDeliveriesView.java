package com.example.database1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.util.List;

public class ActiveDeliveriesView {
    private final TableView<DriverDeliveries> table = new TableView<>();
    private final ObservableList<DriverDeliveries> data = FXCollections.observableArrayList();
    private final ReportDAO dao = new ReportDAO();

    public Parent getView() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Button loadBtn = new Button("Load Active Deliveries");
        loadBtn.setOnAction(e -> {
            try {
                List<DriverDeliveries> result = dao.getActiveDeliveriesPerDriver();
                data.setAll(result);
            } catch (SQLException ex) {
                showAlert("Failed to load data: " + ex.getMessage());
            }
        });

        TableColumn<DriverDeliveries, String> ssnCol = new TableColumn<>("SSN");
        ssnCol.setCellValueFactory(d -> javafx.beans.property.SimpleStringProperty
                .stringExpression(javafx.beans.binding.Bindings.createStringBinding(() -> d.getValue().getSsn())));

        TableColumn<DriverDeliveries, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(d -> javafx.beans.property.SimpleStringProperty
                .stringExpression(javafx.beans.binding.Bindings.createStringBinding(() -> d.getValue().getName())));

        TableColumn<DriverDeliveries, String> countCol = new TableColumn<>("Active Deliveries");
        countCol.setCellValueFactory(d -> javafx.beans.property.SimpleStringProperty
                .stringExpression(javafx.beans.binding.Bindings.createStringBinding(() ->
                        String.valueOf(d.getValue().getDeliveryCount()))));

        table.getColumns().addAll(ssnCol, nameCol, countCol);
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

