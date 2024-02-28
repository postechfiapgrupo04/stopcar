package br.com.fiap.stopcar.application.dto;

import java.time.LocalDateTime;

public record ReservationDTO(
        String id,
        CarDTO car,
        String location,
        LocalDateTime startDate,
        Long totalHours,
        boolean status,
        PaymentDTO payment
) {
}
