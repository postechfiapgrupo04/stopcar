package br.com.fiap.stopcar.domain.mapper;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservations;
import org.mapstruct.Mapper;

@Mapper
public interface ReservationMapper {
    ReservationDTO toReservationDTO(Reservations reservations);
    Reservations toReservation(ReservationDTO reservationDTO);
}
