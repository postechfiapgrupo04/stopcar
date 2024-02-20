package br.com.fiap.stopcar.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@Document(collection = "reservation")
public class Reservation {

    @Id
    private BigInteger _id;
    private String location;
    private Date startDate;
    private Date endDate;
    private boolean status;

    @DBRef
    private Payment payment;

    @DBRef
    private Car car;

}
