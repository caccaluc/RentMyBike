package ch.zhaw.rentmybike.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.rentmybike.model.dtos.CreateRideDTO;
import ch.zhaw.rentmybike.model.entities.Ride;
import ch.zhaw.rentmybike.model.entities.Ride.RideStatus;
import ch.zhaw.rentmybike.repository.RideRepository;
import ch.zhaw.rentmybike.model.entities.Adress;

@Service
public class RideService {

    @Autowired
    private RideRepository rideRepository;
    
    
    public Ride createRide(CreateRideDTO createRideDTO, String ownerId) {
    // Address-Objekt manuell aus DTO-Daten erstellen
    Adress adress = new Adress();
    adress.setCountry(createRideDTO.getCountry());
    adress.setCity(createRideDTO.getCity());
    adress.setPostalCode(createRideDTO.getPostalCode());
    adress.setStreet(createRideDTO.getStreet());
    adress.setStreetNumber(createRideDTO.getStreetNumber());

    // Neues Ride-Objekt erstellen
    Ride ride = new Ride();
    ride.setMotorcycleId(createRideDTO.getMotorcycleId()); // Falls motorcycleId nur String ist
    ride.setPickupAdress(adress);
    ride.setStartingTime(createRideDTO.getStartingTime());
    ride.setEndingTime(createRideDTO.getEndingTime());
    ride.setPrice(createRideDTO.getPrice());
    ride.setOwnerId(ownerId);
    ride.setStatus(RideStatus.AVAILABLE);

    return rideRepository.save(ride);
}

    



}
