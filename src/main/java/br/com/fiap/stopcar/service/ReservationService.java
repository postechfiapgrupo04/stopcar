package br.com.fiap.stopcar.service;


import br.com.fiap.stopcar.application.annotations.AppError;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;
import br.com.fiap.stopcar.domain.constants.CacheConstants;
import br.com.fiap.stopcar.domain.entities.Reservation;
import br.com.fiap.stopcar.domain.mapper.ReservationMapper;
import br.com.fiap.stopcar.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    ModelMapper modelMapper = new ModelMapper();

    private final ReservationMapper reservationMapper;

    @AppError
    @Cacheable(value = CacheConstants.FIND_ALL_RESERVATIONS)
    public List<ReservationDTO> getReservation() throws AppException {
        List<ReservationDTO> reservation = reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toReservationDTO)
                .toList();
        if(reservation.isEmpty()) {
            throw new AppException("Não existem reservas a serem listadas");
        }
        return reservation;
    }

    @AppError
    public ReservationDTO findById(String id) throws AppException {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if(reservation.isEmpty()) {
            throw new AppException("Não existe uma reserva com o id informado");
        }
        return modelMapper.map(reservation.get(), ReservationDTO.class);
    }

    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        //regra de hora de expiracao
        reservation.setEndDate(reservation.getStartDate().plusHours(reservationDTO.totalHours()));
        return modelMapper.map(reservationRepository.save(reservation), ReservationDTO.class);
    }

}
