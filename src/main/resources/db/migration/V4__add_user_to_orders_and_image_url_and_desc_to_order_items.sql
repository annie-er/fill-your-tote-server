-- Add user_id to orders
ALTER TABLE orders ADD COLUMN user_id BIGINT;
ALTER TABLE orders ADD CONSTRAINT fk_orders_user FOREIGN KEY (user_id) REFERENCES app_user(id);

-- Add new columns to order_items
ALTER TABLE order_items ADD COLUMN image_url VARCHAR(255);
ALTER TABLE order_items ADD COLUMN description VARCHAR(500);