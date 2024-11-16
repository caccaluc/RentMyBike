package ch.zhaw.rentmybike.services;

import java.time.LocalDateTime;
import java.util.Optional;

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

    // Ride Status auf COMPLETED setzen
    public Ride completeRide(String rideId) {
        Optional<Ride> rideOptional = rideRepository.findById(rideId);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            ride.setStatus(RideStatus.COMPLETED);
            ride.setEndingTime(LocalDateTime.now());
            return rideRepository.save(ride);
        }
        return null;
    }
    
    

}
