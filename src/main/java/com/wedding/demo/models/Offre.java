package com.wedding.demo.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
    private LocalDateTime updateDateTime;}
