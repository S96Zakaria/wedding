package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.User;
import com.wedding.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        User user =userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User "+ userId+" not found" ));
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/")
    public User addUser(@Valid @RequestBody User user){
        return userRepository.save(user);
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
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws Exception {
        User user = userRepository
                        .findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("User "+ userId+" not found" ));
        userRepository.delete(user);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

}
