/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author An_PC
 */
public class CourseTagline {
    private int tagline_id;
    private int course_id;

    public CourseTagline() {
    }

    public CourseTagline(int tagline_id, int course_id) {
        this.tagline_id = tagline_id;
        this.course_id = course_id;
    }

    @Override
    public String toString() {
        return "CourseTagline{" + "tagline_id=" + tagline_id + ", course_id=" + course_id + '}';
    }
    
    
}
