package model;


import java.util.Date;

public class CourseRegistration {
    private int id;
    private String subject; // Maps to Course.subtitle
    private String category; // Maps to Setting.value (category_id from Course)
    private Date registrationTime; // Maps to PersonalCourse.enroll_date
    private String packageName; // Maps to PricePackage.title
    private double totalCost; // Maps to PricePackage.sale_price
    private String status; // Derived based on dates and progress
    private Date validFrom; // Maps to PersonalCourse.enroll_date
    private Date validTo; // Maps to PersonalCourse.expire_date
    private int customerId; // Maps to PersonalCourse.customer_id
    private int courseId; // Maps to PersonalCourse.customer_id

    public CourseRegistration() {
    }

    public CourseRegistration(int id, String subject, String category, Date registrationTime, String packageName, double totalCost, String status, Date validFrom, Date validTo, int customerId, int courseId) {
        this.id = id;
        this.subject = subject;
        this.category = category;
        this.registrationTime = registrationTime;
        this.packageName = packageName;
        this.totalCost = totalCost;
        this.status = status;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.customerId = customerId;
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    
}
