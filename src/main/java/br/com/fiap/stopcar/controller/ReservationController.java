package br.com.fiap.stopcar.controller;

import br.com.fiap.stopcar.application.dto.ReservationCheckedDTO;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;
import br.com.fiap.stopcar.service.impl.ReservationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@RequiredArgsConstructor
public class ReservationController extends AbstractRestController {

    private final ReservationServiceImpl reservationService;

    // Controller responsável por retornar todas as reservas
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations() throws AppException {
        return ResponseEntity.ok(reservationService.getReservation());
    }

    // Controller responsável por retornar uma reserva específica através do id
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable("id") String id) throws AppException {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    // Controller responsável por salvar uma nova reserva
    @PostMapping
    public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservation) {
        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }

    // Controller responsável por retornar todas as reservas ativas
    @GetMapping("/ativas")
    public ResponseEntity<List<ReservationDTO>> getActiveReservations() {
        return ResponseEntity.ok(reservationService.getActiveReservations());
    }

    @PostMapping("/{id}/check")
    public ResponseEntity<ReservationCheckedDTO> getReservationChecked(@PathVariable String id) throws AppException {
        return ResponseEntity.ok(reservationService.getReservationChecked(id));
    }
}
