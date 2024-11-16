package ch.zhaw.rentmybike.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ch.zhaw.rentmybike.model.dtos.CreateUserDTO;
import ch.zhaw.rentmybike.model.dtos.UserActivateDTO;
import ch.zhaw.rentmybike.model.entities.Adress;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.model.entities.User.UserState;
import ch.zhaw.rentmybike.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // Erstellung eines neuen Users
    public User createUser(CreateUserDTO createUserDTO) {
        //neue Adresse aus DTO-Daten erstellen
        Adress adress = new Adress();
        adress.setCountry(createUserDTO.getAdress().getCountry());
        adress.setCity(createUserDTO.getAdress().getCity());
        adress.setPostalCode(createUserDTO.getAdress().getPostalCode());
        adress.setStreet(createUserDTO.getAdress().getStreet());
        adress.setStreetNumber(createUserDTO.getAdress().getStreetNumber());
        
        // Neuen User erstellen und die Adresse-Id setzen
        User user = new User();
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setEmail(createUserDTO.getEmail());
        user.setPhoneNumber(createUserDTO.getPhoneNumber());
        user.setLicenceCode(createUserDTO.getLicenseCode());
        user.setBirthdate(createUserDTO.getBirthdate());
        user.setState(UserState.NEW); 
        user.setAdress(adress); // Adresse direkt im User setzen

        return userRepository.save(user);
    }


    // User aktivieren / auf ACTIVE setzen
    public Optional<User> activateUser(UserActivateDTO activateRequest) {
        Optional<User> userOptional = userRepository.findById(activateRequest.getUserId());
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (user.getState() == UserState.NEW) {
                user.setState(UserState.ACTIVE);
                userRepository.save(user);
                return Optional.of(user);
            } else {
                throw new IllegalStateException("User is not in a state that allows activation.");
            }
        }
    
            return Optional.empty();
        }

    // alle User ausgeben
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Benutzer anhand der ID abrufen
    public User getUserById(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
