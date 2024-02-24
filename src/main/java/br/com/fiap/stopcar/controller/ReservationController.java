package br.com.fiap.stopcar.controller;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservation;
import br.com.fiap.stopcar.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservation() {
        return ResponseEntity.ok(reservationService.getReservation());
    }
    @PostMapping
    public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservation) {
        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }

}
