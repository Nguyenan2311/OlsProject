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
public class Blog {
    private int id;
    private String thumbnail_url;
    private String title;
    private String content;
    private String brief_info;
    private int author_id;
    private Date created_date;
    private Date updated_date;
    private int category_id;

    public Blog() {
    }

    public Blog(int id, String thumbnail_url, String title, String content, String brief_info, int author_id, Date created_date, Date updated_date, int category_id) {
        this.id = id;
        this.thumbnail_url = thumbnail_url;
        this.title = title;
        this.content = content;
        this.brief_info = brief_info;
        this.author_id = author_id;
        this.created_date = created_date;
        this.updated_date = updated_date;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getThumbnail_url() {
        return thumbnail_url;
    }

    public void setThumbnail_url(String thumbnail_url) {
        this.thumbnail_url = thumbnail_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBrief_info() {
        return brief_info;
    }

    public void setBrief_info(String brief_info) {
        this.brief_info = brief_info;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    @Override
    public String toString() {
        return "Blog{" + "id=" + id + ", thumbnail_url=" + thumbnail_url + ", title=" + title + ", content=" + content + ", brief_info=" + brief_info + ", author_id=" + author_id + ", created_date=" + created_date + ", updated_date=" + updated_date + ", category_id=" + category_id + '}';
    }
    
    
    
    
}
