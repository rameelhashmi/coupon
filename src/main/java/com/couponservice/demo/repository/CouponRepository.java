package com.couponservice.demo.repository;
import com.couponservice.demo.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByCouponCode(Integer couponCode);
    Optional<Coupon> findByCouponCode(Integer couponCode);

}

