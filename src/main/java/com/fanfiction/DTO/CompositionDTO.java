package com.fanfiction.DTO;

import com.fanfiction.models.Genre;
import com.fanfiction.models.User;

import java.util.Set;

public class CompositionDTO {

    private Long id;
    private String title;
    private String description;
    private Set<GenreNewCompositionDTO> genres;
    private User author;

    public CompositionDTO() {
    }

    public CompositionDTO(Long id, String title, String description, Set<GenreNewCompositionDTO> genres, User author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.genres = genres;
        this.author = author;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public Set<GenreNewCompositionDTO> getGenres() {
        return genres;
    }

    public void setGenres(Set<GenreNewCompositionDTO> genres) {
        this.genres = genres;
    }
}
