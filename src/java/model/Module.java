/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class Module {
    private  int id;
    private int courseId;
    private String name;
    private String description;
    private int order;

    public Module() {
    }

    public Module(int id, int courseId, String name, String description, int order) {
        this.id = id;
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Module{" + "id=" + id + ", courseId=" + courseId + ", name=" + name + ", description=" + description + ", order=" + order + '}';
    }
    
    
}
