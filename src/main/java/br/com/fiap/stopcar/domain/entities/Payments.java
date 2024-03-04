package br.com.fiap.stopcar.domain.entities;

import br.com.fiap.stopcar.domain.enums.PaymentType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class Payments {
    private Double value;
    private LocalDateTime date;
    private PaymentType type;
}
