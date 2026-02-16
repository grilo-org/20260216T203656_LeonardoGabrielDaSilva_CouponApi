package br.com.leogs.coupon.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.leogs.coupon.application.dto.CouponOutput;
import br.com.leogs.coupon.application.dto.CreateCouponCommand;
import br.com.leogs.coupon.domain.Coupon;
import br.com.leogs.coupon.domain.CouponRepository;
import br.com.leogs.coupon.domain.exception.InvalidCouponException;

class CreateCouponUseCaseTest {

	private CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
	private CreateCouponUseCase createCouponUseCase = new CreateCouponUseCase(couponRepository);

	@Test
	void testCreateCouponSuccessfully() {
		Mockito.when(couponRepository.save(Mockito.any(Coupon.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		CreateCouponCommand command = new CreateCouponCommand("ABC123", "Lorem Ipsum", new BigDecimal("1.0"),
				Instant.now().plus(30, ChronoUnit.DAYS), false);

		CouponOutput output = createCouponUseCase.execute(command);
		Mockito.verify(couponRepository).save(Mockito.any(Coupon.class));
		assertEquals("ABC123", output.code());
	}

	@Test
	void testCreateCouponFailure() {
		CreateCouponCommand command = new CreateCouponCommand("AB", "Lorem Ipsum", new BigDecimal("1.0"),
				Instant.now().plus(30, ChronoUnit.DAYS), false);
		assertThrows(InvalidCouponException.class, () -> createCouponUseCase.execute(command));
	}

}
