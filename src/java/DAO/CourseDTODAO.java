package DAO;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CourseDTO;
import model.MyCourse;

public class CourseDTODAO extends DBContext {
    
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
            conn = new DBContext().getConnection();
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
    
    public List<MyCourse> getMyCourse(int id) {
        List<MyCourse> list = new ArrayList<>();
        String query = "SELECT DISTINCT\n"
                + "    c.id                 AS CourseID,\n"
                + "    c.subtitle           AS CourseTitle,\n"
                + "    tg.name              AS CourseTagline,\n"
                + "    th.thumbnail_url     AS CourseThumbnail,\n"
                + "    pc.enroll_date       AS EnrollDate,\n"
                + "    pc.expire_date       AS ExpireDate,\n"
                + "    pc.status            AS Status\n"
                + "FROM\n"
                + "    PersonalCourse pc\n"
                + "INNER JOIN\n"
                + "    Course c\n"
                + "  ON pc.course_id = c.id\n"
                + "LEFT JOIN\n"
                + "    Course_Tagline ct\n"
                + "  ON c.id = ct.course_id\n"
                + "LEFT JOIN\n"
                + "    Tagline tg\n"
                + "  ON ct.tagline_id = tg.id\n"
                + "LEFT JOIN\n"
                + "    Course_Thumbnails th\n"
                + "  ON c.id = th.course_id\n"
                + "WHERE\n"
                + "    pc.customer_id = ?; ";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new MyCourse(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7)));
            }
        } catch (Exception e) {
        }
        return list;
    }
}
