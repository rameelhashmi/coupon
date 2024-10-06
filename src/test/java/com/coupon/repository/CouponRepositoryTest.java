package com.coupon.repository;

import com.coupon.entity.Coupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    private Coupon validCoupon;
    private Coupon expiredCoupon;

    @BeforeEach
    void setUp() {
        // Valid coupon that is not expired
        validCoupon = new Coupon();
        validCoupon.setTitle("Valid Coupon");
        validCoupon.setDescription("This coupon is valid");
        validCoupon.setCouponCode(12345);
        validCoupon.setCouponType("ONE_TIME");
        validCoupon.setStartDate(LocalDateTime.now());
        validCoupon.setExpirationDate(LocalDateTime.now().plusDays(5));
        validCoupon.setRedeemed(true);
        validCoupon.setRedeemCount(1);
        couponRepository.save(validCoupon);

        // Expired coupon that is redeemed
        expiredCoupon = new Coupon();
        expiredCoupon.setTitle("Expired Coupon");
        expiredCoupon.setDescription("This coupon has expired");
        expiredCoupon.setCouponCode(67890);
        expiredCoupon.setCouponType("ONE_TIME");
        expiredCoupon.setStartDate(LocalDateTime.now().minusDays(10)); // Start date is in the past
        expiredCoupon.setExpirationDate(LocalDateTime.now().minusDays(5)); // Expired
        expiredCoupon.setRedeemed(true);
        expiredCoupon.setRedeemCount(1);
        couponRepository.save(expiredCoupon);
    }

    @Test
    void testExistsByCouponCode() {
        boolean exists = couponRepository.existsByCouponCode(12345);
        assertTrue(exists, "Coupon with code 12345 should exist");
    }

    @Test
    void testFindByCouponCode() {
        Optional<Coupon> optionalCoupon = couponRepository.findByCouponCode(12345);
        assertTrue(optionalCoupon.isPresent(), "Coupon with code 12345 should be found");

        Coupon foundCoupon = optionalCoupon.get();
        assertEquals(12345, foundCoupon.getCouponCode());
        assertEquals("Valid Coupon", foundCoupon.getTitle());
        assertEquals("This coupon is valid", foundCoupon.getDescription());
    }

    @Test
    void testFindByCouponCode_NotFound() {
        Optional<Coupon> optionalCoupon = couponRepository.findByCouponCode(123212);
        assertFalse(optionalCoupon.isPresent(), "Coupon with code 123212 should not exist");
    }

    @Test
    void testSaveCoupon() {
        Coupon newCoupon = new Coupon();
        newCoupon.setTitle("New Coupon");
        newCoupon.setDescription("New Description");
        newCoupon.setCouponCode(54321);
        newCoupon.setCouponType("MULTI_TIME");
        newCoupon.setStartDate(LocalDateTime.now());
        newCoupon.setExpirationDate(LocalDateTime.now().plusDays(10));
        newCoupon.setRedeemed(false);
        newCoupon.setRedeemCount(0);

        Coupon savedCoupon = couponRepository.save(newCoupon);
        assertNotNull(savedCoupon.getId(), "Saved coupon should have an ID after saving");
        assertEquals("New Coupon", savedCoupon.getTitle());
        assertEquals(54321, savedCoupon.getCouponCode());
    }

    @Test
    void testDeleteCoupon() {
        couponRepository.delete(validCoupon);
        Optional<Coupon> optionalCoupon = couponRepository.findByCouponCode(12345);
        assertFalse(optionalCoupon.isPresent(), "Coupon should be deleted");
    }

    @Test
    void testFindExpiredAndRedeemedCoupons() {
        // Act
        List<Coupon> expiredAndRedeemedCoupons = couponRepository.findExpiredAndRedeemedCoupons(LocalDateTime.now(), "ONE_TIME");

        // Assert
        assertNotNull(expiredAndRedeemedCoupons, "The list should not be null");
        assertEquals(1, expiredAndRedeemedCoupons.size(), "There should be one expired and redeemed coupon");
        assertEquals(expiredCoupon.getCouponCode(), expiredAndRedeemedCoupons.get(0).getCouponCode(), "The coupon should match the expired coupon");
    }
}
