package br.com.fiap.stopcar.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para representar quantas reservas foram expiradas/afetadas ")
public record ReservationsCheckedTotalDTO(
		@Schema(description = "Total reservas expiradas")
		Integer total
) {
}
