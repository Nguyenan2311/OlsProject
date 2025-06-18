/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class Slider {
    private int id;
    private int author_id;
    private String image_url;
    private String back_link_url;

    public Slider() {
    }

    public Slider(int id, int author_id, String image_url, String back_link_url) {
        this.id = id;
        this.author_id = author_id;
        this.image_url = image_url;
        this.back_link_url = back_link_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getBack_link_url() {
        return back_link_url;
    }

    public void setBack_link_url(String back_link_url) {
        this.back_link_url = back_link_url;
    }

    @Override
    public String toString() {
        return "Slider{" + "id=" + id + ", author_id=" + author_id + ", image_url=" + image_url + ", back_link_url=" + back_link_url + '}';
    }
    
    
}
