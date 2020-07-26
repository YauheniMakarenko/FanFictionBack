package com.fanfiction.DTO;

import java.util.Objects;

public class GenreNewCompositionDTO {

    private Long id;
    private String genrename;

    public GenreNewCompositionDTO(Long id, String genrename) {
        this.id = id;
        this.genrename = genrename;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenrename() {
        return genrename;
    }

    public void setGenrename(String genrename) {
        this.genrename = genrename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreNewCompositionDTO that = (GenreNewCompositionDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(genrename, that.genrename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genrename);
    }
}
