package DAO;

import context.DBContext;
import model.PersonalCourse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalCourseDAO extends DBContext {
    public List<PersonalCourse> getPersonalCoursesByUser(int userId) {
        List<PersonalCourse> list = new ArrayList<>();
        String sql = "SELECT * FROM PersonalCourse WHERE customer_id = ? AND expire_date >= GETDATE()";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PersonalCourse pc = new PersonalCourse();
                pc.setId(rs.getInt("id"));
                pc.setCustomerId(rs.getInt("customer_id"));
                pc.setCourseId(rs.getInt("course_id"));
                pc.setEnrollDate(rs.getDate("enroll_date"));
                pc.setExpireDate(rs.getDate("expire_date"));
                pc.setProgress(rs.getInt("progress"));
                pc.setPricePackageId(rs.getInt("price_package_id"));
                pc.setSaleNoteId(rs.getInt("sale_note_id"));
                list.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        PersonalCourseDAO dao = new PersonalCourseDAO();
        List<PersonalCourse> list = dao.getPersonalCoursesByUser(3);
        for (PersonalCourse personalCourse : list) {
            System.out.println(list);
            
        }
    }
    
}
