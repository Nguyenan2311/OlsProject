/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Chapter;
import model.CourseDTO;
import model.Lesson;
import model.LessonContent;
import model.Module;

/**
 *
 * @author An_PC
 */
public class LessonDAO extends DBContext {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<CourseDTO> getCourse() {
        List<CourseDTO> list = new ArrayList<>();
        String query = "SELECT \n"
                + "    c.id, \n"
                + "    c.subtitle AS title, \n"
                + "    t.name AS tagline, \n"
                + "    cth.thumbnail_url AS thumbnail_url\n"
                + "FROM [dbo].[Course] c\n"
                + "LEFT JOIN [dbo].[Course_Tagline] ct ON c.id = ct.course_id\n"
                + "LEFT JOIN [dbo].[Tagline] t ON ct.tagline_id = t.id\n"
                + "LEFT JOIN [dbo].[Course_Thumbnails] cth ON c.id = cth.course_id\n"
                + "WHERE c.status = 1;";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new CourseDTO(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Module> getModuleByCourse(int courseId) {
        List<Module> list = new ArrayList<>();
        String query = "select* from module where course_id = ?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setInt(1, courseId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Module(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Chapter> getChapterByModule(int moduleId) {
        List<Chapter> list = new ArrayList<>();
        String query = "select* from Chapter where module_id = ?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setInt(1, moduleId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Chapter(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Lesson> getLessonByChapter(int chapterId) {
        List<Lesson> list = new ArrayList<>();
        String query = "select* from Lesson where chapter_id = ?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setInt(1, chapterId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Lesson(rs.getInt(1),
                        rs.getInt(2),
                        
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public LessonContent getLessonContentByLessonId(int lessonId) {
        LessonContent lc = null;
        String query = "SELECT l.id AS lesson_id, l.title, "
                + "v.video_id, v.description AS video_description, "
                + "t.text_content "
                + "FROM Lesson l "
                + "LEFT JOIN VideoContent v ON l.id = v.lesson_id "
                + "LEFT JOIN TextContent t ON l.id = t.lesson_id "
                + "WHERE l.id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, lessonId);
            rs = ps.executeQuery();
            if (rs.next()) {
                lc = new LessonContent();
                lc.setLessonId(rs.getInt("lesson_id"));
                lc.setLessonTitle(rs.getString("title"));
                lc.setVideoId(rs.getString("video_id"));
                lc.setVideoDescription(rs.getString("video_description"));
                lc.setTextContent(rs.getString("text_content"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lc;
    }

    public static void main(String[] args) {
        LessonDAO dao = new LessonDAO();

        List<CourseDTO> listC = dao.getCourse();
        List<Lesson> listL = dao.getLessonByChapter(1);
        List<Module> listM = dao.getModuleByCourse(1);

        for (Lesson o : listL) {
            System.out.println(o);
            
        }
        System.out.println("-----------");
        LessonContent less = dao.getLessonContentByLessonId(1);
        System.out.println(less);

    }

}
