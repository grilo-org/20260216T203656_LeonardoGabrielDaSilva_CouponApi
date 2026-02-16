package br.com.leogs.coupon.infra.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponJpaRepository extends JpaRepository<CouponJpaEntity, String> {
	
}