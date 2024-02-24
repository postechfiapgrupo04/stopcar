package br.com.fiap.stopcar;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservation;
import br.com.fiap.stopcar.repository.ReservationRepository;
import br.com.fiap.stopcar.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class StopcarApplicationTests {

	@Mock
	private ReservationRepository reservationRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private ReservationService reservationService;

	// Testa a Service de Recursos Ativos
	@Test public void testGetActiveReservations() { Reservation activeReservation1 = new Reservation(); activeReservation1.setStatus(true);


		Reservation activeReservation2 = new Reservation();
		activeReservation2.setStatus(false);

		Reservation activeReservation3 = new Reservation();
		activeReservation3.setStatus(true);

		Mockito.when(reservationRepository.findAll())
				.thenReturn(Arrays.asList(activeReservation1, activeReservation2, activeReservation3));

		List<ReservationDTO> activeReservations = reservationService.getActiveReservations();

		assertEquals(2, activeReservations.size());
		assertTrue(activeReservations.stream().allMatch(ReservationDTO::isStatus));
	}

}
