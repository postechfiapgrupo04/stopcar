package br.com.fiap.stopcar.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ReservationDTO {
    private String id;
    private CarDTO car;
    private String location;
    private Date startDate;
    private int totalHours;
    private boolean status;
    private PaymentDTO payment;
    public ReservationDTO() {}
}