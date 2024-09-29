package com.coupon.demo.service;

import com.coupon.demo.entity.Coupon;
import com.coupon.demo.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Override
    public Coupon saveCoupon(Coupon coupon) {
        couponRepository.save(coupon);
        return coupon;
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public String redeemCoupon(Integer couponCode) {

        if (couponRepository.existsByCouponCode(couponCode)) {

            Optional<Coupon> couponDetails = couponRepository.findByCouponCode(couponCode);
            Coupon coupon = couponDetails.get();

            if (coupon.getExpirationDate().isAfter(LocalDateTime.now())) {
                switch (coupon.getCouponType()) {
                    case "MULTI_TIME":
                        return redeemMultiTimeCoupon(coupon);
                    case "ONE_TIME":
                        return redeemOneTimeCoupon(coupon);
                    default:
                        return "Invalid coupon type";
                }
            } else {
                return "Coupon: " + coupon.getCouponCode() + " has already expired on: " + coupon.getExpirationDate();
            }
        }
        return "Wrong Coupon Code: " + couponCode + " Please try with correct code";
    }

    private String redeemMultiTimeCoupon(Coupon coupon) {
        coupon.setRedeemed(true);
        coupon.setRedeemCount(coupon.getRedeemCount() + 1);
        couponRepository.save(coupon); // Update the coupon in the database
        return "Multi Time Coupon: " + coupon.getCouponCode() + " has been redeemed";
    }

    private String redeemOneTimeCoupon(Coupon coupon) {
        if (coupon.getRedeemCount().equals(0) && !coupon.isRedeemed()) {
            coupon.setRedeemCount(1);
            coupon.setRedeemed(true);
            couponRepository.save(coupon);
            return "One Time Coupon: " + coupon.getCouponCode() + " has been redeemed";
        } else {
            return "Coupon: " + coupon.getCouponCode() + " has already been redeemed once";
        }
    }
}
