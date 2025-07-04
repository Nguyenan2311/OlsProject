package model;


import java.util.Date;

public class PricePackage {
    private int id;
    private int courseId;
    private String title;
    private double price;
    private double salePrice;
    private Date startDate;
    private Date endDate;

    public int getId() {
        return this.id;
    }

    public int getCourseId() {
        return this.courseId;
    }

    public String getTitle() {
        return this.title;
    }

    public double getPrice() {
        return this.price;
    }

    public double getSalePrice() {
        return this.salePrice;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}