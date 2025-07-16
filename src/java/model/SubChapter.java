/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class SubChapter {
    private int id;
    private int chapterId;
    private String title;
    private String description;
    private int order;
    

    public SubChapter() {
    }

    public SubChapter(int id, int chapterId, String title, String description, int order) {
        this.id = id;
        this.chapterId = chapterId;
        this.title = title;
        this.description = description;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "SubChapter{" + "id=" + id + ", chapterId=" + chapterId + ", title=" + title + ", description=" + description + ", order=" + order + '}';
    }

   

    
    
    
}
