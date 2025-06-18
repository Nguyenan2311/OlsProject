/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class UserMedia {
    private  int id;
    private int user_id;
    private String media_path;
    private String media_type;
    private String description;

    public UserMedia() {
    }

    public UserMedia(int id, int user_id, String media_path, String media_type, String description) {
        this.id = id;
        this.user_id = user_id;
        this.media_path = media_path;
        this.media_type = media_type;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getMedia_path() {
        return media_path;
    }

    public void setMedia_path(String media_path) {
        this.media_path = media_path;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserMedia{" + "id=" + id + ", user_id=" + user_id + ", media_path=" + media_path + ", media_type=" + media_type + ", description=" + description + '}';
    }
    
}
