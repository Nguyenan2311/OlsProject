package model;
import java.util.Date;

public class PersonalCourse {
    private int id;
    private int customerId;
    private int courseId;
    private Date enrollDate;
    private Date expireDate;
    private int progress;
    private int pricePackageId;
    private int saleNoteId;
    private int status;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public int getCourseId() { return courseId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public Date getEnrollDate() { return enrollDate; }
    public void setEnrollDate(Date enrollDate) { this.enrollDate = enrollDate; }
    public Date getExpireDate() { return expireDate; }
    public void setExpireDate(Date expireDate) { this.expireDate = expireDate; }
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
    public int getPricePackageId() { return pricePackageId; }
    public void setPricePackageId(int pricePackageId) { this.pricePackageId = pricePackageId; }
    public int getSaleNoteId() { return saleNoteId; }
    public void setSaleNoteId(int saleNoteId) { this.saleNoteId = saleNoteId; }
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    @Override
    public String toString() {
        return "PersonalCourse{" + "id=" + id + ", customerId=" + customerId + ", courseId=" + courseId + ", enrollDate=" + enrollDate + ", expireDate=" + expireDate + ", progress=" + progress + ", pricePackageId=" + pricePackageId + ", saleNoteId=" + saleNoteId + ", status=" + status + '}';
    }
    
}
