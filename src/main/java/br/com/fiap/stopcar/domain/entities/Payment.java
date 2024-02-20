package br.com.fiap.stopcar.domain.entities;

import br.com.fiap.stopcar.domain.enums.PaymentType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Date;

@Setter
@Getter
@Document(collection = "payment")
public class Payment {

    @Id
    private BigInteger _id;

    private Double value;

    private Date paymentDate;

    private PaymentType paymentType;

    @DBRef(lazy = true)
    private Reservation reservation;

}
