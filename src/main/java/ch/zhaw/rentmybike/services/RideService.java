package ch.zhaw.rentmybike.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                .orElseThrow(() -> new IllegalArgumentException("Motorrad nicht gefunden"));

        // User, der Besitzer des Motorrads ist, abrufen
        User user = userRepository.findById(motorcycle.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Besitzer nicht gefunden"));

        // Prüfen, ob der User aktiv ist
        if (user.getState() != UserState.ACTIVE) {
            throw new IllegalStateException("Nut aktive Benutzer können eine Vermietung erstellen.");
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
    public Page<Ride> getAvailableRidesWithFilters(String city, LocalDateTime startTime, LocalDateTime endTime,
            Integer minPrice, Integer maxPrice, Integer pageNumber, Integer pageSize) {

        // Beginne mit allen verfügbaren Rides
        Page<Ride> filteredRides;

        // All combinations of search parameters
        if (city == null && startTime == null && endTime == null && minPrice == null && maxPrice == null) {
            filteredRides = rideRepository.findByStatus(RideStatus.AVAILABLE, PageRequest.of(pageNumber - 1, pageSize));
        } else {
            if (city != null && startTime != null && endTime != null && minPrice != null && maxPrice != null) {
                filteredRides = rideRepository
                        .findAvailableRidesByCityAndStartTimeBetweenAndEndTimeBetweenAndPriceBetween(
                                city, startTime, endTime, minPrice, maxPrice, PageRequest.of(pageNumber - 1, pageSize));
            } else if (city != null && startTime != null && endTime != null && minPrice != null) {
                filteredRides = rideRepository.findAvailableRidesByCityAndStartTimeBetweenAndEndTimeBetweenAndMinPrice(
                        city, startTime, endTime, minPrice, PageRequest.of(pageNumber - 1, pageSize));
            } else if (city != null && startTime != null && endTime != null) {
                filteredRides = rideRepository.findAvailableRidesByCityAndStartTimeBetweenAndEndTimeBetween(
                        city, startTime, endTime, PageRequest.of(pageNumber - 1, pageSize));
            } else if (city != null && startTime != null) {
                filteredRides = rideRepository.findAvailableRidesByCityAndStartTime(
                        city, startTime, PageRequest.of(pageNumber - 1, pageSize));
            } else if (city != null) {
                filteredRides = rideRepository.findAvailableRidesByCity(
                        city, PageRequest.of(pageNumber - 1, pageSize));
            } else if (startTime != null && endTime != null && minPrice != null && maxPrice != null) {
                filteredRides = rideRepository.findAvailableRidesByStartTimeBetweenAndEndTimeBetweenAndPriceBetween(
                        startTime, endTime, minPrice, maxPrice, PageRequest.of(pageNumber - 1, pageSize));
            } else if (startTime != null && endTime != null && minPrice != null) {
                filteredRides = rideRepository.findAvailableRidesByStartTimeBetweenAndEndTimeBetweenAndMinPrice(
                        startTime, endTime, minPrice, PageRequest.of(pageNumber - 1, pageSize));
            } else if (startTime != null && endTime != null) {
                filteredRides = rideRepository.findAvailableRidesByStartTimeBetweenAndEndTimeBetween(
                        startTime, endTime, PageRequest.of(pageNumber - 1, pageSize));
            } else if (startTime != null && minPrice != null && maxPrice != null) {
                // Filtere nach Startzeit und einem Preisbereich
                filteredRides = rideRepository.findAvailableRidesByStartTimeAndPriceBetween(
                        startTime, minPrice, maxPrice, PageRequest.of(pageNumber - 1, pageSize));
            } else if (startTime != null && minPrice != null) {
                // Filtere nach Startzeit und Mindestpreis
                filteredRides = rideRepository.findAvailableRidesByStartTimeAndMinPrice(
                        startTime, minPrice, PageRequest.of(pageNumber - 1, pageSize));
            } else if (startTime != null && maxPrice != null) {
                // Filtere nach Startzeit und Höchstpreis
                filteredRides = rideRepository.findAvailableRidesByStartTimeAndMaxPrice(
                        startTime, maxPrice, PageRequest.of(pageNumber - 1, pageSize));
            } else if (startTime != null) {
                // Filtere nur nach Startzeit
                filteredRides = rideRepository.findAvailableRidesByStartTime(
                        startTime, PageRequest.of(pageNumber - 1, pageSize));
            } else if (minPrice != null) {
                filteredRides = rideRepository.findAvailableRidesByMinPrice(
                        minPrice, PageRequest.of(pageNumber - 1, pageSize));
            } else if (maxPrice != null) {
                filteredRides = rideRepository.findAvailableRidesByMaxPrice(
                        maxPrice, PageRequest.of(pageNumber - 1, pageSize));
            } else if (endTime != null) {
                filteredRides = rideRepository.findAvailableRidesByEndTime(
                        endTime, PageRequest.of(pageNumber - 1, pageSize));
            }

            else {
                filteredRides = rideRepository.findByStatus(RideStatus.AVAILABLE,
                        PageRequest.of(pageNumber - 1, pageSize));
            }

        }
        return filteredRides;

    }

    // Ride Status auf BOOKED setzen, wenn der aktuelle Status AVAILABLE ist
    public Optional<Ride> bookRide(String rideId, String renterEmail) {
        // Benutzer anhand der E-Mail abrufen
        User renter = userRepository.findFirstByEmail(renterEmail);

        // Ride abrufen
        Optional<Ride> rideOptional = rideRepository.findById(rideId);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();

            // Prüfen, ob der Status AVAILABLE ist
            if (ride.getStatus() == RideStatus.AVAILABLE) {
                ride.setRenterId(renter.getId()); // Hier die User-ID setzen
                ride.setStatus(RideStatus.BOOKED);

                // Ride speichern
                rideRepository.save(ride);
                return Optional.of(ride);
            }
        }
        return Optional.empty();
    }

    // Ride Status auf COMPLETED setzen, wenn der aktuelle Status BOOKED ist
    public Optional<Ride> completeRide(String rideId, String ownerId) {
        Optional<Ride> rideOptional = rideRepository.findById(rideId);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            if (ride.getStatus() == RideStatus.BOOKED) {
                ride.setStatus(RideStatus.COMPLETED);
                rideRepository.save(ride);
                return Optional.of(ride);
            }
        }
        return Optional.empty();
    }

}
