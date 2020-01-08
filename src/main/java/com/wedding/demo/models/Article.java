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
@javax.persistence.Table(name = "articles")
@Data
@NoArgsConstructor
public class Article implements Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="avatar", nullable = false)
    private String avatar;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

}
