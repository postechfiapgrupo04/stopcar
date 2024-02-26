package br.com.fiap.stopcar.repository;

import br.com.fiap.stopcar.domain.entities.Reservations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends MongoRepository<Reservations, String> {
}
