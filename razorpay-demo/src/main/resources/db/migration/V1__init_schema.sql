CREATE TABLE payments(
    id BIGSERIAL PRIMARY KEY,
    order_id VARCHAR(100) NOT NULL UNIQUE,
    payment_id VARCHAR(100),
    signature VARCHAR(255),
    amount BIGINT NOT NULL CHECK ( amount > 0 ),
    currency VARCHAR(10) NOT NULL,
    status VARCHAR(20) NOT NULL,
    receipt VARCHAR(150),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP
);
