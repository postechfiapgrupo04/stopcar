package br.com.fiap.stopcar.service.impl;


import br.com.fiap.stopcar.application.annotations.AppError;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;
import br.com.fiap.stopcar.domain.constants.CacheConstants;
import br.com.fiap.stopcar.domain.entities.Reservations;
import br.com.fiap.stopcar.domain.mapper.ReservationMapper;
import br.com.fiap.stopcar.repository.ReservationRepository;
import br.com.fiap.stopcar.service.IReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    @AppError
    @Cacheable(value = CacheConstants.FIND_ALL_RESERVATIONS)
    public List<ReservationDTO> getReservation() throws AppException {
        List<ReservationDTO> reservation = reservationRepository.findAll()
                .stream()
                .map(reservationMapper::toReservationDTO)
                .toList();
        if (reservation.isEmpty()) {
            throw new AppException("Não existem reservas a serem listadas");
        }
        return reservation;
    }

    @AppError
    public ReservationDTO findById(String id) throws AppException {
        return reservationMapper
                .toReservationDTO(reservationRepository.findById(id)
                        .orElseThrow(() -> new AppException("Não existe uma reserva com o id informado")));
    }

    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        Reservations reservations = reservationMapper.toReservation(reservationDTO);

        if (reservations.getStartDate() != null) {
            reservations.setEndDate(reservations.getStartDate().plusHours(reservationDTO.totalHours()));
        }
        return reservationMapper.toReservationDTO(reservationRepository.save(reservations));
    }

    public List<ReservationDTO> getActiveReservations() {
        return reservationRepository.findAll().stream()
                .filter(Reservations::isStatus)
                .map(reservationMapper::toReservationDTO)
                .collect(Collectors.toList());
    }

}
