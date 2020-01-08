package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.User;
import com.wedding.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user =userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User "+ userId+" not found" ));
        return ok().body(user);
    }

    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers() {
        return ok().body(userRepository.findAll());
    }

    @PostMapping("/")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user){
        return ResponseEntity.ok(userRepository.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long userId, @Valid @RequestBody User userDetails) throws ResourceNotFoundException {

        User user =userRepository
                    .findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("User "+ userId+" not found" ));

        user.setEmail(userDetails.getEmail());
        user.setLastName(userDetails.getLastName());
        user.setFirstName(userDetails.getFirstName());
        user.setPassword(userDetails.getPassword());
        user.setUsername(userDetails.getUsername());
        user.setAvatar(userDetails.getAvatar());

        return ResponseEntity.ok(userRepository.save(user));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable(value = "id") Long id) throws Exception {

        User user = userRepository
                        .findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("User "+ id+" not found" ));
        userRepository.delete(user);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
