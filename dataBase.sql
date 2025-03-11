--create database FinalTest
--use FinalTest

CREATE TABLE Category (
  id INT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Supplier (
  id INT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  email VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE Product (
  id INT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  quantity INT NOT NULL,
  color ENUM('Đỏ', 'Xanh', 'Đen', 'Trắng', 'Vàng') NOT NULL,
  description TEXT,
  category_id INT,
  supplier_id INT,
  PRIMARY KEY (id),
  FOREIGN KEY (category_id) REFERENCES Category(id),
  FOREIGN KEY (supplier_id) REFERENCES Supplier(id)
);
ALTER TABLE Product ADD COLUMN status TINYINT(1) DEFAULT 1;


CREATE TABLE Customer (
  id INT AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  address VARCHAR(255) NOT NULL,
  phone VARCHAR(20) NOT NULL,
  email VARCHAR(100) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE OrderTable (
  id INT AUTO_INCREMENT,
  customer_id INT,
  order_date DATE NOT NULL,
  total DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id) REFERENCES Customer(id)
);

CREATE TABLE OrderDetail (
  id INT AUTO_INCREMENT,
  order_id INT,
  product_id INT,
  quantity INT NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES OrderTable(id),
  FOREIGN KEY (product_id) REFERENCES Product(id)
);





INSERT INTO Category (name) VALUES
('Phone'),
('Tivi'),
('Tủ lạnh'),
('Máy giặt');

INSERT INTO Supplier (name, address, phone, email) VALUES
('Apple', 'Cupertino, CA', '123-456-7890', 'info@apple.com'),
('Samsung', 'Seoul, Hàn Quốc', '098-765-4321', 'info@samsung.com'),
('Toshiba', 'Tokyo, Nhật Bản', '111-222-3333', 'info@toshiba.com');

INSERT INTO Product (name, price, quantity, color, description, category_id, supplier_id) VALUES
('iPhone 11', 799.00, 12, 'Đen', 'Sản phẩm điện thoại thông minh', 1, 1),
('iPhone 11 Pro', 1100.00, 12, 'Vàng', 'Sản phẩm điện thoại thông minh cao cấp', 1, 1),
('iPhone X', 749.00, 12, 'Xanh', 'Sản phẩm điện thoại thông minh', 1, 1),
('Smart Tivi 55 inch', 799.00, 12, 'Trắng', 'Sản phẩm tivi thông minh', 2, 2),
('Tủ lạnh 2 cánh Toshiba', 799.00, 12, 'Đỏ', 'Sản phẩm tủ lạnh', 3, 3),
('Máy giặt cửa ngang SamSung', 320.00, 12, 'Đen', 'Sản phẩm máy giặt', 4, 2);

INSERT INTO Customer (name, address, phone, email) VALUES
('Nguyễn Văn A', 'Hà Nội', '090-123-4567', 'nguyenvana@gmail.com'),
('Trần Thị B', 'Hồ Chí Minh', '098-765-4321', 'tranthib@gmail.com');

INSERT INTO OrderTable (customer_id, order_date, total) VALUES
(1, '2022-01-01', 1598.00),
(2, '2022-01-15', 799.00);

INSERT INTO OrderDetail (order_id, product_id, quantity, price) VALUES
(1, 1, 2, 799.00),
(1, 2, 1, 799.00),
(2, 4, 1, 799.00);