/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class Lesson {
    private  int id;
    private int status;
    
    private  String title;
    private  int chapterId;
    private int order;
    private  int subChapterId;;

    public Lesson() {
    }

    public Lesson(int id, int status, String title, int chapterId, int order, int subChapterId) {
        this.id = id;
        this.status = status;
        this.title = title;
        this.chapterId = chapterId;
        this.order = order;
        this.subChapterId = subChapterId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getSubChapterId() {
        return subChapterId;
    }

    public void setSubChapterId(int subChapterId) {
        this.subChapterId = subChapterId;
    }

    @Override
    public String toString() {
        return "Lesson{" + "id=" + id + ", status=" + status + ", title=" + title + ", chapterId=" + chapterId + ", order=" + order + ", subChapterId=" + subChapterId + '}';
    }

    

    

    
   
    
}
