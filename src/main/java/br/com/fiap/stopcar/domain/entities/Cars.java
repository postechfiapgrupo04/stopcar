package br.com.fiap.stopcar.domain.entities;

import br.com.fiap.stopcar.domain.enums.CarType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Cars {
    private String model;
    private String plate;
    private CarType type;
}
