package br.com.fiap.stopcar.application.dto;

import java.math.BigInteger;
import java.util.Date;

public record ReservationDTO(
        BigInteger id,
        String carId,
        String location,
        Date startDate,
        Date endDate,
        boolean status,
        String paymentId
) {
}
