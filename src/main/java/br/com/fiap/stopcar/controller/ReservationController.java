package br.com.fiap.stopcar.controller;

import br.com.fiap.stopcar.application.dto.ReservationCheckedDTO;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;
import br.com.fiap.stopcar.service.impl.ReservationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservas")
@Tag(name = "Reservas", description = "Endpoints para gerenciar reservas de vagas para veículos")
@RequiredArgsConstructor
public class ReservationController extends AbstractRestController {

    private final ReservationServiceImpl reservationService;

    // Controller responsável por retornar todas as reservas
    @GetMapping
    @Operation(summary = "Obter todas as reservas", description = "Endpoint para obter todas as reservas.")
    public ResponseEntity<List<ReservationDTO>> getReservations() throws AppException {
        return ResponseEntity.ok(reservationService.getReservation());
    }

    // Controller responsável por retornar uma reserva específica através do id
    @GetMapping("/{id}")
    @Operation(summary = "Obter reserva por ID", description = "Endpoint para obter uma reserva específica pelo ID.")
    public ResponseEntity<ReservationDTO> getReservation(
            @Parameter(description = "ID da reserva", required = true)
            @PathVariable("id") String id) throws AppException {
        return ResponseEntity.ok(reservationService.getReservationDTOByReservationId(id));
    }

    // Controller responsável por salvar uma nova reserva
    @PostMapping
    @Operation(summary = "Salvar reserva", description = "Endpoint para salvar uma nova reserva.")
    public ResponseEntity<ReservationDTO> saveReservation(@Valid
            @Parameter(description = "Detalhes da reserva a ser salva", required = true)
            @RequestBody ReservationDTO reservation) {
        return ResponseEntity.ok(reservationService.saveReservation(reservation));
    }

    // Controller responsável por retornar todas as reservas ativas
    @GetMapping("/ativas")
    @Operation(summary = "Obter reservas ativas", description = "Endpoint para obter todas as reservas ativas.")
    public ResponseEntity<List<ReservationDTO>> getActiveReservations() {
        return ResponseEntity.ok(reservationService.getActiveReservations());
    }

    @PostMapping("/{id}/check")
    @Operation(summary = "Verificar reserva", description = "Endpoint para verificar uma reserva.")
    public ResponseEntity<ReservationCheckedDTO> getReservationChecked(
            @Parameter(description = "ID da reserva a ser verificada", required = true)
            @PathVariable String id) throws AppException {
        return ResponseEntity.ok(reservationService.getReservationChecked(id));
    }

    @PutMapping
    @Operation(summary = "Atualizar reserva", description = "Endpoint para atualizar uma reserva.")
    public ReservationDTO updateReservation(
            @Parameter(description = "Detalhes da reserva a ser atualizada", required = true)
            @RequestBody ReservationDTO reservationDTO) {
        return reservationService.updateReservation(reservationDTO);
    }

    @GetMapping("/findByPlate/{plate}")
    @Operation(summary = "Obter reservas por placa do carro", description = "Endpoint para obter todas as reservas associadas a uma placa de carro.")
    public List<ReservationDTO> getReservationsByCarPlate(
            @Parameter(description = "Placa do carro", required = true)
            @PathVariable String plate) throws AppException  {
        return reservationService.getReservationsByCarPlate(plate);
    }


}
