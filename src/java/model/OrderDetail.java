package model;


/**
 * Represents an OrderDetail entity, corresponding to the 'OrderDetail' table.
 * This class holds information about a specific item within an order.
 */
public class OrderDetail {
    private int id;
    private int orderId;
    private int courseId;
    private int pricePackageId;
    private double price;
    private double salePrice;
    
    // Constructors
    public OrderDetail() {}

    // Getters and Setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getPricePackageId() {
        return pricePackageId;
    }

    public void setPricePackageId(int pricePackageId) {
        this.pricePackageId = pricePackageId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "id=" + id + ", orderId=" + orderId + ", courseId=" + courseId + ", salePrice=" + salePrice + '}';
    }
}