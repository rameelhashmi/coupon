CREATE TABLE IF NOT EXISTS coupons (
    id              serial PRIMARY KEY,
    title           varchar(255) NOT NULL,
    description     text,
    start_date      timestamp,
    expiration_date timestamp,
    coupon_type     varchar(50) NOT NULL,
    is_redeemed     boolean DEFAULT false,
    redeem_count    integer DEFAULT 0,
    coupon_code     varchar(50) -- Change to varchar if coupon codes may include letters
);
