BEGIN;

DROP TABLE IF EXISTS categories CASCADE;
CREATE TABLE categories (id bigserial PRIMARY KEY, title VARCHAR(255));
INSERT INTO categories (title) VALUES
('Food'),
('Not Food');

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (id bigserial PRIMARY KEY, title VARCHAR(255), price int, category_id bigint REFERENCES categories (id));
INSERT INTO products (title, price, category_id) VALUES
('Bread', 25, 1),
('Milk', 80, 1),
('Cat', 300, 2);


COMMIT;