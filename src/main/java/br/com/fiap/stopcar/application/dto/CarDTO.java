package br.com.fiap.stopcar.application.dto;

import br.com.fiap.stopcar.domain.enums.CarType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CarDTO(
        @NotBlank(message="Modelo não pode ser vazio")
        String model,
        @NotBlank(message="Placa não pode ser vazia")
        String plate,
        @NotBlank(message="Tipo não pode ser vazio")
        CarType type
) {
}