/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class BlogCategory {
    private int id;
    private String value;
    private String description;

    public BlogCategory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BlogCategory(int id, String value, String description) {
        this.id = id;
        this.value = value;
        this.description = description;
    }

    @Override
    public String toString() {
        return "BlogCategory{" + "id=" + id + ", value=" + value + ", description=" + description + '}';
    }
    
}
