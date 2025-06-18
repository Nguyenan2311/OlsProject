/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class CourseThumbnails {
    private int id;
    private int course_id;
    private String thumbnail_url;
    private String description;

    public CourseThumbnails(int id, int course_id, String thumbnail_url, String description) {
        this.id = id;
        this.course_id = course_id;
        this.thumbnail_url = thumbnail_url;
        this.description = description;
    }

    public CourseThumbnails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CourseThumbnails{" + "id=" + id + ", course_id=" + course_id + ", thumbnail_url=" + thumbnail_url + ", description=" + description + '}';
    }
    
}
