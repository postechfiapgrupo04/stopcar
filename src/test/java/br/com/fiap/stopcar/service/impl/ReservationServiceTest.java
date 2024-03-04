package br.com.fiap.stopcar.service.impl;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservations;
import br.com.fiap.stopcar.domain.mapper.ReservationMapper;
import br.com.fiap.stopcar.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReservationServiceTest {

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationDTO reservationDTO;

    @Mock
    private Reservations reservations;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);

        when(reservationMapper.toReservation(reservationDTO)).thenReturn(reservations);
        when(reservationRepository.save(any(Reservations.class))).thenReturn(reservations);
        when(reservationMapper.toReservationDTO(any(Reservations.class))).thenReturn(reservationDTO);
    }

//    @Test
//    public void saveReservationTest(){
//        ReservationDTO result = reservationService.saveReservation(reservationDTO);
//
//        verify(reservationMapper, times(1)).toReservation(reservationDTO);
//        verify(reservationRepository, times(2)).save(reservations);
//        verify(reservationMapper, times(1)).toReservationDTO(reservations);
//
//        assertEquals(result, reservationDTO);
//    }
}