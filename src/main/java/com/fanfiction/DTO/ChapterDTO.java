package com.fanfiction.DTO;

import java.util.Objects;

public class ChapterDTO {

    private Long id;
    private String chaptername;
    private String text;
    private String imgUrl;
    private Long compositionId;
    private int numberChapter;

    public ChapterDTO(Long id, String chaptername, String text, String imgUrl, Long compositionId, int numberChapter) {
        this.id = id;
        this.chaptername = chaptername;
        this.text = text;
        this.imgUrl = imgUrl;
        this.compositionId = compositionId;
        this.numberChapter = numberChapter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChaptername() {
        return chaptername;
    }

    public void setChaptername(String chaptername) {
        this.chaptername = chaptername;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Long getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(Long compositionId) {
        this.compositionId = compositionId;
    }

    public int getNumberChapter() {
        return numberChapter;
    }

    public void setNumberChapter(int numberChapter) {
        this.numberChapter = numberChapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChapterDTO that = (ChapterDTO) o;
        return numberChapter == that.numberChapter &&
                Objects.equals(id, that.id) &&
                Objects.equals(chaptername, that.chaptername) &&
                Objects.equals(text, that.text) &&
                Objects.equals(imgUrl, that.imgUrl) &&
                Objects.equals(compositionId, that.compositionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chaptername, text, imgUrl, compositionId, numberChapter);
    }
}
