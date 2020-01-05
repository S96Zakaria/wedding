package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Contact;
import com.wedding.demo.models.User;
import com.wedding.demo.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/")
    public List<Contact> getConctacts() {
        return contactRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Contact contact =contactRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact "+ id+" not found" ));
        return ResponseEntity.ok().body(contact);
    }

    @PostMapping("/")
    public Contact postContact(@RequestBody Contact contact){
        return contactRepository.save(contact);
    }
}
