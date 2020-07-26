package com.fanfiction.DTO;

import java.util.Objects;

public class CompositionProfileDTO {

    private Long id;
    private String title;
    private String description;
    private int chapterAmount;

    public CompositionProfileDTO(Long id, String title, String description, int chapterAmount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.chapterAmount = chapterAmount;
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

    public int getChapterAmount() {
        return chapterAmount;
    }

    public void setChapterAmount(int chapterAmount) {
        this.chapterAmount = chapterAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositionProfileDTO that = (CompositionProfileDTO) o;
        return chapterAmount == that.chapterAmount &&
                Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, chapterAmount);
    }
}
