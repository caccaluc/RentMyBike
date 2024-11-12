package ch.zhaw.rentmybike.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.rentmybike.model.dtos.CreateMotorcycleDTO;
import ch.zhaw.rentmybike.model.entities.Motorcycle;
import ch.zhaw.rentmybike.repository.MotorcycleRepository;

@RestController
@RequestMapping("/api/motorcycles")
public class MotorcycleController {

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    // Erstellen eines neuen Motorrads
    @PostMapping("/create")
    public ResponseEntity<Motorcycle> createMotorcycle(@RequestBody CreateMotorcycleDTO motorcycleDTO) {
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setBrand(motorcycleDTO.getBrand());
        motorcycle.setModel(motorcycleDTO.getModel());
        motorcycle.setYear(motorcycleDTO.getYear());
        motorcycle.setColor(motorcycleDTO.getColor());
        motorcycle.setLicensePlate(motorcycleDTO.getLicensePlate());
        motorcycle.setValue(motorcycleDTO.getValue());
        motorcycle.setPs(motorcycleDTO.getPs());
        motorcycle.setKm(motorcycleDTO.getKm());
        motorcycle.setUserId(motorcycleDTO.getUserId());

        Motorcycle savedMotorcycle = motorcycleRepository.save(motorcycle);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMotorcycle);
    }
    
    // Aktualisieren eines bestehenden Motorrads
    @PutMapping("/{id}")
    public ResponseEntity<Motorcycle> updateMotorcycle(@PathVariable String id, @RequestBody CreateMotorcycleDTO motorcycleDTO) {
        Optional<Motorcycle> existingMotorcycle = motorcycleRepository.findById(id);
        if (existingMotorcycle.isPresent()) {
            Motorcycle motorcycle = existingMotorcycle.get();
            motorcycle.setBrand(motorcycleDTO.getBrand());
            motorcycle.setModel(motorcycleDTO.getModel());
            motorcycle.setYear(motorcycleDTO.getYear());
            motorcycle.setColor(motorcycleDTO.getColor());
            motorcycle.setLicensePlate(motorcycleDTO.getLicensePlate());
            motorcycle.setValue(motorcycleDTO.getValue());
            motorcycle.setPs(motorcycleDTO.getPs());
            motorcycle.setKm(motorcycleDTO.getKm());
            motorcycle.setUserId(motorcycleDTO.getUserId());

            Motorcycle updatedMotorcycle = motorcycleRepository.save(motorcycle);
            return ResponseEntity.ok(updatedMotorcycle);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Löschen eines Motorrads
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMotorcycle(@PathVariable String id) {
        if (motorcycleRepository.existsById(id)) {
            motorcycleRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}