package br.com.fiap.stopcar.application.dto;

import br.com.fiap.stopcar.domain.enums.CarType;

public record CarDTO (
    String model,
    String plate,
    CarType type
) {}