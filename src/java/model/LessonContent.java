/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class LessonContent {

    private int lessonId;
    private String lessonTitle;
    private String videoId;
    private String videoDescription;
    private String textContent;

    public LessonContent() {
    }

    public LessonContent(int lessonId, String lessonTitle, String videoId, String videoDescription, String textContent) {
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.videoId = videoId;
        this.videoDescription = videoDescription;
        this.textContent = textContent;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoDescription() {
        return videoDescription;
    }

    public void setVideoDescription(String videoDescription) {
        this.videoDescription = videoDescription;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "LessonContent{" + "lessonId=" + lessonId + ", lessonTitle=" + lessonTitle + ", videoId=" + videoId + ", videoDescription=" + videoDescription + ", textContent=" + textContent + '}';
    }
    
}
