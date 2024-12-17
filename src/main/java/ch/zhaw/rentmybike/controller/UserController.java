package ch.zhaw.rentmybike.controller;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.rentmybike.model.dtos.CreateUserDTO;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.model.entities.User.UserState;
import ch.zhaw.rentmybike.repository.UserRepository;
import ch.zhaw.rentmybike.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    // alle User ausgeben
    @GetMapping("/all")
    public ResponseEntity<Page<User>> getAllUsers(
        @RequestParam(required = false) UserState state,
        @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
        @RequestParam(required = false, defaultValue = "9") Integer pageSize){
        Page<User> allUsers;
        if (state == null) {
            allUsers = userRepository.findAll(PageRequest.of(pageNumber - 1, pageSize));
        } else {
            allUsers = userRepository.findByState(state, PageRequest.of(pageNumber - 1, pageSize));
        }
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    // User-Daten anhand der ID ausgeben
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            return new ResponseEntity<>(optUser.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Registrierung eines neuen Users
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        User user = userService.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // User anhand der ID löschen
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUserById(@PathVariable String userId) {
        Optional<User> optUser = userRepository.findById(userId);
        if (optUser.isPresent()) {
            userRepository.deleteById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

