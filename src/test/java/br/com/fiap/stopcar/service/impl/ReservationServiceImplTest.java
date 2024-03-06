package br.com.fiap.stopcar.service.impl;

import br.com.fiap.stopcar.application.dto.ReservationCheckedDTO;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.application.dto.ReservationsCheckedTotalDTO;
import br.com.fiap.stopcar.application.exceptions.AppException;
import br.com.fiap.stopcar.domain.entities.Reservations;
import br.com.fiap.stopcar.domain.mapper.ReservationMapper;
import br.com.fiap.stopcar.repository.ReservationRepository;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;

import static br.com.fiap.stopcar.factory.ReservationsFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {
    @Mock
    private ReservationRepository reservationRepository;
    @InjectMocks
    private ReservationServiceImpl reservationService;
    @Mock
    private ReservationMapper reservationMapper;

    Logger logger;

    ListAppender<ILoggingEvent> logAppender = new ListAppender<>();

    @BeforeEach
    void setUp() {
        logger = (Logger) LoggerFactory.getLogger(ReservationServiceImpl.class);
        logger.setLevel(Level.DEBUG);
        logAppender.start();
        logger.addAppender(logAppender);
    }

    @Test
    void shouldFindAllReservationSuccess() throws AppException {
        when(reservationRepository.findAll()).thenReturn(Collections.singletonList(buildReservations()));
        List<ReservationDTO> result = reservationService.getReservation();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
    }

    @Test
    void shouldFindAllReservationEmpty() {
        List<Reservations> emptyList = new ArrayList<>();
        when(reservationRepository.findAll()).thenReturn(emptyList);

        AppException exception = assertThrows(AppException.class, () -> reservationService.getReservation());
        assertEquals("Não existem reservas a serem listadas", exception.getMessage());
    }

    @Test
    void shouldgetReservationDTOByReservationIdSuccess() throws AppException {
        when(reservationRepository.findById(any(String.class))).thenReturn(Optional.of(buildReservations()));
        when(reservationMapper.toReservationDTO(any(Reservations.class))).thenReturn(buildReservationDTO());

        ReservationDTO result = reservationService.getReservationDTOByReservationId("123");
        assertNotNull(result);
    }

    @Test
    void testFindReservationByIdNotFound() {
        String invalidId = "123";
        when(reservationRepository.findById(invalidId)).thenReturn(Optional.empty());
        AppException exception = assertThrows(AppException.class, () ->
                reservationService.getReservationDTOByReservationId(invalidId));
        assertEquals("Não existe uma reserva com o id informado", exception.getMessage());
    }

    @Test
    void shouldGetActiveReservations() {
        when(reservationRepository.findAll()).thenReturn(Collections.singletonList(buildReservations()));
        List<ReservationDTO> result = reservationService.getActiveReservations();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void testGetReservationChecked() throws AppException {
        when(reservationRepository.findById(any(String.class))).thenReturn(Optional.of(buildReservations()));
        when(reservationMapper.toReservationCheckedDTO(any(Reservations.class))).thenReturn(buildReservationCheckedValidDTO());
        ReservationCheckedDTO result = reservationService.getReservationChecked("123");
        assertNotNull(result);
        assertEquals(ReservationMapper.RESERVATION_VALID_MSG, result.message());
    }

    @Test
    void shouldValidateCheckedReservationExpired() throws AppException {
        Reservations reservation = buildReservations();
        reservation.setStatus(true);
        when(reservationRepository.findById(any(String.class))).thenReturn(Optional.of(buildReservations()));
        reservationService.getReservationChecked("123");
        var expectedLog = "reserva {}, do carro {} validada.";
        assertThat(logAppender.list.stream()
                .map(ILoggingEvent::getMessage).anyMatch(actualLog -> actualLog.contains(expectedLog))).isTrue();
        verify(reservationRepository, times(1)).save(any(Reservations.class));
    }

    @Test
    public void shouldGetReservationsByCarPlate() throws AppException {
        Reservations reservation = buildReservations();
        when(reservationRepository.getReservationsByCarPlate(reservation.getCar().getPlate())).thenReturn(Collections.singletonList(reservation));

        List<ReservationDTO> result = reservationService.getReservationsByCarPlate(reservation.getCar().getPlate());

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    void shouldGetReservationsByCarPlateNotFound() throws AppException {
        String invalidPlate = "999999";
        when(reservationRepository.getReservationsByCarPlate(invalidPlate)).thenReturn(new ArrayList<>());
        assertThrows(AppException.class, () -> reservationService.getReservationsByCarPlate(invalidPlate));
    }
    @Test
    void shouldVerifyCheckAllReservationsExpireds() {
        List<Reservations> reservationsExperid = Collections.singletonList(buildReservations());
        when(reservationRepository.findByStatusIsTrueAndEndDateBefore(any(LocalDateTime.class)))
                .thenReturn(reservationsExperid);

        ReservationsCheckedTotalDTO reservationsCheckedTotalDTO = reservationService.checkAllReservation();
        Assertions.assertNotNull(reservationsCheckedTotalDTO);
        verify(reservationRepository, times(1)).saveAll(reservationsExperid);
    }
}