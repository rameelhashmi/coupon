package com.coupon.controller;

import com.coupon.entity.Coupon;
import com.coupon.service.CouponService;
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

    // Redeem a new coupon (One-time & multi-time both)
    @GetMapping("/redeem/{couponCode}")
    public ResponseEntity<String> redeemCoupon(@PathVariable Integer couponCode) {
        String message = couponService.redeemCoupon(couponCode);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("delete/allcoupon")
    public ResponseEntity <String> deleteAllCoupon(){
        String result = couponService.deleteAllCoupon();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("delete/expired/onetimecoupon")
    public ResponseEntity <String> deleteOneTimeCoupon(){
        String result = couponService.deleteExpiredAndOneTimeRedeemCoupon();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("delete/expired/multitimecoupon")
    public ResponseEntity<String> deleteMultiTimeCoupon(){
        String result = couponService.deleteExpiredAndMultiTimeRedeemCoupon();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
