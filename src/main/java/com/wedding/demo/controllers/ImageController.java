package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Image;
import com.wedding.demo.repositories.ImageRepository;
import com.wedding.demo.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/image")
public class ImageController {
    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/")
    public ResponseEntity<List<Image>> getImages() {
        return ResponseEntity.ok().body(imageRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> getImageById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Image table =imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image "+ id+" not found" ));
        return ResponseEntity.ok().body(table);
    }

    @PostMapping("/")
    public ResponseEntity<Image> postImage(@RequestBody Image table){
        return ResponseEntity.ok(imageRepository.save(table));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable(value = "id") Long id,@Valid @RequestBody Image imageDetails) throws ResourceNotFoundException{
        Image image =imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image "+ id+" not found" ));

        image.setLink(imageDetails.getLink());

        return ResponseEntity.ok(imageRepository.save(image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteImage(@PathVariable(value = "id") Long id) throws Exception {

        Image table = imageRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image "+ id+" not found" ));
        imageRepository.delete(table);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
