package br.com.leogs.coupon.infra.persistence.jpa;

import java.math.BigDecimal;
import java.time.Instant;

import br.com.leogs.coupon.domain.Coupon;
import br.com.leogs.coupon.domain.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "coupon")
public class CouponJpaEntity {

	@Id
	private String id;
	private String code;
	private String description;
	private BigDecimal discountValue;
	private Instant expirationDate;
	@Enumerated(EnumType.STRING)
	private Status status;
	private boolean published;
	private boolean redeemed;

	public Coupon toModel() {
		return new Coupon(id, code, description, discountValue, expirationDate, published, redeemed, status);
	}

	public static CouponJpaEntity fromModel(Coupon coupon) {
		return new CouponJpaEntity(coupon.getId(), coupon.getCode(), coupon.getDescription(), coupon.getDiscountValue(),
				coupon.getExpirationDate(), coupon.isPublished(), coupon.isRedeemed(), coupon.getStatus());
	}
	
	protected CouponJpaEntity() {
		
	}

	public CouponJpaEntity(String id, String code, String description, BigDecimal discountValue,
			Instant expirationDate, boolean published, boolean redeemed, Status status) {
		this.id = id;
		this.code = code;
		this.description = description;
		this.discountValue = discountValue;
		this.expirationDate = expirationDate;
		this.published = published;
		this.redeemed = redeemed;
		this.status = status;
	}

}
