INSERT INTO products (name, description, unit_price) VALUES
                                                         ('Notebook', 'Notebook Dell Inspiron 15', 3500.00),
                                                         ('Mouse', 'Mouse sem fio Logitech', 120.00),
                                                         ('Teclado', 'Teclado mecânico Redragon', 250.00);

CALL AddToInventory(1, 50);
CALL AddToInventory(2, 100);
CALL AddToInventory(3, 80);

CALL SellProduct(1, 2);
CALL SellProduct(2, 5);

SELECT product_id, GetStockLevel(product_id) AS estoque_atual FROM products;

