package br.com.fiap.stopcar.application.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record ReservationCheckedDTO(
        String id,
        CarDTO car,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Long totalHours,
        boolean status,
        String message
) {
}
