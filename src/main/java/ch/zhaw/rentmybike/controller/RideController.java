package ch.zhaw.rentmybike.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.rentmybike.model.dtos.CreateRideDTO;
import ch.zhaw.rentmybike.model.entities.Ride;
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
    public ResponseEntity<Ride> createRide(@RequestBody CreateRideDTO createRideDTO) {
        Ride newRide = rideService.createRide(createRideDTO);
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



    // Alle verfügbaren Rides abrufen
    @GetMapping("/status/available")
    public ResponseEntity<Page<Ride>> getAvailableRides(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "9") Integer pageSize) {
    
    
        // Rufe den Service auf, um die gefilterten Rides zu erhalten
        Page<Ride> availableRides = rideService.getAvailableRidesWithFilters(city, startTime, endTime, minPrice, maxPrice, pageNumber, pageSize);
    

        if(availableRides == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
         }
         
         return new ResponseEntity<>(availableRides, HttpStatus.OK);
      }
    

    
    // Ride nach ID löschen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRideById(@PathVariable String id) {
        Optional<Ride> ride = rideRepository.findById(id);
        if (ride.isPresent()) {
            rideRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
