package com.couponservice.demo.service;

import com.couponservice.demo.entity.Coupon;
import com.couponservice.demo.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CouponServiceImplTest {

    @InjectMocks
    private CouponServiceImpl couponService;

    @Mock
    private CouponRepository couponRepository;

    private Coupon testCoupon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testCoupon = new Coupon();
        testCoupon.setId(1L);
        testCoupon.setTitle("Test Coupon");
        testCoupon.setDescription("Test Description");
        testCoupon.setCouponCode(12345);
        testCoupon.setCouponType("ONE_TIME");
        testCoupon.setStartDate(LocalDateTime.now());
        testCoupon.setExpirationDate(LocalDateTime.now().plusDays(5));
        testCoupon.setRedeemed(false);
        testCoupon.setRedeemCount(0);
    }

    @Test
    void testSaveCoupon() {
        when(couponRepository.save(any(Coupon.class))).thenReturn(testCoupon);

        Coupon savedCoupon = couponService.saveCoupon(testCoupon);

        assertNotNull(savedCoupon);
        assertEquals("Test Coupon", savedCoupon.getTitle());
        verify(couponRepository, times(1)).save(testCoupon);
    }

    @Test
    void testGetAllCoupons() {
        List<Coupon> couponList = new ArrayList<>();
        couponList.add(testCoupon);

        when(couponRepository.findAll()).thenReturn(couponList);

        List<Coupon> result = couponService.getAllCoupons();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Coupon", result.get(0).getTitle());
        verify(couponRepository, times(1)).findAll();
    }

    @Test
    void testRedeemCoupon_SuccessOneTime() {
        when(couponRepository.existsByCouponCode(testCoupon.getCouponCode())).thenReturn(true);
        when(couponRepository.findByCouponCode(testCoupon.getCouponCode())).thenReturn(Optional.of(testCoupon));

        String result = couponService.redeemCoupon(testCoupon.getCouponCode());

        assertEquals("One Time Coupon: 12345 has been redeemed", result);
        assertTrue(testCoupon.isRedeemed());
        assertEquals(1, testCoupon.getRedeemCount());
        verify(couponRepository, times(1)).save(testCoupon);
    }

    @Test
    void testRedeemCoupon_AlreadyRedeemed() {
        testCoupon.setRedeemed(true);
        testCoupon.setRedeemCount(1);
        when(couponRepository.existsByCouponCode(testCoupon.getCouponCode())).thenReturn(true);
        when(couponRepository.findByCouponCode(testCoupon.getCouponCode())).thenReturn(Optional.of(testCoupon));

        String result = couponService.redeemCoupon(testCoupon.getCouponCode());

        assertEquals("Coupon: 12345 has already been redeemed once", result);
        verify(couponRepository, times(0)).save(any(Coupon.class)); // Ensure save is not called
    }

    @Test
    void testRedeemCoupon_ExpiredCoupon() {
        testCoupon.setExpirationDate(LocalDateTime.now().minusDays(1)); // Set as expired
        when(couponRepository.existsByCouponCode(testCoupon.getCouponCode())).thenReturn(true);
        when(couponRepository.findByCouponCode(testCoupon.getCouponCode())).thenReturn(Optional.of(testCoupon));

        String result = couponService.redeemCoupon(testCoupon.getCouponCode());

        assertEquals("Coupon: 12345 has already expired on: " + testCoupon.getExpirationDate(), result);
        verify(couponRepository, times(0)).save(any(Coupon.class)); // Ensure save is not called
    }

    @Test
    void testRedeemCoupon_InvalidCouponCode() {
        when(couponRepository.existsByCouponCode(testCoupon.getCouponCode())).thenReturn(false);

        String result = couponService.redeemCoupon(testCoupon.getCouponCode());

        assertEquals("Wrong Coupon Code: 12345 Please try with correct code", result);
        verify(couponRepository, times(0)).findByCouponCode(anyInt()); // Ensure find is not called
    }
}
