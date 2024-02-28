package br.com.fiap.stopcar.service.impl;


import br.com.fiap.stopcar.application.annotations.AppError;
import br.com.fiap.stopcar.application.dto.ReservationCheckedDTO;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;
import br.com.fiap.stopcar.domain.constants.CacheConstants;
import br.com.fiap.stopcar.domain.entities.Reservations;
import br.com.fiap.stopcar.domain.mapper.ReservationMapper;
import br.com.fiap.stopcar.repository.ReservationRepository;
import br.com.fiap.stopcar.service.IReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements IReservationService {

    private final ReservationRepository reservationRepository;
    private final MongoTemplate mongoTemplate;
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
        return reservationMapper.toReservationDTO(findReservationByIdOrThrows(id));
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

    public ReservationCheckedDTO getReservationChecked(String id) throws AppException {
        Reservations reservationValidate = validateCheckedReservation(findReservationByIdOrThrows(id));
        return reservationMapper.toReservationCheckedDTO(reservationValidate);
    }

    @Transactional
    @Override
    public ReservationDTO updateReservation(ReservationDTO reservationDTO) {
        Reservations reservations = reservationMapper.toReservation(reservationDTO);
        return reservationMapper.toReservationDTO(reservationRepository.save(reservations));
    }

    @Override
    public List<ReservationDTO> getReservationsByCarPlate(String plate) {
        List<Reservations> reservationsByCarPlate = reservationRepository.getReservationsByCarPlate(plate);
        return reservationsByCarPlate.stream()
                .map(reservationMapper::toReservationDTO)
                .collect(Collectors.toList());
    }

    private Reservations validateCheckedReservation(final Reservations reservations) {
        LocalDateTime now = LocalDateTime.now();
        if (reservations.isStatus() && now.isAfter(reservations.getEndDate())) {
            reservations.setStatus(false);
            reservationRepository.save(reservations);
            log.info("reserva {}, do carro {} validada.",
                    reservations.getId(), reservations.getCar().getPlate());
        }
        return reservations;
    }

    private Reservations findReservationByIdOrThrows(String id) throws AppException {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new AppException("Não existe uma reserva com o id informado"));
    }


}
