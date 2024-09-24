package com.couponservice.demo.controller;

import com.couponservice.demo.entity.Coupon;
import com.couponservice.demo.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    // Get all coupons
    @GetMapping()
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    // Create a new coupon
    @PostMapping("/create")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon createdCoupon = couponService.saveCoupon(coupon);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    // Create a new coupon
    @GetMapping("/redeem/{couponCode}")
    public ResponseEntity<String> redeemCoupon(@PathVariable Integer couponCode) {
        String message = couponService.redeemCoupon(couponCode);
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }
}
