package ch.zhaw.rentmybike.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.rentmybike.model.entities.Motorcycle; 

public interface MotorcycleRepository extends MongoRepository<Motorcycle, String> {

  List<Motorcycle> findByUserId(String userId);

}
