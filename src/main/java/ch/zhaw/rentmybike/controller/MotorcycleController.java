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

@RestController
@RequestMapping("/api/motorcycles")
public class MotorcycleController {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private MotorcycleService motorcycleService;

    // Erstellen eines neuen Motorrads
    @PostMapping("/create")
    public ResponseEntity<Motorcycle> createMotorcycle(@RequestBody CreateMotorcycleDTO motorcycleDTO) {
        Motorcycle savedMotorcycle = motorcycleService.createMotorcycle(motorcycleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMotorcycle);
    }

    // Abrufen eines Motorrads basierend auf der ID
    @GetMapping("/{id}")
    public ResponseEntity<Motorcycle> getMotorcycleById(@PathVariable String id) {
        Optional<Motorcycle> motorcycle = motorcycleRepository.findById(id);
        return motorcycle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

     // Abrufen aller Motorräder eines bestimmten Nutzers
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Motorcycle>> getMotorcyclesByUserId(@PathVariable String userId) {
        List<Motorcycle> motorcycles = motorcycleRepository.findByUserId(userId);
        return ResponseEntity.ok(motorcycles);
    }


    // Löschen eines Motorrads
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMotorcycle(@PathVariable String id) {
        Optional<String> result = motorcycleService.deleteMotorcycleById(id);

        return result
                .map(message -> ResponseEntity.status(HttpStatus.OK).body(message))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Motorcycle not found"));
    }
}
