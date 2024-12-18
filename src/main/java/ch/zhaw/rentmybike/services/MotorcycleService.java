package ch.zhaw.rentmybike.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.rentmybike.model.dtos.CreateMotorcycleDTO;
import ch.zhaw.rentmybike.model.entities.Motorcycle;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.repository.MotorcycleRepository;
import ch.zhaw.rentmybike.repository.UserRepository;

@Service
public class MotorcycleService {

    @Autowired 
    private UserService userService;

    @Autowired
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private UserRepository userRepository;

    // Motorrad hinzufügen
    public Motorcycle createMotorcycle(CreateMotorcycleDTO motorcycleDTO) {
        // Benutzer-E-Mail mithilfe des UserService auslesen
        String userEmail = userService.getEmail();
        User user = userRepository.findFirstByEmail(userEmail);
    
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
    
        // Neues Motorrad erstellen
        Motorcycle motorcycle = new Motorcycle();
        motorcycle.setBrand(motorcycleDTO.getBrand());
        motorcycle.setModel(motorcycleDTO.getModel());
        motorcycle.setYear(motorcycleDTO.getYear());
        motorcycle.setColor(motorcycleDTO.getColor());
        motorcycle.setLicensePlate(motorcycleDTO.getLicensePlate());
        motorcycle.setValue(motorcycleDTO.getValue());
        motorcycle.setPs(motorcycleDTO.getPs());
        motorcycle.setKm(motorcycleDTO.getKm());
        motorcycle.setUserId(user.getId()); // Benutzer-ID aus JWT setzen
    
        // Motorrad speichern
        Motorcycle savedMotorcycle = motorcycleRepository.save(motorcycle);
    
        // Motorrad-ID zur Benutzer-Liste hinzufügen
        user.getMotorcycleIds().add(savedMotorcycle.getId());
        userRepository.save(user);
    
        return savedMotorcycle;
    }
    

    public Motorcycle updateMotorcycle(String motorcycleId, CreateMotorcycleDTO motorcycleDTO) {
        Optional<Motorcycle> existingMotorcycle = motorcycleRepository.findById(motorcycleId);
    
        if (existingMotorcycle.isPresent()) {
            // Benutzer-ID aus dem JWT-Token extrahieren
            String userEmail = userService.getEmail();
            User user = userRepository.findFirstByEmail(userEmail);
    
            if (user == null) {
                throw new IllegalArgumentException("User not found");
            }
    
            // Motorrad aktualisieren
            Motorcycle motorcycle = existingMotorcycle.get();
            motorcycle.setBrand(motorcycleDTO.getBrand());
            motorcycle.setModel(motorcycleDTO.getModel());
            motorcycle.setYear(motorcycleDTO.getYear());
            motorcycle.setColor(motorcycleDTO.getColor());
            motorcycle.setLicensePlate(motorcycleDTO.getLicensePlate());
            motorcycle.setValue(motorcycleDTO.getValue());
            motorcycle.setPs(motorcycleDTO.getPs());
            motorcycle.setKm(motorcycleDTO.getKm());
    
            // Benutzer-ID setzen
            motorcycle.setUserId(user.getId());
    
            return motorcycleRepository.save(motorcycle);
        }
    
        throw new IllegalArgumentException("Motorcycle not found");
    }
    

    // Motorrad löschen
    public Optional<String> deleteMotorcycleById(String id) {
        Optional<Motorcycle> motorcycle = motorcycleRepository.findById(id);

        if (motorcycle.isPresent()) {
            motorcycleRepository.delete(motorcycle.get());
            return Optional.of("Motorcycle successfully deleted");
        }
        return Optional.empty(); // Motorrad nicht gefunden
    }
}