package com.wedding.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@javax.persistence.Table(name = "images")
@Data
@NoArgsConstructor
public class Image {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "offre_id")
    private Offre offre;

    private String link;

}