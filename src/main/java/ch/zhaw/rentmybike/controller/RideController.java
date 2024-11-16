package ch.zhaw.rentmybike.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.rentmybike.model.dtos.CreateRideDTO;
import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.model.entities.Ride.RideStatus;
import ch.zhaw.rentmybike.repository.RideRepository;
import ch.zhaw.rentmybike.services.RideService;


@RestController
@RequestMapping("api/rides")
public class RideController {

    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private RideService rideService;
    
    // Erstellen eines neuen Ride
   @PostMapping("/create")
    public ResponseEntity<Ride> createRide(@RequestBody CreateRideDTO createRideDTO, @RequestParam String ownerId) {
        Ride newRide = rideService.createRide(createRideDTO, ownerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRide);
    }

     // Alle Rides abrufen
    @GetMapping("/all")
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

    // Ride nach Status abrufen
    @GetMapping("/status/{status}")
    public List<Ride> getRidesByStatus(@PathVariable RideStatus status) {
        return rideRepository.findByStatus(status);
    }

    
    
}
