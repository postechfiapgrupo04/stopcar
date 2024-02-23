package br.com.fiap.stopcar.domain.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter
@Setter
@Document(collection = "reservation")
public class Reservation {

    @Id
    private String id;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean status;
    private Long totalHours;

    private Payment payment;
    private Car car;
}
