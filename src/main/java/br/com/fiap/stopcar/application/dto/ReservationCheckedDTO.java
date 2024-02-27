package br.com.fiap.stopcar.application.dto;

import java.util.Date;

public record ReservationCheckedDTO(
        String id,
        CarDTO car,
        Date startDate,
        Date endDate,
        Long totalHours,
        boolean status,
        String message
) {
}
