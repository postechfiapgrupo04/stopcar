package br.com.fiap.stopcar.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ReservationDTO(
        String id,
        CarDTO car,
        String location,
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDate,
        Long totalHours,
        boolean status,
        PaymentDTO payment
) {
}
