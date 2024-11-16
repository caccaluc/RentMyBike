package ch.zhaw.rentmybike.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.rentmybike.model.dtos.CreateRideDTO;
import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.model.entities.Ride.RideStatus;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.model.entities.User.UserState;
import ch.zhaw.rentmybike.repository.RideRepository;
import ch.zhaw.rentmybike.repository.UserRepository;
import ch.zhaw.rentmybike.model.entities.Adress;

@Service
public class RideService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideRepository rideRepository;
    
    // Ride erstellen
    public Ride createRide(CreateRideDTO createRideDTO, String ownerId) {
        // Prüfen, ob der User aktiv ist
        User owner = userRepository.findById(ownerId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (owner.getState() != UserState.ACTIVE) {
        throw new IllegalStateException("Ride creation is only allowed for active users.");
        }

        // Address-Objekt manuell aus DTO-Daten erstellen
        Adress adress = new Adress();
        adress.setCountry(createRideDTO.getCountry());
        adress.setCity(createRideDTO.getCity());
        adress.setPostalCode(createRideDTO.getPostalCode());
        adress.setStreet(createRideDTO.getStreet());
        adress.setStreetNumber(createRideDTO.getStreetNumber());

        // Neues Ride-Objekt erstellen
        Ride ride = new Ride();
        ride.setMotorcycleId(createRideDTO.getMotorcycleId()); // Falls motorcycleId nur String ist
        ride.setPickupAdress(adress);
        ride.setStartingTime(createRideDTO.getStartingTime());
        ride.setEndingTime(createRideDTO.getEndingTime());
        ride.setPrice(createRideDTO.getPrice());
        ride.setOwnerId(ownerId);
        ride.setStatus(RideStatus.AVAILABLE);

        return rideRepository.save(ride);
    }

    // Ride Status aktualisieren
    public Ride updateRideStatus(String rideId, RideStatus status) {
        Optional<Ride> rideOptional = rideRepository.findById(rideId);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            ride.setStatus(status);
            return rideRepository.save(ride);
        }
        return null;
    }

    // Filtern von verfügbaren Rides

    public List<Ride> getAvailableRidesWithFilters(String city, LocalDateTime startTime, LocalDateTime endTime, Integer minPrice, Integer maxPrice) {
        // Beginne mit allen verfügbaren Rides
        List<Ride> availableRides = rideRepository.findByStatus(RideStatus.AVAILABLE);

        // Filtere nach Stadt, falls angegeben
        if (city != null) {
            List<Ride> cityFilteredRides = rideRepository.findAvailableRidesByCity(city);
            availableRides = availableRides.stream()
                    .filter(cityFilteredRides::contains)
                    .collect(Collectors.toList());
        }

        // Filtere nach Startzeit, falls angegeben
        if (startTime != null) {
            List<Ride> startTimeFilteredRides = rideRepository.findAvailableRidesByStartTime(startTime);
            availableRides = availableRides.stream()
                    .filter(startTimeFilteredRides::contains)
                    .collect(Collectors.toList());
        }

        // Filtere nach Endzeit, falls angegeben
        if (endTime != null) {
            List<Ride> endTimeFilteredRides = rideRepository.findAvailableRidesByEndTime(endTime);
            availableRides = availableRides.stream()
                    .filter(endTimeFilteredRides::contains)
                    .collect(Collectors.toList());
        }

        // Filtere nach Mindestpreis, falls angegeben
        if (minPrice != null) {
            List<Ride> minPriceFilteredRides = rideRepository.findAvailableRidesByMinPrice(minPrice);
            availableRides = availableRides.stream()
                    .filter(minPriceFilteredRides::contains)
                    .collect(Collectors.toList());
        }

        // Filtere nach Höchstpreis, falls angegeben
        if (maxPrice != null) {
            List<Ride> maxPriceFilteredRides = rideRepository.findAvailableRidesByMaxPrice(maxPrice);
            availableRides = availableRides.stream()
                    .filter(maxPriceFilteredRides::contains)
                    .collect(Collectors.toList());
        }

        return availableRides;
    }
       
    

}
