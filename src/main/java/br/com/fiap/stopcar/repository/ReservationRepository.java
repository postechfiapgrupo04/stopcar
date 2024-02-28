package br.com.fiap.stopcar.repository;

import br.com.fiap.stopcar.application.dto.ReservationDTO;
import br.com.fiap.stopcar.domain.entities.Reservations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservations, String> {

    @Query("{'car.plate': '?0'}")
    public List<Reservations> getReservationsByCarPlate(String plate);
}
