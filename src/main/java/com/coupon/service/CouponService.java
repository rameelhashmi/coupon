package com.coupon.service;

import com.coupon.entity.Coupon;

import java.util.List;

public interface CouponService {
    Coupon saveCoupon(Coupon coupon);
    List<Coupon> getAllCoupons();
    String redeemCoupon(Integer couponCode);
}
