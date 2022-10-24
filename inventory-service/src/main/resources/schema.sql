DROP TABLE IF EXISTS products_availability;
CREATE TABLE products
(
    id                   VARCHAR(50) PRIMARY KEY,
    is_available         BOOLEAN NOT NULL
);
