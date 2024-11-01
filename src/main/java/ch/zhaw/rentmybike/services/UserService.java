package ch.zhaw.rentmybike.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.rentmybike.dtos.UserDTO;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.model.entities.User.UserState;
import ch.zhaw.rentmybike.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setState(UserState.NEW);
        return userRepository.save(user);
    }

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
