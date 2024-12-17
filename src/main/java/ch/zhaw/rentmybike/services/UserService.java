package ch.zhaw.rentmybike.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
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

    // Erstellung eines neuen Benutzers
    public User createUser(CreateUserDTO createUserDTO) {
        // neue Adresse aus DTO-Daten erstellen
        Adress adress = new Adress();
        adress.setCountry(createUserDTO.getAdress().getCountry());
        adress.setCity(createUserDTO.getAdress().getCity());
        adress.setPostalCode(createUserDTO.getAdress().getPostalCode());
        adress.setStreet(createUserDTO.getAdress().getStreet());
        adress.setStreetNumber(createUserDTO.getAdress().getStreetNumber());
        
        // Neuen Benutzer erstellen und die Adresse setzen
        User user = new User();
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setEmail(createUserDTO.getEmail());
        user.setPhoneNumber(createUserDTO.getPhoneNumber());
        user.setLicenceCode(createUserDTO.getLicenseCode());
        user.setBirthdate(createUserDTO.getBirthdate());
        user.setState(UserState.NEW); 
        user.setAdress(adress); // Adresse direkt im Benutzer setzen

        return userRepository.save(user);
    }

    // Benutzer aktivieren / auf AKTIV setzen
    public Optional<User> activateUser(UserActivateDTO activateRequest) {
        Optional<User> userOptional = userRepository.findById(activateRequest.getUserId());
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (user.getState() == UserState.NEW) {
                user.setState(UserState.ACTIVE);
                userRepository.save(user);
                return Optional.of(user);
            } else {
                throw new IllegalStateException("Benutzer ist nicht in einem Zustand, der eine Aktivierung erlaubt.");
            }
        }
    
        return Optional.empty();
    }

    // alle Benutzer ausgeben
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Benutzer anhand der ID abrufen
    public User getUserById(String userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Benutzer nicht gefunden"));
    }

    // Benutzer anhand der E-Mail-Adresse abrufen
    public String getUserIdFromJwt(Jwt jwt) {
        String userEmail = jwt.getClaimAsString("email");
        User user = userRepository.findFirstByEmail(userEmail);
        if (user == null) {
            throw new UsernameNotFoundException("Kein Benutzer gefunden");
        }
        return user.getId();
    }

    // E-Mail-Adresse des angemeldeten Benutzers abrufen
    public String getEmail() {
        Jwt jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwt.getClaimAsString("email");
    }


    

}
