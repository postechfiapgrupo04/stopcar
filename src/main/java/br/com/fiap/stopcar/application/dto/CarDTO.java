package br.com.fiap.stopcar.application.dto;

import java.math.BigInteger;

public record CarDTO(BigInteger id, String model, String plate, String type) {
}
