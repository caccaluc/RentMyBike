package ch.zhaw.rentmybike.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.rentmybike.model.entities.Motorcycle; 

public interface MotorcycleRepository extends MongoRepository<Motorcycle, String> {
}
