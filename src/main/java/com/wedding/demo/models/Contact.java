package com.wedding.demo.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "contact_us")
@Data
public class Contact implements Serializable {
    @Id
    @GeneratedValue
    private long id;
    @Column(name="description", nullable = false)
    private String description;
    @Column(name="phone_primary", nullable = false)
    private String phonePrimary;
    @Column(name="phone_secondary")
    private String phoneSecondary;
    @Column(name="avatar", nullable = false)
    private String avatar;

}
