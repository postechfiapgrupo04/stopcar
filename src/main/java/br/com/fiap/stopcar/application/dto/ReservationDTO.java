package br.com.fiap.stopcar.application.dto;

import java.math.BigInteger;
import java.util.Date;

public record ReservationDTO(
        String id,
        CarDTO car,
        String location,
        Date startDate,
        Long totalHours,
        boolean status,
        PaymentDTO payment
) {
}
