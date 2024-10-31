package ch.zhaw.rentmybike.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.rentmybike.model.entities.Ride;

public interface RideRepository extends MongoRepository<Ride, String> {
    
}
