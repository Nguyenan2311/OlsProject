package DAO;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CourseDTO;

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
        } catch (Exception e) {}
        return list;
    }
}
