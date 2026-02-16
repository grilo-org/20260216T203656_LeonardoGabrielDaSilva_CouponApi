package br.com.leogs.coupon.api.dto;

import br.com.leogs.coupon.application.dto.CouponOutput;
import br.com.leogs.coupon.domain.Status;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados de resposta de um cupom")
public record CouponResponse(

    @Schema(description = "Identificador único do cupom", example = "550e8400-e29b-41d4-a716-446655440000")
    String id,

    @Schema(description = "Código do cupom", example = "ABC123")
    String code,

    @Schema(description = "Descrição do cupom", example = "Cupom de desconto para novos clientes")
    String description,

    @Schema(description = "Valor de desconto", example = "0.8")
    double discountValue,

    @Schema(description = "Data de expiração do cupom", example = "2026-11-04T17:14:45.180Z")
    String expirationDate,

    @Schema(description = "Se o cupom está publicado", example = "false")
    boolean published,

    @Schema(description = "Se o cupom foi resgatado", example = "false")
    boolean redeemed,

    @Schema(description = "Status do cupom", example = "ACTIVE")
    Status status
) {

    public static CouponResponse from(CouponOutput couponOutput) {
        return new CouponResponse(
            couponOutput.id(),
            couponOutput.code(),
            couponOutput.description(),
            couponOutput.discountValue().doubleValue(),
            couponOutput.expirationDate().toString(),
            couponOutput.published(),
            couponOutput.redeemed(),
            couponOutput.status()
        );
    }
}