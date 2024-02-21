package br.com.fiap.stopcar.service;


import br.com.fiap.stopcar.domain.entities.Reservation;
import br.com.fiap.stopcar.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {


    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getReservation() {
        return reservationRepository.findAll();
    }

    public Reservation saveReservation(Reservation reservation) {
        //regra de hora de expiracao
        reservation.setEndDate(reservation.getStartDate().plusHours(reservation.getTotalHours()));
        return reservationRepository.save(reservation);
    }

}
