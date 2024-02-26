package br.com.fiap.stopcar.service;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;

import java.util.List;

public interface IReservationService {
    public List<ReservationDTO> getReservation() throws AppException;
    public ReservationDTO findById(String id) throws AppException;
    public ReservationDTO saveReservation(ReservationDTO reservationDTO);
    public List<ReservationDTO> getActiveReservations();
}