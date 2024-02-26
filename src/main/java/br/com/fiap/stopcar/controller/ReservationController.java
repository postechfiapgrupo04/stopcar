package br.com.fiap.stopcar.controller;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;
import br.com.fiap.stopcar.service.ReservationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@Api(tags = "Reservas", description = "API para operações relacionadas a reservas")
public class ReservationController {
    
    private final ReservationService reservationService;
    
    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    
    @ApiOperation("Retorna todas as reservas")
    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getReservations() throws AppException {
        return ResponseEntity.ok(reservationService.getReservation());
    }
    
    @ApiOperation("Retorna uma reserva específica pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable("id") String id) throws AppException {
        return ResponseEntity.ok(reservationService.findById(id));
    }
    
    @ApiOperation("Salva uma nova reserva")
    @PostMapping
    public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservation) {
        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }
    
    @ApiOperation("Retorna todas as reservas ativas")
    @GetMapping("/ativas")
    public ResponseEntity<List<ReservationDTO>> getActiveReservations() {
        return ResponseEntity.ok(reservationService.getActiveReservations());
    }
}
