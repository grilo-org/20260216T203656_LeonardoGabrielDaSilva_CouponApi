package br.com.leogs.coupon.application.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * DTO com todos os dados de criação de um cupom.
 */
public record CreateCouponCommand(String code, String description, BigDecimal discountValue, Instant expirationDate,
		Boolean published) {}
