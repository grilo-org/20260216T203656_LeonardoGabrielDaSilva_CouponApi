package br.com.leogs.coupon.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.leogs.coupon.application.dto.CouponOutput;
import br.com.leogs.coupon.domain.Coupon;
import br.com.leogs.coupon.domain.CouponRepository;
import br.com.leogs.coupon.domain.exception.CouponNotFoundException;

class GetCouponUseCaseTest {

	private CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
	private GetCouponUseCase getCouponUseCase = new GetCouponUseCase(couponRepository);

	@Test
	void testGetCouponSuccessfully() {
        Coupon coupon = new Coupon("ABC123", "Lorem", new BigDecimal("1.0"),
                Instant.now().plus(30, ChronoUnit.DAYS), false);

        Mockito.when(couponRepository.findById(coupon.getId()))
               .thenReturn(Optional.of(coupon));

        CouponOutput output = getCouponUseCase.execute(coupon.getId());

        assertEquals(coupon.getCode(), output.code());
        assertEquals(coupon.getDescription(), output.description());
        assertEquals(coupon.getStatus(), output.status());
	}

	@Test
	void testThrowWhenCouponNotFound() {
		Mockito.when(couponRepository.findById("id-inexistente")).thenReturn(Optional.empty());
		assertThrows(CouponNotFoundException.class, () -> getCouponUseCase.execute("id-inexistente"));
	}

}
