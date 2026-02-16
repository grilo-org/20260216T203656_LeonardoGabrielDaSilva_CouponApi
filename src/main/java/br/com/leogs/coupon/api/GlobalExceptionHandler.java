package br.com.leogs.coupon.api;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.leogs.coupon.api.dto.ErrorResponse;
import br.com.leogs.coupon.domain.exception.CouponAlreadyDeletedException;
import br.com.leogs.coupon.domain.exception.CouponNotFoundException;
import br.com.leogs.coupon.domain.exception.InvalidCouponException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		log.error("Erro inesperado: {}", ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ErrorResponse("Erro interno.", HttpStatus.INTERNAL_SERVER_ERROR.value(), Instant.now()));
	}

	@ExceptionHandler(InvalidCouponException.class)
	public ResponseEntity<ErrorResponse> handleInvalidCoupon(InvalidCouponException ex) {
		log.warn(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), Instant.now()));
	}

	@ExceptionHandler(CouponNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCouponNotFound(CouponNotFoundException ex) {
		log.warn(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), Instant.now()));
	}

	@ExceptionHandler(CouponAlreadyDeletedException.class)
	public ResponseEntity<ErrorResponse> handleCouponAlreadyDeleted(CouponAlreadyDeletedException ex) {
		log.warn(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse(ex.getMessage(), HttpStatus.CONFLICT.value(), Instant.now()));
	}

	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity<ErrorResponse> handleDateTimeParseException(DateTimeParseException ex) {
		log.warn("Erro: O formato de data informado está inválido: {}", ex.getMessage(), ex);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
				"Erro: O formato de data informado está inválido.", HttpStatus.BAD_REQUEST.value(), Instant.now()));
	}
}