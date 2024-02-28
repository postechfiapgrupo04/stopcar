package br.com.fiap.stopcar.application.dto;

import br.com.fiap.stopcar.domain.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public record PaymentDTO (
   PaymentType type,
   String value,
   LocalDateTime date
){}



