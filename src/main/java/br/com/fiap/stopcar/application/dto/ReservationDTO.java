package br.com.fiap.stopcar.application.dto;

import br.com.fiap.stopcar.application.exceptions.AppException;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@Schema(description = "DTO para representar uma reserva de estacionamento")
public record ReservationDTO(
        @Schema(description = "ID da reserva")
        String id,
        @Schema(description = "Informações do carro")
        @NotNull(message = "Carro não pode ser nulo")
        CarDTO car,
        @Schema(description = "Localização da reserva")
        @NotBlank(message = "Localização não pode ser vazia")
        String location,
        @Schema(description = "Data e hora de início da reserva")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @NotNull(message = "Data de início não pode ser nula")
        LocalDateTime startDate,
        LocalDateTime endDate,
        @Schema(description = "Total de horas da reserva")
        @Min(value = 1, message = "Total de horas não pode ser menor que 1 por reserva")
        @Max(value = 2, message = "Total de horas não pode ser maior que 2 por reserva")
        @NotNull(message = "Total de horas não pode ser nulo")
        Long totalHours,
        @Schema(description = "Status da reserva (ativo ou não)")
        @NotNull(message = "Status não pode ser nulo")
        boolean status,
        @Schema(description = "Informações de pagamento")
        @NotNull(message = "Pagamento não pode ser nulo")
        PaymentDTO payment
) {
}
