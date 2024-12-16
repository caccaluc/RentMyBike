package ch.zhaw.rentmybike.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.model.entities.Ride.RideStatus;

public interface RideRepository extends MongoRepository<Ride, String> {
    
    // Abfrage nach Status
    Page<Ride> findByStatus(RideStatus status, Pageable pageable);
    
    // Abfrage nach Besitzer-ID
    Page<Ride> findByOwnerId(String ownerId, Pageable pageable);
    
    // Abfrage nach Mieter-ID
    Page<Ride> findByRenterId(String renterId, Pageable pageable);

    // Abfrage nach Stadt, Startzeit, Endzeit und Preisbereich
    @Query("{ 'pickupAdress.city' : ?0, 'startingTime' : { $gte : ?1, $lte : ?2 }, 'endingTime' : { $gte : ?1, $lte : ?2 }, 'price' : { $gte : ?3, $lte : ?4 } }")
    Page<Ride> findAvailableRidesByCityAndStartTimeBetweenAndEndTimeBetweenAndPriceBetween(String city,
            LocalDateTime startTime, LocalDateTime endTime, Integer minPrice, Integer maxPrice, Pageable pageable);

    // Abfrage nach Stadt, Startzeit, Endzeit und Mindestpreis
    @Query("{ 'pickupAdress.city' : ?0, 'startingTime' : { $gte : ?1, $lte : ?2 }, 'endingTime' : { $gte : ?1, $lte : ?2 }, 'price' : { $gte : ?3 } }")
    Page<Ride> findAvailableRidesByCityAndStartTimeBetweenAndEndTimeBetweenAndMinPrice(String city,
            LocalDateTime startTime, LocalDateTime endTime, Integer minPrice, Pageable pageable);

    // Abfrage nach Stadt, Startzeit und Endzeit
    @Query("{ 'pickupAdress.city' : ?0, 'startingTime' : { $gte : ?1, $lte : ?2 }, 'endingTime' : { $gte : ?1, $lte : ?2 } }")        
    Page<Ride> findAvailableRidesByCityAndStartTimeBetweenAndEndTimeBetween(String city, LocalDateTime startTime,
            LocalDateTime endTime, Pageable pageable);

    // Abfrage nach Stadt und Startzeit
    @Query("{ 'pickupAdress.city' : ?0, 'startingTime' : { $gte : ?1 } }")
    Page<Ride> findAvailableRidesByCityAndStartTime(String city, LocalDateTime startTime, Pageable pageable);

    // Abfrage nach Startzeit, Endzeit und Preisbereich
    @Query("{ 'startingTime' : { $gte : ?0, $lte : ?1 }, 'endingTime' : { $gte : ?0, $lte : ?1 }, 'price' : { $gte : ?2, $lte : ?3 } }")
    Page<Ride> findAvailableRidesByStartTimeBetweenAndEndTimeBetweenAndPriceBetween(LocalDateTime startTime,
            LocalDateTime endTime, Integer minPrice, Integer maxPrice, Pageable pageable);

    // Abfrage nach Startzeit, Endzeit und Mindestpreis
    @Query("{ 'startingTime' : { $gte : ?0, $lte : ?1 }, 'endingTime' : { $gte : ?0, $lte : ?1 }, 'price' : { $gte : ?2 } }")
    Page<Ride> findAvailableRidesByStartTimeBetweenAndEndTimeBetweenAndMinPrice(LocalDateTime startTime,
            LocalDateTime endTime, Integer minPrice, Pageable pageable);

    // Abfrage nach Startzeit und Endzeit
    @Query("{ 'startingTime' : { $gte : ?0, $lte : ?1 }, 'endingTime' : { $gte : ?0, $lte : ?1 } }")
    Page<Ride> findAvailableRidesByStartTimeBetweenAndEndTimeBetween(LocalDateTime startTime, LocalDateTime endTime,
            Pageable pageable);

    // Abfrage nach Startzeit und Preisbereich
    @Query("{ 'startingTime' : { $gte : ?0 }, 'price' : { $gte : ?1, $lte : ?2 } }")
    Page<Ride> findAvailableRidesByStartTimeAndPriceBetween(LocalDateTime startTime, Integer minPrice, Integer maxPrice,
            PageRequest of);

    // Abfrage nach Startzeit und Mindestpreis
    @Query("{ 'startingTime' : { $gte : ?0 }, 'price' : { $gte : ?1 } }")
    Page<Ride> findAvailableRidesByStartTimeAndMinPrice(LocalDateTime startTime, Integer minPrice, PageRequest of);

    // Abfrage nach Startzeit und Höchstpreis
    @Query("{ 'startingTime' : { $gte : ?0 }, 'price' : { $lte : ?1 } }")
    Page<Ride> findAvailableRidesByStartTimeAndMaxPrice(LocalDateTime startTime, Integer maxPrice, PageRequest of);

    // Abfrage nach Startzeit
    @Query("{ 'startingTime' : { $gte : ?0 } }")
    Page<Ride> findAvailableRidesByStartTime(LocalDateTime startTime, Pageable pageable);

    // Abfrage nach Stadt
    @Query("{ 'pickupAdress.city' : ?0 }")
    Page<Ride> findAvailableRidesByCity(String city, Pageable pageable);

    // Abfrage nach Mindestpreis
    @Query("{ 'price' : { $gte : ?0 } }")
    Page<Ride> findAvailableRidesByMinPrice(Integer minPrice, Pageable pageable);

    // Abfrage nach Höchstpreis
    @Query("{ 'price' : { $lte : ?0 } }")
    Page<Ride> findAvailableRidesByMaxPrice(Integer maxPrice, Pageable pageable);

    // Abfrage nach Endzeit
    @Query("{ 'endingTime' : { $lte : ?0 } }")
    Page<Ride> findAvailableRidesByEndTime(LocalDateTime endTime, Pageable pageable);

}
