package ch.zhaw.rentmybike.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.model.entities.Ride.RideStatus;

public interface RideRepository extends MongoRepository<Ride, String> {
    
    // Abfrage nach Status
    List<Ride> findByStatus(RideStatus status);

    // Query to filter by city
    @Query("{ 'status': 'AVAILABLE', 'pickupAdress.city': ?0 }")
    List<Ride> findAvailableRidesByCity(String city);

    // Query to filter by start time
    @Query("{ 'status': 'AVAILABLE', 'startingTime': { $gte: ?0 } }")
    List<Ride> findAvailableRidesByStartTime(LocalDateTime startTime);

    // Query to filter by end time
    @Query("{ 'status': 'AVAILABLE', 'endingTime': { $lte: ?0 } }")
    List<Ride> findAvailableRidesByEndTime(LocalDateTime endTime);

    // Query to filter by minimum price
    @Query("{ 'status': 'AVAILABLE', 'price': { $gte: ?0 } }")
    List<Ride> findAvailableRidesByMinPrice(Integer minPrice);

    // Query to filter by maximum price
    @Query("{ 'status': 'AVAILABLE', 'price': { $lte: ?0 } }")
    List<Ride> findAvailableRidesByMaxPrice(Integer maxPrice);

}
