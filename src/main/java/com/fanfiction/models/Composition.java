package com.fanfiction.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Indexed
@Table(name = "composition")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 510)
    @Field
    private String description;

    @Size(max = 100)
    @Field
    private String title;

    @ManyToMany
    private Set<Genre> genres = new HashSet<>();

    @ManyToOne
    private User author;

    @Column(columnDefinition = "DATETIME")
    private String publicationDate;

    public Composition() {
    }

    public Composition(Long id, @Size(max = 510) String description, @Size(max = 100) String title, Set<Genre> genres, User author) {
        this.id = id;
        this.description = description;
        this.title = title;
        this.genres = genres;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String compositioname) {
        this.title = compositioname;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

}
