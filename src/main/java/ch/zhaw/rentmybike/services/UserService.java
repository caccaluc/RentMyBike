package ch.zhaw.rentmybike.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.rentmybike.model.dtos.CreateUserDTO;
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
        User user = new User();
        user.setFirstName(createUserDTO.getFirstName());
        user.setLastName(createUserDTO.getLastName());
        user.setEmail(createUserDTO.getEmail());
        user.setPhoneNumber(createUserDTO.getPhoneNumber());
        user.setLicenceCode(createUserDTO.getLicenseCode());
        Adress address = new Adress();
        address.setId(createUserDTO.getAddressId());
        user.setAdressID(address);
        user.setBirthdate(createUserDTO.getBirthdate());
        user.setState(UserState.NEW); 
        return userRepository.save(user);
    }


    // User verifizieren
    public User verifyUser(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (user.getState() == UserState.NEW) {
            user.setState(UserState.ACTIVE);
            return userRepository.save(user);
        }
        throw new IllegalStateException("User is not in a verifiable state");
    }

}
