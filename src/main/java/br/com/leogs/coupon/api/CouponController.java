package br.com.leogs.coupon.api;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.leogs.coupon.api.dto.CouponResponse;
import br.com.leogs.coupon.api.dto.CreateCouponRequest;
import br.com.leogs.coupon.application.CreateCouponUseCase;
import br.com.leogs.coupon.application.DeleteCouponUseCase;
import br.com.leogs.coupon.application.GetCouponUseCase;
import br.com.leogs.coupon.application.dto.CouponOutput;
import br.com.leogs.coupon.application.dto.CreateCouponCommand;

@RestController
@RequestMapping("/coupon")
public class CouponController {

	private final CreateCouponUseCase createCouponUseCase;
	private final DeleteCouponUseCase deleteCouponUseCase;
	private final GetCouponUseCase getCouponUseCase;


	public CouponController(CreateCouponUseCase createCouponUseCase, DeleteCouponUseCase deleteCouponUseCase, GetCouponUseCase getCouponUseCase) {
		this.createCouponUseCase = createCouponUseCase;
		this.deleteCouponUseCase = deleteCouponUseCase;
		this.getCouponUseCase = getCouponUseCase;
	}

	@PostMapping
	public ResponseEntity<CouponResponse> createCoupon(@RequestBody CreateCouponRequest createCouponDto) {
		CouponOutput outputDto = this.createCouponUseCase.execute(
				new CreateCouponCommand(
						createCouponDto.code(), 
						createCouponDto.description(), 
						BigDecimal.valueOf(createCouponDto.discountValue()), 
						Instant.parse(createCouponDto.expirationDate()), 
						createCouponDto.published())
			);
		return new ResponseEntity<>(CouponResponse.from(outputDto), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCoupon(@PathVariable String id) {
		this.deleteCouponUseCase.execute(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CouponResponse> getCoupon(@PathVariable String id) {
		CouponOutput outputDto = this.getCouponUseCase.execute(id);
		return new ResponseEntity<>(CouponResponse.from(outputDto), HttpStatus.OK);
	}
}
