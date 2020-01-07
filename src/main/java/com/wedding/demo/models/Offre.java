package com.wedding.demo.models;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "offers")
@Data
@NoArgsConstructor
public class Offre implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name="offre_chairs",joinColumns = @JoinColumn(name="offre_id"), inverseJoinColumns = @JoinColumn(name="chair_id"))
    private List<Chair> chairs;

    @ManyToMany
    @JoinTable(name="offre_decorations",joinColumns = @JoinColumn(name="offre_id"), inverseJoinColumns = @JoinColumn(name="decoration_id"))
    private List<Decoration> decorations;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;}
