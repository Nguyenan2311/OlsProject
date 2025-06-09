/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class CourseDTO {
    private int id;
    private String title; 
    private String tagline; 
    private String thumbnailUrl; 

    public CourseDTO() {
    }

    public CourseDTO(int id, String title, String tagline, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.tagline = tagline;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
    

    @Override
    public String toString() {
        return "CourseDTO{" + "id=" + id + ", title=" + title + ", tagline=" + tagline + ", thumbnailUrl=" + thumbnailUrl + '}';
    }
    
}
