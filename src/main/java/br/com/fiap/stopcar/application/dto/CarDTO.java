package br.com.fiap.stopcar.application.dto;

import br.com.fiap.stopcar.domain.enums.CarType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CarDTO(
        @NotNull(message="Modelo não pode ser nulo")
        @NotEmpty(message="Modelo não pode ser vazio")
        String model,
        @NotNull(message="Placa não pode ser nula")
        @NotEmpty(message="Placa não pode ser vazia")
        String plate,
        @NotNull(message="Tipo não pode ser nulo")
        @NotEmpty(message="Tipo não pode ser vazio")
        CarType type
) {
}