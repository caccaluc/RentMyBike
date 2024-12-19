package ch.zhaw.rentmybike.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.rentmybike.model.dtos.CreateMotorcycleDTO;
import ch.zhaw.rentmybike.model.entities.Motorcycle;
import ch.zhaw.rentmybike.repository.MotorcycleRepository;
import ch.zhaw.rentmybike.services.MotorcycleService;
import ch.zhaw.rentmybike.services.RoleService;

@RestController
@RequestMapping("/api/motorcycles")
public class MotorcycleController {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private MotorcycleService motorcycleService;

    @Autowired
    private RoleService roleService;

    // Erstellen eines neuen Motorrads
    @PostMapping("/create")
    public ResponseEntity<Motorcycle> createMotorcycle(@RequestBody CreateMotorcycleDTO motorcycleDTO) {

        if (!(roleService.userHasRole("user") )) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Motorcycle savedMotorcycle = motorcycleService.createMotorcycle(motorcycleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMotorcycle);
    }
    // Abrufen aller Motorräder
    @GetMapping
    public ResponseEntity<List<Motorcycle>> getAllMotorcycles() {

        if (!(roleService.userHasRole("admin"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        List<Motorcycle> motorcycles = motorcycleRepository.findAll();
        return ResponseEntity.ok(motorcycles);
    }
    // Abrufen eines Motorrads basierend auf der ID
    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> getMotorcycleById(@PathVariable String id) {

        if (!(roleService.userHasRole("user") || roleService.userHasRole("admin"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Optional<Motorcycle> motorcycle = motorcycleRepository.findById(id);
        return motorcycle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

     // Abrufen aller Motorräder eines bestimmten Nutzers
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Motorcycle>> getMotorcyclesByUserId(@PathVariable String userId) {


        if (!(roleService.userHasRole("admin"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Motorcycle> motorcycles = motorcycleRepository.findByUserId(userId);
        return ResponseEntity.ok(motorcycles);
    }


    // Löschen eines Motorrads
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMotorcycle(@PathVariable String id) {

        if (!(roleService.userHasRole("user") || roleService.userHasRole("admin"))) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Optional<String> motorcycle = motorcycleService.deleteMotorcycleById(id);
        if (motorcycle.isPresent()) {
            motorcycleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
