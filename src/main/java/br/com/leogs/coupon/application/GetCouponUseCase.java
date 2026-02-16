package br.com.leogs.coupon.application;

import br.com.leogs.coupon.application.dto.CouponOutput;
import br.com.leogs.coupon.domain.Coupon;
import br.com.leogs.coupon.domain.CouponRepository;
import br.com.leogs.coupon.domain.exception.CouponNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetCouponUseCase {

	private final CouponRepository couponRepository;

	public GetCouponUseCase(CouponRepository couponRepository) {
		this.couponRepository = couponRepository;
	}

	/**
	 * Realiza a busca de um cupom.
	 * @param commandDto
	 * @return
	 */
	public CouponOutput execute(String couponId) {
        log.debug("Recuperando cupom com ID: {}", couponId);
		Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);
        log.debug("Cupom com ID: {} recuperado com sucesso!", couponId);
		return CouponOutput.from(coupon);
	}
}
