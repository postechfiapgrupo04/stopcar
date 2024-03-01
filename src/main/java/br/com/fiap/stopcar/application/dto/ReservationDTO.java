package br.com.fiap.stopcar.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "DTO para representar uma reserva de estacionamento")
public record ReservationDTO(
        @Schema(description = "ID da reserva")
        String id,
        @Schema(description = "Informações do carro")
        CarDTO car,
        @Schema(description = "Localização da reserva")
        String location,
        @Schema(description = "Data e hora de início da reserva")
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime startDate,
        @Schema(description = "Total de horas da reserva")
        Long totalHours,
        @Schema(description = "Status da reserva (ativo ou não)")
        boolean status,
        @Schema(description = "Informações de pagamento")
        PaymentDTO payment
) {
}
