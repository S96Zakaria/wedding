package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Contact;
import com.wedding.demo.models.User;
import com.wedding.demo.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/contact")
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    @GetMapping("/")
    public ResponseEntity<List<Contact>>  getContacts() {
        return ResponseEntity.ok().body(contactRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContactById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Contact contact =contactRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact "+ id+" not found" ));
        return ResponseEntity.ok().body(contact);
    }

    @PostMapping("/")
    public ResponseEntity<Contact> postContact(@RequestBody Contact contact){
        return ResponseEntity.ok(contactRepository.save(contact));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable(value = "id") Long id,@Valid @RequestBody Contact contactDetails) throws ResourceNotFoundException{
        Contact contact =contactRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact "+ id+" not found" ));

        contact.setAvatar(contactDetails.getAvatar());
        contact.setDescription(contactDetails.getDescription());
        contact.setPhonePrimary(contactDetails.getPhonePrimary());
        contact.setPhoneSecondary(contactDetails.getPhoneSecondary());

        return ResponseEntity.ok(contactRepository.save(contact));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable(value = "id") Long id) throws Exception {

        Contact contact = contactRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact "+ id+" not found" ));
        contactRepository.delete(contact);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

}
