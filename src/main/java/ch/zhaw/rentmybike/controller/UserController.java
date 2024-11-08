package ch.zhaw.rentmybike.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.rentmybike.model.dtos.CreateUserDTO;
import ch.zhaw.rentmybike.model.entities.User;
import ch.zhaw.rentmybike.services.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Registrierung eines neuen Users
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody CreateUserDTO createUserDTO) {
        User user = userService.createUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // User verifizieren
    @PutMapping("/{userId}/verify")
    public ResponseEntity<User> verifyUser(@PathVariable String userId) {
        User user = userService.verifyUser(userId);
        return ResponseEntity.ok(user);
    }

}