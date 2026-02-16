package br.com.leogs.coupon.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import br.com.leogs.coupon.domain.exception.CouponAlreadyDeletedException;
import br.com.leogs.coupon.domain.exception.InvalidCouponException;

class CouponTest {

	@Test
	void testValidDataAndSanitizedCode() {
		Coupon coupon = new Coupon("ABC-123", "Lorem Ipsum", new BigDecimal("0.6"), Instant.now().plus(30, ChronoUnit.DAYS), false);
		assertEquals("ABC123", coupon.getCode());
	}

	@Test
	void testCompleteConstructor() {
		assertDoesNotThrow(() -> new Coupon("hash-id", "ABC-123", "Lorem Ipsum", new BigDecimal("0.6"),
				Instant.now().plus(30, ChronoUnit.DAYS), false, false, Status.ACTIVE));
	}

	@Test
	void testDelete() {
		Coupon coupon = new Coupon("ABC-123", "Lorem Ipsum", new BigDecimal("0.6"),
				Instant.now().plus(30, ChronoUnit.DAYS), false);
		coupon.delete();
		assertEquals(Status.DELETED, coupon.getStatus());
	}
	
	@Test
	void testThrowWhenDeleteAlreadyDeleted() {
		Coupon coupon = new Coupon("ABC-123", "Lorem Ipsum", new BigDecimal("0.6"),
				Instant.now().plus(30, ChronoUnit.DAYS), false);
		coupon.delete();
		assertThrows(CouponAlreadyDeletedException.class, () -> coupon.delete());
	}
	
	@Test
	void testMinimumDiscountValue() {
	    assertDoesNotThrow(() -> new Coupon("ABC123", "Lorem Ipsum", new BigDecimal("0.5"),
	            Instant.now().plus(30, ChronoUnit.DAYS), false));
	}
	
	@Test
	void testCreateWithPublishedTrue() {
	    Coupon coupon = new Coupon("ABC123", "Lorem Ipsum", new BigDecimal("0.6"),
	            Instant.now().plus(30, ChronoUnit.DAYS), true);
	    assertEquals(true, coupon.isPublished());
	}

	@ParameterizedTest
	@MethodSource
	void testThrowWhenInvalidData(String code, String description, BigDecimal discount, Instant expiration,
			String expectedMessage) {
		InvalidCouponException ex = assertThrows(InvalidCouponException.class,
				() -> new Coupon(code, description, discount, expiration, false));
		assertEquals(expectedMessage, ex.getMessage());
	}

	static Stream<Arguments> testThrowWhenInvalidData() {
		Instant validDate = Instant.now().plus(30, ChronoUnit.DAYS);
		BigDecimal validDiscount = new BigDecimal("1.0");

		return Stream.of(Arguments.of(null, "Lorem Ipsum", validDiscount, validDate, "Código do cupom não informado."),
				Arguments.of("", "Lorem Ipsum", validDiscount, validDate, "Código do cupom não informado."),
				Arguments.of("AB", "Lorem Ipsum", validDiscount, validDate,
						"Código do cupom deve conter exatamente 6 caracteres alfanuméricos."),
				Arguments.of("ABC123", null, validDiscount, validDate, "Descrição do cupom não informada."),
				Arguments.of("ABC123", "", validDiscount, validDate, "Descrição do cupom não informada."),
				Arguments.of("ABC123", "Lorem Ipsum", null, validDate, "Desconto do cupom não informado."),
				Arguments.of("ABC123", "Lorem Ipsum", new BigDecimal("0.4"), validDate,
						"Valor do desconto deve ser no mínimo 0.5."),
				Arguments.of("ABC123", "Lorem Ipsum", validDiscount, null, "Data de expiração do cupom não informada."),
				Arguments.of("ABC123", "Lorem Ipsum", validDiscount, Instant.now().minus(1, ChronoUnit.DAYS),
						"Data de expiração não pode estar no passado."));
	}

}
