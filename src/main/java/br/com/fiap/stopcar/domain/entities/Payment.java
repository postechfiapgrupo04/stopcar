package br.com.fiap.stopcar.domain.entities;

import br.com.fiap.stopcar.domain.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
public class Payment {
    private Double value;
    private LocalDateTime paymentDate;
    private PaymentType paymentType;
}
