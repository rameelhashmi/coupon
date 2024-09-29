package com.coupon.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CouponTest {

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        coupon = new Coupon();
    }

    @Test
    void testSetAndGetId() {
        Long id = 1L;
        coupon.setId(id);
        assertEquals(id, coupon.getId());
    }

    @Test
    void testSetAndGetTitle() {
        String title = "Discount Coupon";
        coupon.setTitle(title);
        assertEquals(title, coupon.getTitle());
    }

    @Test
    void testSetAndGetDescription() {
        String description = "10% off on all items";
        coupon.setDescription(description);
        assertEquals(description, coupon.getDescription());
    }

    @Test
    void testSetAndGetStartDate() {
        LocalDateTime startDate = LocalDateTime.now();
        coupon.setStartDate(startDate);
        assertEquals(startDate, coupon.getStartDate());
    }

    @Test
    void testSetAndGetExpirationDate() {
        LocalDateTime expirationDate = LocalDateTime.now().plusDays(7);
        coupon.setExpirationDate(expirationDate);
        assertEquals(expirationDate, coupon.getExpirationDate());
    }

    @Test
    void testSetAndGetCouponType() {
        String couponType = "ONE_TIME";
        coupon.setCouponType(couponType);
        assertEquals(couponType, coupon.getCouponType());
    }

    @Test
    void testSetAndGetIsRedeemed() {
        coupon.setRedeemed(true);
        assertTrue(coupon.isRedeemed());

        coupon.setRedeemed(false);
        assertFalse(coupon.isRedeemed());
    }

    @Test
    void testSetAndGetRedeemCount() {
        Integer redeemCount = 5;
        coupon.setRedeemCount(redeemCount);
        assertEquals(redeemCount, coupon.getRedeemCount());
    }

    @Test
    void testSetAndGetCouponCode() {
        Integer couponCode = 12345;
        coupon.setCouponCode(couponCode);
        assertEquals(couponCode, coupon.getCouponCode());
    }
}
