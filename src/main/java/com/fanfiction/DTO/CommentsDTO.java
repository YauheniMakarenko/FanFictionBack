package com.fanfiction.DTO;

import com.fanfiction.models.Composition;
import com.fanfiction.models.User;


public class CommentsDTO {

    private Long id;
    private String text;
    private User commentUser;
    private Composition composition;

    public CommentsDTO() {
    }

    public CommentsDTO(Long id, String text, User commentUser, Composition composition) {
        this.id = id;
        this.text = text;
        this.commentUser = commentUser;
        this.composition = composition;
    }

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

    public User getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(User commentUser) {
        this.commentUser = commentUser;
    }

    public Composition getComposition() {
        return composition;
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }
}
