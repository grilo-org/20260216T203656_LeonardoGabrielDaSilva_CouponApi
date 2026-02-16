package br.com.leogs.coupon.infra.persistence.jpa;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.com.leogs.coupon.domain.Coupon;
import br.com.leogs.coupon.domain.CouponRepository;

@Repository
public class CouponRepositoryJpaAdapter implements CouponRepository {

	private final CouponJpaRepository jpaRepository;
	
	public CouponRepositoryJpaAdapter(CouponJpaRepository jpaRepository) {
		this.jpaRepository = jpaRepository;
	}
	
	@Override
	public Coupon save(Coupon coupon) {
        return jpaRepository.save(CouponJpaEntity.fromModel(coupon)).toModel();
	}

	@Override
	public Optional<Coupon> findById(String id) {
        return jpaRepository.findById(id).map(CouponJpaEntity::toModel);
	}

	
}
