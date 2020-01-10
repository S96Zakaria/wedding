package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Article;
import com.wedding.demo.models.Image;
import com.wedding.demo.models.Offre;
import com.wedding.demo.repositories.ArticleRepository;
import com.wedding.demo.repositories.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


@RestController
@RequestMapping("/article")
@CrossOrigin("*")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;


    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/")
    public ResponseEntity<List<Article>>  getContacts() {
        return ResponseEntity.ok().body(articleRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getContactById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Article article = articleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact "+ id+" not found" ));
        return ResponseEntity.ok().body(article);
    }

    @PostMapping("/")
    public ResponseEntity<Article> postContact(@RequestBody Article article){
        return ResponseEntity.ok(articleRepository.save(article));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Article> updateContact(@PathVariable(value = "id") Long id, @Valid @RequestBody Article articleDetails) throws ResourceNotFoundException{
        Article article = articleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact "+ id+" not found" ));

        article.setAvatar(articleDetails.getAvatar());
        article.setContent(articleDetails.getContent());
        article.setTitle(articleDetails.getTitle());

        return ResponseEntity.ok(articleRepository.save(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteContact(@PathVariable(value = "id") Long id) throws Exception {

        Article article = articleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact "+ id+" not found" ));
        articleRepository.delete(article);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
    @GetMapping(path="/articleImage/{id}",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getPhoto(@PathVariable("id") Long id) throws Exception{
        Image p=imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact "+ id+" not found" ));;
        return Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/images/article/"+p.getLink()));
    }

    @PostMapping("/articleImage/{id}")
    public Image uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id") Long id)  throws Exception{
        Article article =articleRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article "+ id+" not found" ));

        Files.write(Paths.get(System.getProperty("user.dir")+"/images/article/"+file.getOriginalFilename()),file.getBytes());
        Image image = new Image();
        image.setLink(file.getOriginalFilename());

        return imageRepository.save(image);
    }
}
