package ch.zhaw.rentmybike.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.repository.UserRepository;
import ch.zhaw.rentmybike.services.RideService;
import ch.zhaw.rentmybike.services.UserService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import ch.zhaw.rentmybike.model.entities.Motorcycle;
import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.repository.MotorcycleRepository;
import ch.zhaw.rentmybike.repository.RideRepository;

@RestController
@RequestMapping("api/me")
public class MeController {

    @Autowired
    private UserService userService;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideService rideService;

    // Einen User anhand des JWTs zurückgeben
    @GetMapping("/user")
    public ResponseEntity<User> getMe(@AuthenticationPrincipal Jwt jwt) {
        String userEmail = jwt.getClaimAsString("email");

        User user = userRepository.findFirstByEmail(userEmail);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Alle Vermietungen eines Users zurückgeben
    @GetMapping("/rentals")
    public ResponseEntity<Page<Ride>> getMyRides(@AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        String userEmail = jwt.getClaimAsString("email");
        User user = userRepository.findFirstByEmail(userEmail);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Page<Ride> myRides = rideRepository.findByOwnerId(user.getId(), PageRequest.of(pageNumber - 1, pageSize));
        return new ResponseEntity<>(myRides, HttpStatus.OK);
    }

    // Alle Mietungen eines Users zurückgeben
    @GetMapping("/rides")
    public ResponseEntity<Page<Ride>> getMyRentals(@AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "5") Integer pageSize) {
        String userEmail = jwt.getClaimAsString("email");
        User user = userRepository.findFirstByEmail(userEmail);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Page<Ride> myRentals = rideRepository.findByRenterId(user.getId(), PageRequest.of(pageNumber - 1, pageSize));
        return new ResponseEntity<>(myRentals, HttpStatus.OK);
    }

    // Benutzerdaten aktualisieren
    @PutMapping("/user")
    public ResponseEntity<User> updateMe(@AuthenticationPrincipal Jwt jwt, @RequestBody User user) {
        String userEmail = jwt.getClaimAsString("email");
        User existingUser = userRepository.findFirstByEmail(userEmail);

        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setBirthdate(user.getBirthdate());
        existingUser.setAdress(user.getAdress());

        User updatedUser = userRepository.save(existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);

    }

    // Get all bikes of an owner
    @GetMapping("/motorcycles")
    public ResponseEntity<List<Motorcycle>> getMyMotorcycles(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        User user = userRepository.findFirstByEmail(email);

        if (user != null) {
            List<Motorcycle> motorcycles = motorcycleRepository.findByUserId(user.getId());
            return new ResponseEntity<>(motorcycles, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        }

    // Löschen eines eigenen Motorrads
    @DeleteMapping("/motorcycles/{id}")
    public ResponseEntity<Void> deleteMotorcycle(@PathVariable String id) {
        String email = userService.getEmail();
        User user = userRepository.findFirstByEmail(email);

        if (user != null) {
            Optional<Motorcycle> motorcycle = motorcycleRepository.findById(id);
            if (motorcycle.isPresent()) {
                if (motorcycle.get().getUserId().equals(user.getId())) {
                    motorcycleRepository.deleteById(id);
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Buchung eines Rides
    @PutMapping("/bookRide")
    public ResponseEntity<Ride> bookRide(@RequestParam String rideId) {
        String userEmail = userService.getEmail();
        Optional<Ride> ride = rideService.bookRide(rideId, userEmail);
        if (ride.isPresent()) {
            return new ResponseEntity<>(ride.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Abschluss eines Rides
    @PutMapping("/completeRide")
    public ResponseEntity<Ride> completeRide(@RequestParam String rideId) {
        String userEmail = userService.getEmail();
        Optional<Ride> ride = rideService.completeRide(rideId, userEmail);
        if (ride.isPresent()) {
            return new ResponseEntity<>(ride.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




 
}
