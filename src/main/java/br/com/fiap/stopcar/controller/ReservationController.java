package br.com.fiap.stopcar.controller;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;
import br.com.fiap.stopcar.domain.entities.Reservation;
import br.com.fiap.stopcar.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
public class ReservationController extends AbstractRestController{

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations() throws AppException {
        return ResponseEntity.ok(reservationService.getReservation());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable("id") String id) throws AppException {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservation) {
        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }

}
