package com.fanfiction.DTO;

import com.fanfiction.models.Genre;

import java.util.Set;

public class CompositionHomeDTO {

    private Long id;
    private String title;
    private String description;
    private String publicationDate;
    private Set<Genre> genres;

    public CompositionHomeDTO(Long id, String title, String description, String publicationDate, Set<Genre> genres) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.genres = genres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
}
