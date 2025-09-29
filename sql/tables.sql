CREATE TABLE products (
                          product_id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description TEXT,
                          unit_price NUMERIC(10,2) NOT NULL CHECK (unit_price >= 0)
);

CREATE TABLE inventory (
                           inventory_id SERIAL PRIMARY KEY,
                           product_id INT NOT NULL REFERENCES products(product_id) ON DELETE CASCADE,
                           quantity INT NOT NULL DEFAULT 0 CHECK (quantity >= 0)
);

CREATE TABLE transactions (
                              transaction_id SERIAL PRIMARY KEY,
                              product_id INT NOT NULL REFERENCES products(product_id) ON DELETE CASCADE,
                              transaction_type VARCHAR(10) NOT NULL CHECK (transaction_type IN ('IN', 'OUT')),
                              quantity INT NOT NULL CHECK (quantity > 0),
                              transaction_date TIMESTAMP NOT NULL DEFAULT NOW()
);

