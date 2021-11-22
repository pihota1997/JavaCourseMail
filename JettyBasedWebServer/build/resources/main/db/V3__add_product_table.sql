CREATE table products
(
    id           SERIAL PRIMARY KEY,
    name         varchar(100) NOT NULL UNIQUE,
    manufacturer varchar(100) NOT NULL,
    quantity     int          NOT NULL CHECK (quantity >= 0)
);