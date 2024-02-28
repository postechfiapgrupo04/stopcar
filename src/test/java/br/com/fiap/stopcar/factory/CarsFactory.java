package br.com.fiap.stopcar.factory;

import br.com.fiap.stopcar.application.dto.CarDTO;
import br.com.fiap.stopcar.domain.entities.Cars;
import br.com.fiap.stopcar.domain.enums.CarType;

public class CarsFactory {
    public static Cars buildCars() {
        return Cars.builder()
                .model("VW Polo HL")
                .plate("PDD4566")
                .type(CarType.HATCH)
                .build();
    }

    public static CarDTO builCarsDTO() {
        return new CarDTO(
                buildCars().getPlate(),
                buildCars().getModel(),
                buildCars().getType()
        );
    }
}
