package br.com.leogs.coupon.domain.exception;

public class CouponAlreadyDeletedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT_MESSAGE = "Erro: Cupom já está deletado.";
	
    public CouponAlreadyDeletedException() {
        super(DEFAULT_MESSAGE);
    }
}
