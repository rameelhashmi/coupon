package com.couponservice.demo.service;

import com.couponservice.demo.entity.Coupon;
import com.couponservice.demo.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    CouponRepository couponRepository;

    public Coupon saveCoupon(Coupon coupon){
        couponRepository.save(coupon);
        return coupon;
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    public String redeemCoupon(Integer couponCode){

        if (couponRepository.existsByCouponCode(couponCode)){

            Optional<Coupon> couponOptional = couponRepository.findByCouponCode(couponCode);
                Coupon coupon = couponOptional.get();

                if (coupon.getExpirationDate().isAfter(LocalDateTime.now())) {
                    // Check if the coupon type is MULTI_TIME
                    if ("MULTI_TIME".equals(coupon.getCouponType())) {
                        coupon.setRedeemed(true);
                        coupon.setRedeemCount(coupon.getRedeemCount() + 1);
                        couponRepository.save(coupon); // Update the coupon in the database
                       return "Multi Time Coupon has been redeemed with Coupon Code: "+coupon.getCouponCode();

                    } else if ("ONE_TIME".equals(coupon.getCouponType())) {
                        if (coupon.getRedeemCount().equals(0) && !coupon.isRedeemed())
                        {
                            coupon.setRedeemCount(1);
                            coupon.setRedeemed(true);
                            couponRepository.save(coupon);
                            return "One Time Coupon has been redeemed with Coupon Code: "+coupon.getCouponCode();
                        } else {
                            return "Coupon "+coupon.getCouponCode()+" has already redeemed once: " ;
                        }
                    }
                }

                else {
                    return "Coupon "+coupon.getCouponCode()+ "has already expired on: " +coupon.getExpirationDate();
                }
        }
        return " Wrong Coupon Code: "+ couponCode+ " Please try with correct code";
    }
}
