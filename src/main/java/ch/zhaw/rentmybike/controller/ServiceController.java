package ch.zhaw.rentmybike.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.rentmybike.model.dtos.CreateMotorcycleDTO;
import ch.zhaw.rentmybike.model.dtos.UserActivateDTO;
import ch.zhaw.rentmybike.model.entities.Motorcycle;
import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.model.entities.Ride.RideStatus;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.services.MotorcycleService;
import ch.zhaw.rentmybike.services.RideService;
import ch.zhaw.rentmybike.services.UserService;

@RestController
@RequestMapping("/api/service")
public class ServiceController {

    @Autowired
    private MotorcycleService motorcycleService;
    @Autowired
    private UserService userService;
     @Autowired
    private RideService rideService;

    // User aktivieren
    @PutMapping("/activateUser")
    public ResponseEntity<User> activateUser(@RequestBody UserActivateDTO activateRequest) {
        Optional<User> user = userService.activateUser(activateRequest);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Ride-Status aktualisieren
    @PutMapping("/rides/{id}")
    public ResponseEntity<Ride> updateRideStatus(@PathVariable String id, @RequestParam RideStatus status) {
        Ride updatedRide = rideService.updateRideStatus(id, status);
        if (updatedRide != null) {
            return ResponseEntity.ok(updatedRide);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Ride beenden
    @PutMapping("/rides/{id}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String id) {
        Ride completedRide = rideService.completeRide(id);
        if (completedRide != null) {
            return ResponseEntity.ok(completedRide);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Aktualisieren eines bestehenden Motorrads
    @PutMapping("/motorcycles/{id}")
    public ResponseEntity<Motorcycle> updateMotorcycle(@PathVariable String id, @RequestBody CreateMotorcycleDTO motorcycleDTO) {
        Motorcycle updatedMotorcycle = motorcycleService.updateMotorcycle(id, motorcycleDTO);
        if (updatedMotorcycle != null) {
            return ResponseEntity.ok(updatedMotorcycle);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    

}
