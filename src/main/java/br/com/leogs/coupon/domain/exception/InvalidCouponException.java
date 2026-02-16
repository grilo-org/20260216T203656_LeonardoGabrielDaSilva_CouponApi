package br.com.leogs.coupon.domain.exception;

public class InvalidCouponException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public InvalidCouponException(String message) {
        super(message);
    }
}
