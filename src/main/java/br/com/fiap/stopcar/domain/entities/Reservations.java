package br.com.fiap.stopcar.domain.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "reservation")
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
