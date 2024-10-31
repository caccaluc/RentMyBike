package ch.zhaw.rentmybike.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.model.entities.Ride.RideStatus;

public interface RideRepository extends MongoRepository<Ride, String> {
    
    // Abfrage nach Besitzer-ID
    List<Ride> findByOwnerId(String ownerId);

    // Abfrage nach Status
    List<Ride> findByStatus(RideStatus status);

}
