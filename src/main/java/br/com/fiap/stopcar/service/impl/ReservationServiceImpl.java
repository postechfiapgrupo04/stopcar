package br.com.fiap.stopcar.service.impl;


import br.com.fiap.stopcar.application.annotations.AppError;
import br.com.fiap.stopcar.application.dto.ReservationCheckedDTO;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.dto.ReservationsCheckedTotalDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationServiceImpl implements IReservationService {

    public static final int VALOR_DEFAULT = 7;
    private final ReservationRepository reservationRepository;
    private final MongoTemplate mongoTemplate;
    private final ReservationMapper reservationMapper;

    @AppError
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
    @Cacheable(value = CacheConstants.FIND_RESERVATION_BY_ID)
    public ReservationDTO getReservationDTOByReservationId(String id) throws AppException {
        return reservationMapper.toReservationDTO(findReservationByIdOrThrows(id));
    }

    public ReservationDTO saveReservation(ReservationDTO reservationDTO) throws AppException {
        Reservations reservation = reservationMapper.toReservation(reservationDTO);

        if (reservation.getPayment().getType() == null || reservation.getPayment().getType().equals(""))
            throw new AppException("O método de pagamento precisa ser informado");

        if (reservation.getStartDate() != null) {
            if (reservation.getStartDate().isBefore(LocalDateTime.now()))
                throw new AppException("O início da reserva não pode ser antes da data atual.");
            if (reservation.getStartDate().isAfter(LocalDateTime.now().plusHours(24)))
                throw new AppException("O início da reserva não pode ser depois de 24 horas da data atual.");

            reservation.setEndDate(reservation.getStartDate().plusHours(reservationDTO.totalHours()));
        }
        reservation.setStatus(true);

        //calcular a payment
        reservation.getPayment().setValue((double) (reservation.getTotalHours() * VALOR_DEFAULT));
        reservation.getPayment().setDate(reservation.getStartDate());


        reservation = reservationRepository.save(reservation);
        return reservationMapper.toReservationDTO(reservationRepository.save(reservation));
    }

    public List<ReservationDTO> getActiveReservations() {//TODO alterar para realizar query no banco
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
    public List<ReservationDTO> getReservationsByCarPlate(String plate) throws AppException {
        List<Reservations> reservationsByCarPlate = reservationRepository.getReservationsByCarPlate(plate);
        List<ReservationDTO> reservationDTOS = reservationsByCarPlate.stream()
                .map(reservationMapper::toReservationDTO)
                .collect(Collectors.toList());

        if (reservationDTOS.isEmpty()) {
            throw new AppException("Não existem reservas com a placa informada");
        }

        return reservationDTOS;
    }

    @Override
    public ReservationsCheckedTotalDTO checkAllReservation() {
        List<Reservations> reservationsExpired = reservationRepository.findByStatusIsTrueAndEndDateBefore(LocalDateTime.now())
                .stream().peek(reservation -> reservation.setStatus(false)).toList();
        log.info("Total de reservas expiradas {}.", reservationsExpired.size());
        return new ReservationsCheckedTotalDTO(reservationRepository.saveAll(reservationsExpired).size());
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
