-- V4__Alter_coupon_code_to_integer.sql

-- Change coupon_code from varchar(50) to integer
ALTER TABLE coupons
ALTER COLUMN coupon_code TYPE integer USING coupon_code::integer;
