package com.wedding.demo.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;



/*
{
    "description":"description",
    "phonePrimary":"phonePrimary",
    "phoneSecondary":"phoneSecondary",
    "avatar":"avatar"
}
*/

@Entity
@Table(name = "contact_us")
@Data
@NoArgsConstructor
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

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

}
