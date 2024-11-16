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
    private MotorcycleRepository motorcycleRepository;

    @Autowired
    private UserRepository userRepository;

    // Motorrad hinzufügen
    public Motorcycle createMotorcycle(CreateMotorcycleDTO motorcycleDTO) {
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
        motorcycle.setUserId(motorcycleDTO.getUserId());

        // Motorrad speichern
        Motorcycle savedMotorcycle = motorcycleRepository.save(motorcycle);

        // Benutzer finden und Motorrad-ID hinzufügen
        Optional<User> userOptional = userRepository.findById(motorcycleDTO.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.getMotorcycleIds().add(savedMotorcycle.getId());
            userRepository.save(user);
        }

        return savedMotorcycle;
    }

    // Motorrad aktualisieren
    public Motorcycle updateMotorcycle(String motorcycleId, CreateMotorcycleDTO motorcycleDTO) {
        Optional<Motorcycle> existingMotorcycle = motorcycleRepository.findById(motorcycleId);
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
            return motorcycleRepository.save(motorcycle);
        }
        return null;
    }
}