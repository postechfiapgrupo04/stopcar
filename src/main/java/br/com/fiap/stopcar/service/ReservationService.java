package br.com.fiap.stopcar.service;


import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservation;
import br.com.fiap.stopcar.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    ModelMapper modelMapper = new ModelMapper();

    public List<ReservationDTO> getReservation() {
        List<ReservationDTO> reservation = reservationRepository.findAll()
                .stream()
                .map(R -> modelMapper.map(R, ReservationDTO.class))
                .toList();
        return reservation;
    }

    public ReservationDTO findById(String id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        Reservation reservation1 = reservation.orElseThrow(() -> new RuntimeException("Reserva inexistente"));
        return modelMapper.map(reservation1, ReservationDTO.class);
    }

    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        Reservation reservation = modelMapper.map(reservationDTO, Reservation.class);
        //regra de hora de expiracao
        reservation.setEndDate(reservation.getStartDate().plusHours(reservationDTO.totalHours()));
        return modelMapper.map(reservationRepository.save(reservation), ReservationDTO.class);
    }

}
