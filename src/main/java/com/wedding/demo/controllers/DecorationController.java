package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Decoration;
import com.wedding.demo.models.Decoration;
import com.wedding.demo.repositories.DecorationRepository;
import com.wedding.demo.repositories.DecorationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/decoration")
public class DecorationController {
    @Autowired
    private DecorationRepository decorationRepository;

    @GetMapping("/")
    public ResponseEntity<List<Decoration>> getDecorations() {
        return ResponseEntity.ok().body(decorationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Decoration> getDecorationById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Decoration decoration =decorationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Decoration "+ id+" not found" ));
        return ResponseEntity.ok().body(decoration);
    }

    @PostMapping("/")
    public ResponseEntity<Decoration> postDecoration(@RequestBody Decoration decoration){
        return ResponseEntity.ok(decorationRepository.save(decoration));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Decoration> updateDecoration(@PathVariable(value = "id") Long id,@Valid @RequestBody Decoration decorationDetails) throws ResourceNotFoundException{
        Decoration decoration =decorationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Decoration "+ id+" not found" ));

        decoration.setLable(decorationDetails.getLable());

        return ResponseEntity.ok(decorationRepository.save(decoration));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteDecoration(@PathVariable(value = "id") Long id) throws Exception {

        Decoration decoration = decorationRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Decoration "+ id+" not found" ));
        decorationRepository.delete(decoration);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
