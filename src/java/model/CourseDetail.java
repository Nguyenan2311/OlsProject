package model;


import DAO.CourseDAO;
import java.util.List;

public class CourseDetail {
    private CourseDAO.CourseListItem courseInfo;
    private String fullDescription;
    private PricePackage lowestPricePackage;
    private List<CourseVisualContent> images;
    private List<CourseVisualContent> videos;
    private List<PricePackage> allPricePackages;

    public CourseDAO.CourseListItem getCourseInfo() {
        return this.courseInfo;
    }

    public void setCourseInfo(CourseDAO.CourseListItem courseInfo) {
        this.courseInfo = courseInfo;
    }

    public String getFullDescription() {
        return this.fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public PricePackage getLowestPricePackage() {
        return this.lowestPricePackage;
    }

    public void setLowestPricePackage(PricePackage lowestPricePackage) {
        this.lowestPricePackage = lowestPricePackage;
    }

    public List<CourseVisualContent> getImages() {
        return this.images;
    }

    public void setImages(List<CourseVisualContent> images) {
        this.images = images;
    }

    public List<CourseVisualContent> getVideos() {
        return this.videos;
    }

    public void setVideos(List<CourseVisualContent> videos) {
        this.videos = videos;
    }

    public List<PricePackage> getAllPricePackages() {
        return this.allPricePackages;
    }

    public void setAllPricePackages(List<PricePackage> allPricePackages) {
        this.allPricePackages = allPricePackages;
    }
}