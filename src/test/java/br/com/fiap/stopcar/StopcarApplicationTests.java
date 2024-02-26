package br.com.fiap.stopcar;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservations;
import br.com.fiap.stopcar.domain.mapper.ReservationMapper;
import br.com.fiap.stopcar.repository.ReservationRepository;
import br.com.fiap.stopcar.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class StopcarApplicationTests {

	@Mock
	private ReservationRepository reservationRepository;

	@InjectMocks
	private ReservationServiceImpl reservationService;

	@Mock
	private ReservationMapper reservationMapper;

	// Testa a Service de Recursos Ativos
	@Test
	public void testGetActiveReservations() {
		Reservations activeReservations1 = new Reservations();
		activeReservations1.setStatus(true);

		Reservations activeReservations2 = new Reservations();
		activeReservations2.setStatus(false);

		Reservations activeReservations3 = new Reservations();
		activeReservations3.setStatus(true);

		Mockito.when(reservationRepository.findAll())
				.thenReturn(List.of(activeReservations1, activeReservations2, activeReservations3));

		List<ReservationDTO> activeReservations = reservationService.getActiveReservations();

		assertEquals(2, activeReservations.size());
//		assertTrue(activeReservations.stream().allMatch(ReservationDTO::status));
	}

}
