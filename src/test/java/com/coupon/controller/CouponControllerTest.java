package com.coupon.controller;

import com.coupon.entity.Coupon;
import com.coupon.service.CouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CouponController.class)
public class CouponControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    @InjectMocks
    private CouponController couponController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(couponController).build();
    }

    @Test
    public void testGetAllCoupons() throws Exception {
        // Given
        Coupon coupon1 = new Coupon();
        coupon1.setCouponCode(123);
        coupon1.setCouponType("ONE_TIME");

        Coupon coupon2 = new Coupon();
        coupon2.setCouponCode(456);
        coupon2.setCouponType("MULTI_TIME");

        List<Coupon> couponList = Arrays.asList(coupon1, coupon2);

        // When
        when(couponService.getAllCoupons()).thenReturn(couponList);

        // Then
        mockMvc.perform(get("/coupons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].couponCode", is(123)))
                .andExpect(jsonPath("$[0].couponType", is("ONE_TIME")))
                .andExpect(jsonPath("$[1].couponCode", is(456)))
                .andExpect(jsonPath("$[1].couponType", is("MULTI_TIME")));
    }

    @Test
    public void testCreateCoupon() throws Exception {
        // Given
        Coupon newCoupon = new Coupon();
        newCoupon.setCouponCode(789);
        newCoupon.setCouponType("ONE_TIME");

        // When
        when(couponService.saveCoupon(any(Coupon.class))).thenReturn(newCoupon);

        // Then
        mockMvc.perform(post("/coupons/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"couponCode\":789,\"couponType\":\"ONE_TIME\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.couponCode").value(789))
                .andExpect(jsonPath("$.couponType").value("ONE_TIME"));
    }


    @Test
    public void testRedeemCoupon() throws Exception {
        // Given
        String successMessage = "One Time Coupon: 789 has been redeemed";

        // When
        when(couponService.redeemCoupon(789)).thenReturn(successMessage);

        // Then
        mockMvc.perform(get("/coupons/redeem/789"))
                .andExpect(status().isCreated())
                .andExpect(content().string(successMessage));
    }
}
