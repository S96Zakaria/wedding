package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Article;
import com.wedding.demo.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/contact")
@CrossOrigin("*")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

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

}
