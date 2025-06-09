/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author An_PC
 */
public class Course {
    private int id;
    private String subtitle;
    private int expert_id;
    private int total_duration;
    private int category_id;
    private String description;
    private int status;
    private Date updated_date;
    private Date created_date;
    private int number_of_learner;

    public Course() {
    }

    public Course(int id, String subtitle, int expert_id, int total_duration, int category_id, String description, int status, Date updated_date, Date created_date, int number_of_learner) {
        this.id = id;
        this.subtitle = subtitle;
        this.expert_id = expert_id;
        this.total_duration = total_duration;
        this.category_id = category_id;
        this.description = description;
        this.status = status;
        this.updated_date = updated_date;
        this.created_date = created_date;
        this.number_of_learner = number_of_learner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getExpert_id() {
        return expert_id;
    }

    public void setExpert_id(int expert_id) {
        this.expert_id = expert_id;
    }

    public int getTotal_duration() {
        return total_duration;
    }

    public void setTotal_duration(int total_duration) {
        this.total_duration = total_duration;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public int getNumber_of_learner() {
        return number_of_learner;
    }

    public void setNumber_of_learner(int number_of_learner) {
        this.number_of_learner = number_of_learner;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", subtitle=" + subtitle + ", expert_id=" + expert_id + ", total_duration=" + total_duration + ", category_id=" + category_id + ", description=" + description + ", status=" + status + ", updated_date=" + updated_date + ", created_date=" + created_date + ", number_of_learner=" + number_of_learner + '}';
    }
    
    
}
