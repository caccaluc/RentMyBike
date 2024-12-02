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
import ch.zhaw.rentmybike.repository.MotorcycleRepository;
import ch.zhaw.rentmybike.repository.RideRepository;
import ch.zhaw.rentmybike.repository.UserRepository;
import ch.zhaw.rentmybike.model.entities.Adress;
import ch.zhaw.rentmybike.model.entities.Motorcycle;

@Service
public class RideService {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideRepository rideRepository;

    // Ride erstellen
    public Ride createRide(CreateRideDTO createRideDTO) {
        // Motorrad anhand der ID abrufen
        Motorcycle motorcycle = motorcycleRepository.findById(createRideDTO.getMotorcycleId())
                .orElseThrow(() -> new IllegalArgumentException("Motorcycle not found"));

        // User, der Besitzer des Motorrads ist, abrufen
        User user = userRepository.findById(motorcycle.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Owner not found"));

        // Prüfen, ob der User aktiv ist
        if (user.getState() != UserState.ACTIVE) {
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
        ride.setOwnerId(motorcycle.getUserId()); // Setze die OwnerId aus dem Motorcycle-Objekt
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

    public List<Ride> getAvailableRidesWithFilters(String city, LocalDateTime startTime, LocalDateTime endTime,
            Integer minPrice, Integer maxPrice) {
        // Beginne mit allen verfügbaren Rides
        List<Ride> filteredRides = rideRepository.findByStatus(RideStatus.AVAILABLE);

        // Falls Stadt gegeben, filtern wir direkt nach Stadt in der Datenbank
        if (city != null && !city.isEmpty()) {
            filteredRides = rideRepository.findAvailableRidesByCity(city);
        } else {
            // Starte mit allen verfügbaren Rides, falls Stadt nicht spezifiziert ist
            filteredRides = rideRepository.findByStatus(RideStatus.AVAILABLE);
        }

        // Falls Startzeit angegeben, weiter filtern nach Startzeit
        if (startTime != null) {
            filteredRides = rideRepository.findAvailableRidesByStartTime(startTime)
                    .stream()
                    .filter(filteredRides::contains)
                    .collect(Collectors.toList());
        }

        // Falls Endzeit angegeben, weiter filtern nach Endzeit
        if (endTime != null) {
            filteredRides = rideRepository.findAvailableRidesByEndTime(endTime)
                    .stream()
                    .filter(filteredRides::contains)
                    .collect(Collectors.toList());
        }

        // Falls Mindestpreis gegeben, weiter filtern nach Mindestpreis
        if (minPrice != null) {
            filteredRides = rideRepository.findAvailableRidesByMinPrice(minPrice)
                    .stream()
                    .filter(filteredRides::contains)
                    .collect(Collectors.toList());
        }

        // Falls Höchstpreis gegeben, weiter filtern nach Höchstpreis
        if (maxPrice != null) {
            filteredRides = rideRepository.findAvailableRidesByMaxPrice(maxPrice)
                    .stream()
                    .filter(filteredRides::contains)
                    .collect(Collectors.toList());
        }

        return filteredRides;
    }

}
