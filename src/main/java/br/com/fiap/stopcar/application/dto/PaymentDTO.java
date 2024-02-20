package br.com.fiap.stopcar.application.dto;

import java.math.BigInteger;

public record PaymentDTO(
        BigInteger id,
        String type,
        String value,
        String date
) {
}
