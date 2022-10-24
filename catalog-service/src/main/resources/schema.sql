DROP TABLE IF EXISTS products;
CREATE TABLE products
(
    id                   VARCHAR(50) PRIMARY KEY,
    sku                  VARCHAR(50),
    name_title           VARCHAR(255),
    description          CLOB,
    list_price           NUMERIC,
    sale_price           NUMERIC,
    category             VARCHAR(100),
    category_tree        VARCHAR(255),
    product_url          CLOB,
    product_image_urls   CLOB,
    brand                VARCHAR(100),
    total_number_reviews INTEGER,
    reviews              CLOB
);