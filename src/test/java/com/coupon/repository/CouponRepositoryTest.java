package com.coupon.repository;

import com.coupon.entity.Coupon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    private Coupon testCoupon;

    @BeforeEach
    void setUp() {
        testCoupon = new Coupon();
        testCoupon.setTitle("Test Coupon");
        testCoupon.setDescription("Test Description");
        testCoupon.setCouponCode(12345);
        testCoupon.setCouponType("ONE_TIME");
        testCoupon.setStartDate(LocalDateTime.now());
        testCoupon.setExpirationDate(LocalDateTime.now().plusDays(5));
        testCoupon.setRedeemed(false);
        testCoupon.setRedeemCount(0);

        // Save the test coupon in the repository
        couponRepository.save(testCoupon);
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
        assertEquals("Test Coupon", foundCoupon.getTitle());
        assertEquals("Test Description", foundCoupon.getDescription());
    }

    @Test
    void testFindByCouponCode_NotFound() {
        Optional<Coupon> optionalCoupon = couponRepository.findByCouponCode(67890);
        assertFalse(optionalCoupon.isPresent(), "Coupon with code 67890 should not exist");
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
        couponRepository.delete(testCoupon);
        Optional<Coupon> optionalCoupon = couponRepository.findByCouponCode(12345);
        assertFalse(optionalCoupon.isPresent(), "Coupon should be deleted");
    }
}
