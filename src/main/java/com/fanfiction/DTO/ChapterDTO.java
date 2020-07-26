package com.fanfiction.DTO;

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
}
