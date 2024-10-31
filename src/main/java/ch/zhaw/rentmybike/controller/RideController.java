package ch.zhaw.rentmybike.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.model.entities.Ride.RideStatus;
import ch.zhaw.rentmybike.repository.RideRepository;

@RestController
@RequestMapping("/rides")
public class RideController {

    @Autowired
    private RideRepository rideRepository;
    
    // Erstellen eines neuen Ride
    @PostMapping("/create")
    public ResponseEntity<Ride> createRide(@RequestBody Ride ride) {
        Ride newRide = rideRepository.save(ride);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRide);
    }

     // Alle Rides abrufen
    @GetMapping
    public List<Ride> getAllRides() {
        return rideRepository.findAll();
    }

    // Ride nach ID abrufen
    @GetMapping("/{id}")
    public ResponseEntity<Ride> getRideById(@PathVariable String id) {
        Optional<Ride> ride = rideRepository.findById(id);
        return ride.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Rides eines bestimmten Vermieters abrufen
    @GetMapping("/owner/{ownerId}")
    public List<Ride> getRidesByOwnerId(@PathVariable String ownerId) {
        return rideRepository.findByOwnerId(ownerId);
    }

    // Ride nach Status abrufen
    @GetMapping("/status/{status}")
    public List<Ride> getRidesByStatus(@PathVariable RideStatus status) {
        return rideRepository.findByStatus(status);
    }

    // Ride-Status aktualisieren
    @PutMapping("/{id}/status")
    public ResponseEntity<Ride> updateRideStatus(@PathVariable String id, @RequestParam RideStatus status) {
        Optional<Ride> rideOptional = rideRepository.findById(id);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            ride.setStatus(status);
            rideRepository.save(ride);
            return ResponseEntity.ok(ride);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Ride beenden (Status auf COMPLETED setzen)
    @PutMapping("/{id}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String id) {
        Optional<Ride> rideOptional = rideRepository.findById(id);
        if (rideOptional.isPresent()) {
            Ride ride = rideOptional.get();
            ride.setStatus(RideStatus.COMPLETED);
            ride.setEndingTime(LocalDateTime.now());
            rideRepository.save(ride);
            return ResponseEntity.ok(ride);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
