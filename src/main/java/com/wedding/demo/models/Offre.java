package com.wedding.demo.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@javax.persistence.Table(name = "offers")
@Data
@NoArgsConstructor
public class Offre implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String titre;

    @Size(max = 10000)
    private String description;

    private boolean disponible;

    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    
    @JoinTable(name="offre_tables",joinColumns = @JoinColumn(name="offre_id"), inverseJoinColumns = @JoinColumn(name="table_id"))   
    private List<Table> tables;

    @ManyToMany
    
    @JoinTable(name="offre_decorations",joinColumns = @JoinColumn(name="offre_id"), inverseJoinColumns = @JoinColumn(name="decoration_id"))   
    private List<Decoration> decorations;

    
    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL)    
    private Set<Image> images = new HashSet<>();


    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
    
    
}
