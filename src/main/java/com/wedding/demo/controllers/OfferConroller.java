package com.wedding.demo.controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Offre;
import com.wedding.demo.repositories.OffreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/offre")
@CrossOrigin("*")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class OfferConroller {
    @Autowired
    private OffreRepository offreRepository;

    @GetMapping("/")
    public ResponseEntity<List<Offre>> getOffres() {
        return ResponseEntity.ok().body(offreRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offre> getOffreById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Offre offre =offreRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offre "+ id+" not found" ));
        return ResponseEntity.ok().body(offre);
    }

    @PostMapping("/")
    public ResponseEntity<Offre> postOffre(@RequestBody Offre offre){
        return ResponseEntity.ok(offreRepository.save(offre));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Offre> updateOffre(@PathVariable(value = "id") Long id,@Valid @RequestBody Offre offreDetails) throws ResourceNotFoundException{
        Offre offre =offreRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offre "+ id+" not found" ));

        offre.setDescription(offreDetails.getDescription());
        offre.setDecorations(offreDetails.getDecorations());
        offre.setTables(offreDetails.getTables());
        offre.setDisponible(offreDetails.isDisponible());
        offre.setPrice(offreDetails.getPrice());
        offre.setTitre(offreDetails.getTitre());
        offre.setUser(offreDetails.getUser());
        offre.setImages(offreDetails.getImages());

        return ResponseEntity.ok(offreRepository.save(offre));
    }



    @PutMapping("/{id}/disponibilte")
    public ResponseEntity<Offre> changeDisponibilty(@PathVariable(value = "id") Long id) throws ResourceNotFoundException{
        Offre offre =offreRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offre "+ id+" not found" ));

        offre.setDisponible(!offre.isDisponible());

        return ResponseEntity.ok(offreRepository.save(offre));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteOffre(@PathVariable(value = "id") Long id) throws Exception {

        Offre offre = offreRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offre "+ id+" not found" ));
        offreRepository.delete(offre);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
