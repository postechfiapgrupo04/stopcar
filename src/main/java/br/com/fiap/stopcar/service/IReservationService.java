package br.com.fiap.stopcar.service;

import br.com.fiap.stopcar.application.dto.ReservationCheckedDTO;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.dto.ReservationsCheckedTotalDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;

import java.util.List;

public interface IReservationService {
    public List<ReservationDTO> getReservation() throws AppException;
    public ReservationDTO getReservationDTOByReservationId(String id) throws AppException;
    public ReservationDTO saveReservation(ReservationDTO reservationDTO) throws AppException;
    public List<ReservationDTO> getActiveReservations();
    public ReservationCheckedDTO getReservationChecked(String id) throws AppException;
    public ReservationDTO updateReservation(ReservationDTO reservationDTO);
    public List<ReservationDTO> getReservationsByCarPlate(String plate) throws AppException;
    public ReservationsCheckedTotalDTO checkAllReservation();
}
