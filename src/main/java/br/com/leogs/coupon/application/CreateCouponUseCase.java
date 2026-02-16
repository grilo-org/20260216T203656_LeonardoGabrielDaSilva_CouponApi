package br.com.leogs.coupon.application;

import br.com.leogs.coupon.application.dto.CouponOutput;
import br.com.leogs.coupon.application.dto.CreateCouponCommand;
import br.com.leogs.coupon.domain.Coupon;
import br.com.leogs.coupon.domain.CouponRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateCouponUseCase {

	private final CouponRepository couponRepository;

	public CreateCouponUseCase(CouponRepository couponRepository) {
		this.couponRepository = couponRepository;
	}

	/**
	 * Realiza a criação de um cupom.
	 * @param commandDto
	 * @return
	 */
	public CouponOutput execute(CreateCouponCommand commandDto) {
        log.debug("Criando cupom com code: {}", commandDto.code());
		CouponOutput createdCupon = CouponOutput.from(couponRepository.save(
				new Coupon(
						commandDto.code(), 
						commandDto.description(),
						commandDto.discountValue(), 
						commandDto.expirationDate(), 
						commandDto.published())
				)
		);
        log.debug("Cupom de ID: {} criado com sucesso", createdCupon.id());
		return createdCupon;
	}
}
