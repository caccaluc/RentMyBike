package ch.zhaw.rentmybike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.repository.RideRepository;

@RestController
@RequestMapping("/rides")
public class RideController {

    @Autowired
    private RideRepository rideRepository;
    
    // 1. Erstellen eines neuen Ride
    @PostMapping("/create")
    public ResponseEntity<Ride> createRide(@RequestBody Ride ride) {
        Ride newRide = rideRepository.save(ride);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRide);
    }


}
