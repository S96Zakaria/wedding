package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Image;
import com.wedding.demo.models.Offre;
import com.wedding.demo.repositories.ImageRepository;
import com.wedding.demo.repositories.OffreRepository;
import com.wedding.demo.services.FileStorageService;
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



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
public class ImageController{

    //private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ImageRepository imageRepository;


    @Autowired
    private OffreRepository offreRepository;

    @GetMapping(path="/photoProduct/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        Image p=imageRepository.findById(id).get();
        return Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/images/"+p.getLink()));
    }

    @PostMapping("/uploadFile/{id}")
    public Image uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id)  throws Exception{
        Offre offre =offreRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offre "+ id+" not found" ));

        Files.write(Paths.get(System.getProperty("user.dir")+"/images/"+file.getOriginalFilename()),file.getBytes());
        Image image = new Image();
        image.setLink(file.getOriginalFilename());
        image.setOffre(offre);

        return imageRepository.save(image);
    }

//    @PostMapping("/uploadMultipleFiles")
//    public List<Image> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//
//        return Arrays.asList(files)
//                .stream()
//                .map(file -> uploadFile(file))
//                .collect(Collectors.toList());
//    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
//            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}





//@RestController
//@RequestMapping("/image")
//public class ImageController {
//
//    private static final String FILE_DIRECTORY = System.getProperty("user.dir") +"/images/";
//
//    @Autowired
//    private ImageRepository imageRepository;
//
//    @GetMapping("/")
//    public ResponseEntity<List<Image>> getImages() {
//        return ResponseEntity.ok().body(imageRepository.findAll());
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Image> getImageById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
//        Image table =imageRepository
//                .findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Image "+ id+" not found" ));
//        return ResponseEntity.ok().body(table);
//    }
//
//    @PostMapping("/")
//    public ResponseEntity<Boolean> uploadImage(@RequestParam("file") MultipartFile[] uploadingFiles) throws IOException {
//        for(MultipartFile uploadedFile : uploadingFiles) {
//            File file = new File(FILE_DIRECTORY + uploadedFile.getOriginalFilename());
//            uploadedFile.transferTo(file);
//            Image image= new Image();
//            image.setLink(uploadedFile.getOriginalFilename());
//            imageRepository.save(image);
//        }
//
//        return ResponseEntity.ok(true);
//
//    }
//
//
//
//
//    @PutMapping("/{id}")
//    public ResponseEntity<Image> updateImage(@PathVariable(value = "id") Long id,@Valid @RequestBody Image imageDetails) throws ResourceNotFoundException{
//        Image image =imageRepository
//                .findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Image "+ id+" not found" ));
//
//        image.setLink(imageDetails.getLink());
//
//        return ResponseEntity.ok(imageRepository.save(image));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Long> deleteImage(@PathVariable(value = "id") Long id) throws Exception {
//
//        Image table = imageRepository
//                .findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Image "+ id+" not found" ));
//        imageRepository.delete(table);
//
//        return new ResponseEntity<>(id, HttpStatus.OK);
//    }
//}
