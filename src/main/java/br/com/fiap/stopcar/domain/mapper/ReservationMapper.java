package br.com.fiap.stopcar.domain.mapper;

import br.com.fiap.stopcar.application.dto.ReservationCheckedDTO;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservations;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ReservationMapper {
    public static final String RESERVATION_VALID_MSG = "Reserva dentro da validade!";
    public static final String RESERVATION_INVALID_MSG = "Reserva fora da validade!";

    ReservationDTO toReservationDTO(Reservations reservations);
    Reservations toReservation(ReservationDTO reservationDTO);
    @Mapping(target = "message", expression = "java(validateMessage(reservations))")
    ReservationCheckedDTO toReservationCheckedDTO(Reservations reservations);
    default String validateMessage(Reservations reservations) {
        return reservations.isStatus() ? RESERVATION_VALID_MSG : RESERVATION_INVALID_MSG;
    }
}
