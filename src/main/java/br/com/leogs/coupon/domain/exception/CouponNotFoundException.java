package br.com.leogs.coupon.domain.exception;

public class CouponNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "Erro: Cupom não encontrado.";
	
    public CouponNotFoundException() {
        super(DEFAULT_MESSAGE);
    }
}
