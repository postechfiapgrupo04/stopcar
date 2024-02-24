package br.com.fiap.stopcar.domain.entities;

import br.com.fiap.stopcar.domain.enums.CarType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;

@Setter
@Getter
public class Car {
    private String model;
    private String plate;
    private CarType type;
}