package br.com.fiap.stopcar.controller;

import br.com.fiap.stopcar.application.dto.CarDTO;
import br.com.fiap.stopcar.application.dto.PaymentDTO;
import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.enums.CarType;
import br.com.fiap.stopcar.domain.enums.PaymentType;
import br.com.fiap.stopcar.repository.ReservationRepository;
import br.com.fiap.stopcar.service.impl.ReservationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
public class ReservationControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ReservationServiceImpl reservationService;

	@MockBean
	private ReservationRepository reservationRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()); // Registrando o módulo
	
	private String asJsonString(final Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	void getReservations_ReturnsListOfReservations() throws Exception {
		CarDTO carDTO = new CarDTO("Modelo Teste", "ABC1234", CarType.SUV);
		PaymentDTO paymentDTO = new PaymentDTO(PaymentType.CREDIT_CARD, "100.00", LocalDateTime.now());
		ReservationDTO reservationDTO = new ReservationDTO("1", carDTO, "Local Teste", LocalDateTime.now(),  LocalDateTime.now().plusHours(2),2L, true, paymentDTO);
		
		List<ReservationDTO> reservations = Arrays.asList(reservationDTO);
		when(reservationService.getReservation()).thenReturn(reservations);
		
		mockMvc.perform(get("/reservas")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(reservationDTO.id())))
				.andExpect(jsonPath("$[0].car.model", is(carDTO.model())))
				.andExpect(jsonPath("$[0].payment.type", is(paymentDTO.type().toString())))
				.andExpect(jsonPath("$[0].location", is(reservationDTO.location())));
		
	}
	
	@Test
	void getReservationById_ReturnsReservation() throws Exception {
		CarDTO carDTO = new CarDTO("Modelo Teste", "ABC1234", CarType.SUV);
		PaymentDTO paymentDTO = new PaymentDTO(PaymentType.CREDIT_CARD, "100.00", LocalDateTime.now());
		ReservationDTO reservationDTO = new ReservationDTO("1", carDTO, "Local Teste", LocalDateTime.now(),  LocalDateTime.now().plusHours(2),  2L, true, paymentDTO);
		
		when(reservationService.getReservationDTOByReservationId("1")).thenReturn(reservationDTO);
		
		mockMvc.perform(get("/reservas/1")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("1")))
				.andExpect(jsonPath("$.car.model", is("Modelo Teste")));
	}
	
	@Test
	void saveReservation_ReturnsSavedReservation() throws Exception {
		CarDTO carDTO = new CarDTO("Modelo Teste", "ABC1234", CarType.SUV);
		PaymentDTO paymentDTO = new PaymentDTO(PaymentType.CREDIT_CARD, "100.00", LocalDateTime.now());
		ReservationDTO newReservation = new ReservationDTO(null, carDTO, "Local Teste", LocalDateTime.now(), LocalDateTime.now().plusHours(2),2L, true, paymentDTO);
		ReservationDTO savedReservation = new ReservationDTO("1", carDTO, "Local Teste", LocalDateTime.now(), LocalDateTime.now().plusHours(2),2L, true, paymentDTO);
		
		when(reservationService.saveReservation(any(ReservationDTO.class))).thenReturn(savedReservation);
		
		mockMvc.perform(post("/reservas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(newReservation)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is("1")));
	}
	
	@Test
	void getActiveReservations_ReturnsActiveReservations() throws Exception {
		CarDTO carDTO = new CarDTO("Modelo Teste", "DEF5678", CarType.SEDAN);
		PaymentDTO paymentDTO = new PaymentDTO(PaymentType.DEBIT_CARD, "200.00", LocalDateTime.now());
		ReservationDTO activeReservation = new ReservationDTO("2", carDTO, "Local Ativo Teste", LocalDateTime.now(), LocalDateTime.now().plusHours(2),3L, true, paymentDTO);
		
		List<ReservationDTO> activeReservations = Arrays.asList(activeReservation);
		
		when(reservationService.getActiveReservations()).thenReturn(activeReservations);
		
		mockMvc.perform(get("/reservas/ativas")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(activeReservation.id())));
	}
	
	@Test
	void updateReservation_ReturnsUpdatedReservation() throws Exception {
		CarDTO carDTO = new CarDTO("Modelo Atualizado", "GHI9012", CarType.SEDAN);
		PaymentDTO paymentDTO = new PaymentDTO(PaymentType.DEBIT_CARD, "300.00", LocalDateTime.now());
		ReservationDTO updatedReservation = new ReservationDTO("3", carDTO, "Local Atualizado Teste", LocalDateTime.now(), LocalDateTime.now().plusHours(2),4L, false, paymentDTO);
		
		when(reservationService.updateReservation(any(ReservationDTO.class))).thenReturn(updatedReservation);
		
		mockMvc.perform(put("/reservas")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(updatedReservation)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(updatedReservation.id())))
				.andExpect(jsonPath("$.car.model", is(carDTO.model())));
	}
	
	@Test
	void getReservationsByCarPlate_ReturnsReservations() throws Exception {
		CarDTO carDTO = new CarDTO("Modelo Por Placa", "JKL3456", CarType.PICKUP);
		PaymentDTO paymentDTO = new PaymentDTO(PaymentType.CREDIT_CARD, "400.00", LocalDateTime.now());
		ReservationDTO reservationByPlate = new ReservationDTO("4", carDTO, "Local Por Placa Teste", LocalDateTime.now(), LocalDateTime.now().plusHours(2),5L, true, paymentDTO);
		
		List<ReservationDTO> reservationsByPlate = Arrays.asList(reservationByPlate);
		String plate = "JKL3456";
		
		when(reservationService.getReservationsByCarPlate(plate)).thenReturn(reservationsByPlate);
		
		mockMvc.perform(get("/reservas/findByPlate/" + plate)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].car.plate", is(plate)));
	}

//	@Test
//	void checkAllReservations_ReturnsReservationsExpired() throws Exception {
//		when(reservationService.checkAllReservation()).thenReturn(new ReservationsCheckedTotalDTO(1));
//		mockMvc.perform(post("/checar/todas").contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk())
//				.andExpect(jsonPath("$", hasSize(1)))
//				.andExpect(jsonPath("$total", is(1)));
//	}
	
}
