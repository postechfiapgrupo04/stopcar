package br.com.fiap.stopcar.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "reservation")
@NoArgsConstructor
@AllArgsConstructor
public class Reservations {

    @Id
    private String id;
    private String location;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean status;
    private Long totalHours;

    private Payments payment;
    private Cars car;
}
