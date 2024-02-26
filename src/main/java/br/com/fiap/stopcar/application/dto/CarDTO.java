package br.com.fiap.stopcar.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CarDTO {
    private String id;
    private String model;
    private String plate;
    private String type;
    public CarDTO() {}
}