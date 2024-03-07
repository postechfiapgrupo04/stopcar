package br.com.fiap.stopcar.application.dto;

import br.com.fiap.stopcar.domain.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;


public record PaymentDTO(
        @NotBlank(message="Tipo de pagamento não pode ser vazio")
        PaymentType type,
        @NotBlank(message="Valor não pode ser vazio")
        String value,
        @NotNull(message="Data não pode ser nula")
        @NotEmpty(message="Data não pode ser nula")
        LocalDateTime date
) {
}



