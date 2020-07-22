package com.fanfiction.DTO;

import com.fanfiction.models.User;


public class CommentsDTO {

    private Long id;
    private String text;
    private User commentUser;
    private CompositionDTO compositionDTO;


    public CommentsDTO(Long id, String text, User commentUser) {
        this.id = id;
        this.text = text;
        this.commentUser = commentUser;
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

    public CompositionDTO getCompositionDTO() {
        return compositionDTO;
    }

    public void setCompositionDTO(CompositionDTO compositionDTO) {
        this.compositionDTO = compositionDTO;
    }
}
