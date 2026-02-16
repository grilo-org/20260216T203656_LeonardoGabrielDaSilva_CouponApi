package br.com.leogs.coupon.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import br.com.leogs.coupon.domain.Coupon;
import br.com.leogs.coupon.domain.CouponRepository;
import br.com.leogs.coupon.domain.Status;
import br.com.leogs.coupon.domain.exception.CouponNotFoundException;

class DeleteCouponUseCaseTest {
	
    private CouponRepository couponRepository = Mockito.mock(CouponRepository.class);
    private DeleteCouponUseCase deleteCouponUseCase = new DeleteCouponUseCase(couponRepository);
    
    @Test
    void testDeleteCouponSuccessfully() {
        Coupon coupon = new Coupon("ABC123", "Lorem Ipsum", new BigDecimal("1.0"),
                Instant.now().plus(30, ChronoUnit.DAYS), false);

        Mockito.when(couponRepository.findById(coupon.getId()))
               .thenReturn(Optional.of(coupon));
        deleteCouponUseCase.execute(coupon.getId());
        Mockito.verify(couponRepository).save(coupon);
        assertEquals(Status.DELETED, coupon.getStatus());
    }

	@Test
	void testThrowWhenCouponNotFound() {
		Mockito.when(couponRepository.findById("id-inexistente")).thenReturn(Optional.empty());
		assertThrows(CouponNotFoundException.class, () -> deleteCouponUseCase.execute("id-inexistente"));
	}
}