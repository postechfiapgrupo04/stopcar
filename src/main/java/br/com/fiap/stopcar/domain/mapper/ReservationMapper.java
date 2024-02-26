package br.com.fiap.stopcar.domain.mapper;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservation;
import org.mapstruct.Mapper;

@Mapper
public interface ReservationMapper {
    ReservationDTO toReservationDTO(Reservation reservation);
}
