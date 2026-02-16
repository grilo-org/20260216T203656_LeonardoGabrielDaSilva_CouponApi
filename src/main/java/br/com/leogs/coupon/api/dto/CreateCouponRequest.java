package br.com.leogs.coupon.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Dados para criação de um cupom")
public record CreateCouponRequest(

    @Schema(description = "Código do cupom (6 caracteres alfanuméricos)", example = "ABC-123")
    String code,

    @Schema(description = "Descrição do cupom", example = "Iure saepe amet. Excepturi saepe inventore nam doloremque voluptatem a.")
    String description,

    @Schema(description = "Valor de desconto (mínimo 0.5)", example = "0.8")
    double discountValue,

    @Schema(description = "Data de expiração do cupom", example = "2026-11-04T17:14:45.180Z")
    String expirationDate,

    @Schema(description = "Se o cupom já está publicado", example = "false")
    Boolean published
) {}