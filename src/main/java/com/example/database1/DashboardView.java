package com.example.database1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DashboardView extends Application {
    private final BorderPane root = new BorderPane();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Warehouse Management Dashboard");


        MenuBar menuBar = new MenuBar();


        Menu peopleMenu = new Menu("👥 People");
        MenuItem empItem = new MenuItem("👤 Employees");
        empItem.setOnAction(e -> root.setCenter(new EmployeeView().getView()));
        MenuItem deliveryEmpItem = new MenuItem("🚚 Delivery Employees");
        deliveryEmpItem.setOnAction(e -> root.setCenter(new DeliveryEmpView().getView()));
        MenuItem supplierItem = new MenuItem("🏭 Suppliers");
        supplierItem.setOnAction(e -> root.setCenter(new SupplierView().getView()));
        MenuItem customerItem = new MenuItem("🛒 Customers");
        customerItem.setOnAction(e -> root.setCenter(new CustomerView().getView()));
        peopleMenu.getItems().addAll(empItem, deliveryEmpItem, supplierItem, customerItem);


        Menu locationsMenu = new Menu("📍 Locations");
        MenuItem branchItem = new MenuItem("🏢 Branches");
        branchItem.setOnAction(e -> root.setCenter(new BranchView().getView()));
        MenuItem warehouseItem = new MenuItem("🏬 Warehouses");
        warehouseItem.setOnAction(e -> root.setCenter(new WarehouseView().getView()));
        locationsMenu.getItems().addAll(branchItem, warehouseItem);


        Menu inventoryMenu = new Menu("📦 Inventory");
        MenuItem productItem = new MenuItem("📦 Products");
        productItem.setOnAction(e -> root.setCenter(new ProductView().getView()));
        MenuItem providesItem = new MenuItem("📋 Provides");
        providesItem.setOnAction(e -> root.setCenter(new ProvidesView().getView()));
        MenuItem supplyItem = new MenuItem("🚚 Supply");
        supplyItem.setOnAction(e -> root.setCenter(new SupplyView().getView()));
        inventoryMenu.getItems().addAll(productItem, providesItem, supplyItem);


        Menu vehiclesMenu = new Menu("🚗 Vehicles");
        MenuItem carItem = new MenuItem("🚙 Cars");
        carItem.setOnAction(e -> root.setCenter(new CarView().getView()));
        MenuItem drivesItem = new MenuItem("🛣️ Drives");
        drivesItem.setOnAction(e -> root.setCenter(new DrivesView().getView()));
        vehiclesMenu.getItems().addAll(carItem, drivesItem);


        Menu transactionsMenu = new Menu("💰 Transactions");
        MenuItem buyItem = new MenuItem("🧾 Buys");
        buyItem.setOnAction(e -> root.setCenter(new BuyView().getView()));
        transactionsMenu.getItems().add(buyItem);


        Menu reportsMenu = new Menu("📊 Reports");

        MenuItem dashboardReportItem = new MenuItem("📈 Dashboard Summary");
        dashboardReportItem.setOnAction(e -> root.setCenter(new ReportView().getView()));

        MenuItem productsByBranchItem = new MenuItem("🏢 Products by Branch");
        productsByBranchItem.setOnAction(e -> root.setCenter(new ProductsByBranchView().getView()));

        MenuItem productsBySupplierBranchItem = new MenuItem("🔗 Products by Supplier & Branch");
        productsBySupplierBranchItem.setOnAction(e -> root.setCenter(new ProductsBySupplierAtBranchView().getView()));

        MenuItem productCountPerSupplierItem = new MenuItem("📦 Product Count Per Supplier");
        productCountPerSupplierItem.setOnAction(e -> root.setCenter(new ProductCountPerSupplierView().getView()));

        MenuItem productCountPerWarehouseItem = new MenuItem("🏬 Product Count Per Warehouse");
        productCountPerWarehouseItem.setOnAction(e -> root.setCenter(new ProductCountPerWarehouseView().getView()));

        MenuItem totalSalesByCustomerItem = new MenuItem("💰 Total Sales by Customer");
        totalSalesByCustomerItem.setOnAction(e -> root.setCenter(new TotalSalesByCustomerView().getView()));

        MenuItem activeDeliveriesItem = new MenuItem("🚚 Active Deliveries per Driver");
        activeDeliveriesItem.setOnAction(e -> root.setCenter(new ActiveDeliveriesView().getView()));

        reportsMenu.getItems().addAll(
                dashboardReportItem,
                productsByBranchItem,
                productsBySupplierBranchItem,
                productCountPerSupplierItem,
                productCountPerWarehouseItem,
                totalSalesByCustomerItem,
                activeDeliveriesItem
        );




        Menu exitMenu = new Menu("❌ Exit");
        MenuItem exitItem = new MenuItem("🚪 Exit App");
        exitItem.setOnAction(e -> primaryStage.close());
        exitMenu.getItems().add(exitItem);


        menuBar.getMenus().addAll(
                exitMenu,
                peopleMenu,
                locationsMenu,
                inventoryMenu,
                vehiclesMenu,
                transactionsMenu,
                reportsMenu

        );


        root.setTop(menuBar);


        VBox welcomeBox = new VBox(20);
        welcomeBox.setAlignment(Pos.CENTER);


        Image logo = new Image(
                "https://scontent.fjrs4-1.fna.fbcdn.net/v/t39.30808-6/295774549_7785327988175736_9037621036116501736_n.jpg?_nc_cat=104&ccb=1-7&_nc_sid=6ee11a&_nc_ohc=Fd_xGkUHTGQQ7kNvwEUbc-q&_nc_oc=Admy-fHKRnsaDGV-BHm4jmOZecIHo9N8IQ4KkWgasdNaeD8vhe46dZ7wBJjr5rkUmPU&_nc_zt=23&_nc_ht=scontent.fjrs4-1.fna&_nc_gid=TvaALNJQNZuPBqqafcqxgA&oh=00_AfOmrt6xGwPxg-Wlm6vOrgPtEh-rd-S2UaQyUsLx_-IDrQ&oe=684813CD",
                300, 300, true, true
        );
        ImageView logoView = new ImageView(logo);


        Text welcomeText = new Text("Welcome to the Al-Sarisi Warehouse Management Dashboard");
        welcomeText.setFont(Font.font("Arial", 28));


        welcomeBox.getChildren().addAll(logoView, welcomeText);


        root.setCenter(welcomeBox);


        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
