package com.wedding.demo.controllers;

import com.wedding.demo.exceptions.ResourceNotFoundException;
import com.wedding.demo.models.Table;
import com.wedding.demo.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/table")
@CrossOrigin("*")
public class TableController {
    @Autowired
    private TableRepository tableRepository;

    @GetMapping("/")
    public ResponseEntity<List<Table>> getTables() {
        return ResponseEntity.ok().body(tableRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Table> getTableById(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
        Table table =tableRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table "+ id+" not found" ));
        return ResponseEntity.ok().body(table);
    }

    @PostMapping("/")
    public ResponseEntity<Table> postTable(@RequestBody Table table){
        return ResponseEntity.ok(tableRepository.save(table));
    }



    @PutMapping("/{id}")
    public ResponseEntity<Table> updateTable(@PathVariable(value = "id") Long id,@Valid @RequestBody Table tableDetails) throws ResourceNotFoundException{
        Table table =tableRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table "+ id+" not found" ));

        table.setNbrChairs(tableDetails.getNbrChairs());
        table.setStyle(tableDetails.getStyle());

        return ResponseEntity.ok(tableRepository.save(table));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteTable(@PathVariable(value = "id") Long id) throws Exception {

        Table table = tableRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Table "+ id+" not found" ));
        tableRepository.delete(table);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
