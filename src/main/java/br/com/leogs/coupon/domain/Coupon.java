package br.com.leogs.coupon.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import br.com.leogs.coupon.domain.exception.CouponAlreadyDeletedException;
import br.com.leogs.coupon.domain.exception.InvalidCouponException;
import lombok.Getter;

@Getter
public class Coupon {

	private final String id;
	private final String code;
	private final String description;
	private final BigDecimal discountValue;
	private final Instant expirationDate;
	private Status status;
	private final boolean published;
	private final boolean redeemed;
	
	private static final String CODE_REQUIRED = "Código do cupom não informado.";
	private static final String DESCRIPTION_REQUIRED = "Descrição do cupom não informada.";
	private static final String EXPIRATION_DATE_REQUIRED = "Data de expiração do cupom não informada.";
	private static final String DISCOUNT_REQUIRED = "Desconto do cupom não informado.";
	private static final String INVALID_CODE_LENGTH = "Código do cupom deve conter exatamente 6 caracteres alfanuméricos.";
	private static final String INVALID_DISCOUNT_VALUE = "Valor do desconto deve ser no mínimo 0.5.";
	private static final String INVALID_EXPIRATION_DATE = "Data de expiração não pode estar no passado.";

	public Coupon(String code, String description, BigDecimal discountValue, Instant expirationDate,
			Boolean published) {
	    validateRequired(code, CODE_REQUIRED);
	    validateRequired(description, DESCRIPTION_REQUIRED);
	    validateNotNull(expirationDate, EXPIRATION_DATE_REQUIRED);
	    validateNotNull(discountValue, DISCOUNT_REQUIRED);

	    String sanitizedCode = sanitize(code);
	    validateCodeLength(sanitizedCode);
	    validateMinDiscount(discountValue);
	    validateExpirationDate(expirationDate);

	    this.id = UUID.randomUUID().toString();
	    this.code = sanitizedCode;
	    this.description = description;
	    this.discountValue = discountValue;
	    this.expirationDate = expirationDate;
	    this.published = published != null ? published : false;
	    this.redeemed = false;
	    this.status = Status.ACTIVE;
	}
	
	/**
	 * Construtor apenas para a construção do cupom através da camada de persistencia.
	 * @param id
	 * @param code
	 * @param description
	 * @param discountValue
	 * @param expirationDate
	 * @param published
	 * @param redeemed
	 * @param status
	 */
	public Coupon(String id, String code, String description, BigDecimal discountValue, Instant expirationDate,
			Boolean published, Boolean redeemed, Status status) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.discountValue = discountValue;
		this.expirationDate = expirationDate;
		this.published = published;
		this.redeemed = redeemed;
		this.status = status;
	}

	/**
	 * Realiza o soft delete do cupom.
	 * 
	 * @throws CouponAlreadyDeletedException se o cupom já estiver deletado
	 */
	public void delete() {
		if (this.status == Status.DELETED) {
			throw new CouponAlreadyDeletedException();
		}
		this.status = Status.DELETED;
	}
	
	/**
	 * Valida se uma String é nula ou vazia.
	 * @param value
	 * @param message
	 * @throws InvalidCouponException
	 */
	private void validateRequired(String value, String message) {
	    if (value == null || value.isBlank()) {
	        throw new InvalidCouponException(message);
	    }
	}

	/**
	 * Valida se o objeto é nulo
	 * @param value
	 * @param message
	 * @throws InvalidCouponException
	 */
	private void validateNotNull(Object value, String message) {
	    if (value == null) {
	        throw new InvalidCouponException(message);
	    }
	}

	/**
	 * Remove os caracteres que não sejam alfanuméricos do código.
	 * @param code
	 * @return
	 */
	private String sanitize(String code) {
	    return code.replaceAll("[^a-zA-Z0-9]", "");
	}

	/**
	 * Valida que o código possui exatos 6 digitos.
	 * @param code
	 * @throws InvalidCouponException
	 */
	private void validateCodeLength(String code) {
	    if (code.length() != 6) {
	        throw new InvalidCouponException(INVALID_CODE_LENGTH);
	    }
	}

	/**
	 * Valida o valor mínimo de desconto.
	 * @param value
	 * @throws InvalidCouponException
	 */
	private void validateMinDiscount(BigDecimal value) {
	    if (value.compareTo(BigDecimal.valueOf(0.5)) < 0) {
	        throw new InvalidCouponException(INVALID_DISCOUNT_VALUE);
	    }
	}

	/**
	 * Valida se a data de expiração já passou.
	 * @param date
	 * @throws InvalidCouponException
	 */
	private void validateExpirationDate(Instant date) {
	    if (date.isBefore(Instant.now())) {
	        throw new InvalidCouponException(INVALID_EXPIRATION_DATE);
	    }
	}

}
