package com.fanfiction.DTO;

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

}
