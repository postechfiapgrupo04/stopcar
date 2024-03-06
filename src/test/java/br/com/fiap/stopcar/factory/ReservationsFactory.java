package br.com.fiap.stopcar.factory;

import br.com.fiap.stopcar.application.dto.ReservationCheckedDTO;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservations;
import br.com.fiap.stopcar.domain.mapper.ReservationMapper;

import java.time.LocalDateTime;

import static br.com.fiap.stopcar.factory.CarsFactory.builCarsDTO;
import static br.com.fiap.stopcar.factory.CarsFactory.buildCars;
import static br.com.fiap.stopcar.factory.PaymentsFactory.buildPaymentDTO;
import static br.com.fiap.stopcar.factory.PaymentsFactory.buildPayments;

public class ReservationsFactory {

    public static final LocalDateTime mockStartDate = LocalDateTime.of(2024, 2, 27, 17, 0, 0);
    public static final long mockTotalHours = 2L;
    public static final LocalDateTime mockEndDate = mockStartDate.plusHours(mockTotalHours);
    public static Reservations buildReservations() {
        return Reservations.builder()
                .id("123")
                .car(buildCars())
                .location("Location")
                .status(true)
                .startDate(mockStartDate)
                .totalHours(mockTotalHours)
                .endDate(mockEndDate)
                .payment(buildPayments())
                .build();
    }

    public static ReservationDTO buildReservationDTO() {
        return new ReservationDTO(
                buildReservations().getId(),
                builCarsDTO(),
                buildReservations().getLocation(),
                buildReservations().getStartDate(),
                buildReservations().getEndDate(),
                buildReservations().getTotalHours(),
                buildReservations().isStatus(),
                buildPaymentDTO()
        );
    }

    public static ReservationCheckedDTO buildReservationCheckedValidDTO() {
        return new ReservationCheckedDTO(
                buildReservations().getId(),
                builCarsDTO(),
                buildReservations().getStartDate(),
                buildReservations().getEndDate(),
                buildReservations().getTotalHours(),
                buildReservations().isStatus(),
                ReservationMapper.RESERVATION_VALID_MSG
        );
    }
}
