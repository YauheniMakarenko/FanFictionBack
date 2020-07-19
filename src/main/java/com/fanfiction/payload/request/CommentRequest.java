package com.fanfiction.payload.request;


import com.fanfiction.models.Composition;

public class CommentRequest {

    private Long id;
    private String text;
    private Composition composition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Composition getComposition() {
        return composition;
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }
}
