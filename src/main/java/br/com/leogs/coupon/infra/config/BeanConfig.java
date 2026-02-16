package br.com.leogs.coupon.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.leogs.coupon.application.CreateCouponUseCase;
import br.com.leogs.coupon.application.DeleteCouponUseCase;
import br.com.leogs.coupon.application.GetCouponUseCase;
import br.com.leogs.coupon.domain.CouponRepository;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class BeanConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI().info(
				new Info().title("Coupon API").version("1.0.0").description("API para gerenciamento de cupons da Tenda")
						.contact(new Contact().name("Leonardo Gabriel da Silva").email("leonardogdasilva05@gmail.com")));
	}

	@Bean
	CreateCouponUseCase createCouponUseCase(CouponRepository couponRepository) {
		return new CreateCouponUseCase(couponRepository);
	}

	@Bean
	DeleteCouponUseCase deleteCouponUseCase(CouponRepository couponRepository) {
		return new DeleteCouponUseCase(couponRepository);
	}

	@Bean
	GetCouponUseCase getCouponUseCase(CouponRepository couponRepository) {
		return new GetCouponUseCase(couponRepository);
	}
}
