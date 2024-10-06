package com.coupon.repository;
import com.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByCouponCode(Integer couponCode);
    Optional<Coupon> findByCouponCode(Integer couponCode);

    @Query("SELECT c FROM Coupon c WHERE c.expirationDate < :now AND c.couponType = :type")
    List<Coupon> findExpiredAndRedeemedCoupons(@Param("now") LocalDateTime now, @Param("type") String couponType);
}

