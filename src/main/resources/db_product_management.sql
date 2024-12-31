CREATE DATABASE IF NOT EXISTS product_management;

USE product_management;

DROP TABLE IF EXISTS products;

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    brand VARCHAR(255) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price FLOAT NOT NULL,
    description TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    image_file_name VARCHAR(255)
);

INSERT INTO products (name, brand, category, price, description, image_file_name)
VALUES
('MacBook Air', 'Apple', 'Laptop', 1199.99, 'The new MacBook Air powered by M2 chip.', 'macbook_air.jpg'),
('MacBook Pro', 'Apple', 'Laptop', 1999.99, 'The ultimate pro notebook with M2 chip.', 'macbook_pro.jpg'),
('iPhone 15 Pro', 'Apple', 'Phone', 999.99, 'iPhone 15 Pro with A17 Bionic chip and titanium design.', 'iphone_15_pro.jpg'),
('iPhone 15', 'Apple', 'Phone', 799.99, 'iPhone 15 with A16 Bionic chip.', 'iphone_15.jpg'),
('iPad Pro', 'Apple', 'Tablet', 1099.99, 'iPad Pro with M2 chip and 12.9-inch Liquid Retina display.', 'ipad_pro.jpg'),
('iPad Air', 'Apple', 'Tablet', 599.99, 'iPad Air powered by M1 chip.', 'ipad_air.jpg'),
('Mac Mini', 'Apple', 'Laptop', 599.99, 'Compact desktop powered by M2 chip.', 'mac_mini.jpg'),
('Mac Studio', 'Apple', 'Laptop', 1999.99, 'High-performance desktop for professionals.', 'mac_studio.jpg'),
('Samsung Galaxy S24 Ultra', 'Samsung', 'Phone', 1199.99, 'The latest Samsung Galaxy S24 Ultra with a 200MP camera and powerful chipset.', 'samsung_galaxy_s24_ultra.jpg'),
('Samsung Galaxy S24', 'Samsung', 'Phone', 799.99, 'Samsung Galaxy S24 with a sleek design and high performance.', 'samsung_galaxy_s24.jpg'),
('Samsung Galaxy Z Fold 5', 'Samsung', 'Phone', 1799.99, 'Samsung Galaxy Z Fold 5 with a foldable screen and 5G support.', 'samsung_galaxy_z_fold_5.jpg'),
('Samsung Galaxy Z Flip 5', 'Samsung', 'Phone', 999.99, 'Samsung Galaxy Z Flip 5 with compact design and foldable screen.', 'samsung_galaxy_z_flip_5.jpg'),
('Samsung Galaxy Tab S9+', 'Samsung', 'Tablet', 899.99, 'Samsung Galaxy Tab S9+ with a 12.4-inch Super AMOLED display.', 'samsung_galaxy_tab_s9_plus.jpg'),
('Samsung Galaxy Tab S9', 'Samsung', 'Tablet', 749.99, 'Samsung Galaxy Tab S9 with 11-inch display and high-performance specs.', 'samsung_galaxy_tab_s9.jpg'),
('Samsung Galaxy Book 3 Ultra', 'Samsung', 'Laptop', 1599.99, 'Samsung Galaxy Book 3 Ultra with 13th Gen Intel processors and AMOLED display.', 'samsung_galaxy_book_3_ultra.jpg'),
('Samsung Galaxy Book 3 Pro', 'Samsung', 'Laptop', 1399.99, 'Samsung Galaxy Book 3 Pro with Intel i7 processor and UHD graphics.', 'samsung_galaxy_book_3_pro.jpg');

SELECT * FROM products;
