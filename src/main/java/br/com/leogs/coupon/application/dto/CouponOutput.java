package br.com.leogs.coupon.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

import br.com.leogs.coupon.domain.Coupon;
import br.com.leogs.coupon.domain.Status;

/**
 * DTO com todos os dados de saída do cupom.
 */
public record CouponOutput(String id, String code, String description, BigDecimal discountValue, Instant expirationDate,
		boolean published, boolean redeemed, Status status) {

	/**
	 * Realiza a criação do DTO através de um objeto Cupom.
	 * @param coupon
	 * @return
	 */
	 public static CouponOutput from(Coupon coupon) {
	        return new CouponOutput(
	            coupon.getId(),
	            coupon.getCode(),
	            coupon.getDescription(),
	            coupon.getDiscountValue(),
	            coupon.getExpirationDate(),
	            coupon.isPublished(),
	            coupon.isRedeemed(),
	            coupon.getStatus()
	        );
	    }
	 
}
