package com.coupon.demo.service;

import com.coupon.demo.entity.Coupon;

import java.util.List;

public interface CouponService {
    Coupon saveCoupon(Coupon coupon);
    List<Coupon> getAllCoupons();
    String redeemCoupon(Integer couponCode);
}
