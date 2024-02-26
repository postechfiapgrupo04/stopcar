package br.com.fiap.stopcar.domain.entities;

import br.com.fiap.stopcar.domain.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class Payments {
    private Double value;
    private LocalDateTime paymentDate;
    private PaymentType paymentType;
}
