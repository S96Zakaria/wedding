package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Image;
import com.wedding.demo.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/image")
public class ImageController {

    private static final String FILE_DIRECTORY = System.getProperty("user.dir") +"/images/";

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
    public ResponseEntity<Boolean> uploadImage(@RequestParam("file") MultipartFile[] uploadingFiles) throws IOException {
        for(MultipartFile uploadedFile : uploadingFiles) {
            File file = new File(FILE_DIRECTORY + uploadedFile.getOriginalFilename());
            uploadedFile.transferTo(file);
            Image image= new Image();
            image.setLink(uploadedFile.getOriginalFilename());
            imageRepository.save(image);
        }

        return ResponseEntity.ok(true);

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
