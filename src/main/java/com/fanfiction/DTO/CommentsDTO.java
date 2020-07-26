package com.fanfiction.DTO;

import com.fanfiction.models.User;

import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentsDTO that = (CommentsDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(commentUser, that.commentUser) &&
                Objects.equals(compositionDTO, that.compositionDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, commentUser, compositionDTO);
    }
}
