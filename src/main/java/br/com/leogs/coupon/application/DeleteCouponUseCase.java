package br.com.leogs.coupon.application;

import br.com.leogs.coupon.domain.Coupon;
import br.com.leogs.coupon.domain.CouponRepository;
import br.com.leogs.coupon.domain.exception.CouponNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DeleteCouponUseCase {

	private final CouponRepository couponRepository;

	public DeleteCouponUseCase(CouponRepository couponRepository) {
		this.couponRepository = couponRepository;
	}

	/**
	 * Realiza o delete de um cupom.
	 * @param commandDto
	 * @return
	 */
	public void execute(String couponId) {
        log.debug("Deletando cupom com ID: {}", couponId);
		Coupon coupon = couponRepository.findById(couponId).orElseThrow(CouponNotFoundException::new);
		coupon.delete();
        log.debug("Cupom com ID: {} deletado com sucesso!", couponId);
		couponRepository.save(coupon);
	}
}
