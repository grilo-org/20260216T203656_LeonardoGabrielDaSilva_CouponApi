package br.com.leogs.coupon.domain;

import java.util.Optional;

public interface CouponRepository {
    Coupon save(Coupon coupon);
    Optional<Coupon> findById(String id);
}
