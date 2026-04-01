
CREATE TABLE Employee (
    SSN CHAR(9) PRIMARY KEY,
    Name VARCHAR(100),
    Address VARCHAR(200),
    Phone VARCHAR(20),
    B_Date DATE,
    H_Date DATE,
    Supervise_SSN CHAR(9),
    FOREIGN KEY (Supervise_SSN) REFERENCES Employee(SSN)
);

CREATE TABLE Dependents (
    SSN CHAR(9),
    Name VARCHAR(100),
    Relationship VARCHAR(50),
    PRIMARY KEY (SSN, Name),
    FOREIGN KEY (SSN) REFERENCES Employee(SSN)
);

CREATE TABLE Branch (
    Address VARCHAR(200) PRIMARY KEY,
    Income DECIMAL(12, 2),
    Landline VARCHAR(20)
);


CREATE TABLE Branch_Emp (
    SSN CHAR(9),
    Branch_Address VARCHAR(200),
    PRIMARY KEY (SSN, Branch_Address),
    FOREIGN KEY (SSN) REFERENCES Employee(SSN),
    FOREIGN KEY (Branch_Address) REFERENCES Branch(Address)
);


CREATE TABLE Warehouse (
    Address VARCHAR(200) PRIMARY KEY,
    Landline VARCHAR(20),
    Space INT,
    Storage VARCHAR(100)
);


CREATE TABLE Warehouse_Emp (
    SSN CHAR(9),
    WarehouseAddress VARCHAR(200),
    PRIMARY KEY (SSN, WarehouseAddress),
    FOREIGN KEY (SSN) REFERENCES Employee(SSN),
    FOREIGN KEY (WarehouseAddress) REFERENCES Warehouse(Address)
);


CREATE TABLE Delivery_Emp (
    SSN CHAR(9) PRIMARY KEY,
    FOREIGN KEY (SSN) REFERENCES Employee(SSN)
);


CREATE TABLE Car (
    Plate_num VARCHAR(20) PRIMARY KEY,
    Purchase_Date DATE,
    Address VARCHAR(200),
    FOREIGN KEY (Address) REFERENCES Warehouse(Address)
);


CREATE TABLE Drives (
    SSN CHAR(9),
    Plate_num VARCHAR(20),
    OrderID INT,
    PRIMARY KEY (SSN, Plate_num, OrderID),
    FOREIGN KEY (SSN) REFERENCES Delivery_Emp(SSN),
    FOREIGN KEY (Plate_num) REFERENCES Car(Plate_num)
);


CREATE TABLE Customer (
    Address VARCHAR(200) PRIMARY KEY,
    Name VARCHAR(100),
    Phone VARCHAR(20)
);


CREATE TABLE Buy (
    Customer_Address VARCHAR(200),
    Branch_Address VARCHAR(200),
    Bill DECIMAL(10, 2),
    PRIMARY KEY (Customer_Address, Branch_Address),
    FOREIGN KEY (Customer_Address) REFERENCES Customer(Address),
    FOREIGN KEY (Branch_Address) REFERENCES Branch(Address)
);


CREATE TABLE Supplier (
    Name VARCHAR(100),
    SSN CHAR(9),
    Company VARCHAR(100),
    Address VARCHAR(200),
    PRIMARY KEY (Name, SSN)
);


CREATE TABLE Product (
    Product_ID INT PRIMARY KEY,
    Name VARCHAR(100)
);


CREATE TABLE Provides (
    Warehouse_Address VARCHAR(200),
    Product_ID INT,
    PRIMARY KEY (Warehouse_Address, Product_ID),
    FOREIGN KEY (Warehouse_Address) REFERENCES Warehouse(Address),
    FOREIGN KEY (Product_ID) REFERENCES Product(Product_ID)
);


CREATE TABLE Supply (
    Supplier_Name VARCHAR(100),
    Supplier_SSN CHAR(9),
    Product_ID INT,
    PRIMARY KEY (Supplier_Name, Supplier_SSN, Product_ID),
    FOREIGN KEY (Supplier_Name, Supplier_SSN) REFERENCES Supplier(Name, SSN),
    FOREIGN KEY (Product_ID) REFERENCES Product(Product_ID)
);

