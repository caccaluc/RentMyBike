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
import ch.zhaw.rentmybike.services.RoleService;
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
    @Autowired
    private RoleService roleService;

    // User aktivieren
    @PutMapping("/activateUser")
    public ResponseEntity<User> activateUser(@RequestBody UserActivateDTO activateRequest) {

        if (!(roleService.userHasRole("admin"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<User> user = userService.activateUser(activateRequest);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // User deaktivieren
    @PutMapping("/deactivateUser")
    public ResponseEntity<User> deactivateUser(@RequestBody UserActivateDTO deactivateRequest) {
        Optional<User> user = userService.deactivateUser(deactivateRequest);

        if (!(roleService.userHasRole("admin"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Ride-Status aktualisieren
    @PutMapping("/rides/{id}")
    public ResponseEntity<Ride> updateRideStatus(@PathVariable String id, @RequestParam RideStatus status) {

        if (!(roleService.userHasRole("user") || roleService.userHasRole("admin"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Ride updatedRide = rideService.updateRideStatus(id, status);
        if (updatedRide != null) {
            return ResponseEntity.ok(updatedRide);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Aktualisieren eines bestehenden Motorrads
    @PutMapping("/motorcycles/{id}")
    public ResponseEntity<Motorcycle> updateMotorcycle(@PathVariable String id, @RequestBody CreateMotorcycleDTO motorcycleDTO) {

        if (!(roleService.userHasRole("user") || roleService.userHasRole("admin"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Motorcycle updatedMotorcycle = motorcycleService.updateMotorcycle(id, motorcycleDTO);
        if (updatedMotorcycle != null) {
            return ResponseEntity.ok(updatedMotorcycle);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    

}
