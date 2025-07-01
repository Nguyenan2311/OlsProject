/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class Chapter {
    private int id;
    private int order;
    private String subtitle;
    private String briefInfor;
    private String description;
    private int status;
    private int duration;
    private int moduleId;

    public Chapter() {
    }

    public Chapter(int id, int order, String subtitle, String briefInfor, String description, int status, int duration, int moduleId) {
        this.id = id;
        this.order = order;
        this.subtitle = subtitle;
        this.briefInfor = briefInfor;
        this.description = description;
        this.status = status;
        this.duration = duration;
        this.moduleId = moduleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getBriefInfor() {
        return briefInfor;
    }

    public void setBriefInfor(String briefInfor) {
        this.briefInfor = briefInfor;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public String toString() {
        return "Chapter{" + "id=" + id + ", order=" + order + ", subtitle=" + subtitle + ", briefInfor=" + briefInfor + ", description=" + description + ", status=" + status + ", duration=" + duration + ", moduleId=" + moduleId + '}';
    }
    
    
}
