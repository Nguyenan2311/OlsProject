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
import model.Blog;
import model.BlogCategory;
import model.BlogDTO;
import model.CourseDTO;
import model.Slider;
import model.User;

/**
 *
 * @author An_PC
 */
public class DAO extends DBContext {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public User login(String email, String password) {
        String query = "select* from [User]\n"
                + "where email = ? and password = ? and status = 1";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getByte(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getByte(4),
                        rs.getDate(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void changePassword(String email, String password) {
        String query = "UPDATE [dbo].[User]\n"
                + "SET password = ?\n"
                + "WHERE email = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(2, email);
            ps.setString(1, password);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Slider> getSlider() {
        List<Slider> list = new ArrayList<>();
        String query = "SELECT *\n"
                + "FROM [dbo].[Slider];";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Slider(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Blog> getPost() {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT  * \n"
                + "FROM [dbo].[Blog]\n";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Blog(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getInt(9)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<BlogDTO> getListPost() {
        List<BlogDTO> list = new ArrayList<>();
        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value\n"
                + "                FROM [dbo].[Blog] b\n"
                + "                JOIN [dbo].[User] u ON b.author_id = u.id\n"
                + "				join Setting s on b.category_id = s.id\n"
                + "                ORDER BY created_date DESC";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BlogDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getString(11)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<BlogDTO> getListPostByCategory(String id) {
        List<BlogDTO> list = new ArrayList<>();
        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value\n"
                + "                FROM [dbo].[Blog] b\n"
                + "                JOIN [dbo].[User] u ON b.author_id = u.id\n"
                + "				join Setting s on b.category_id = s.id\n"
                + "				where b.category_id = ?\n"
                + "                ORDER BY created_date DESC";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BlogDTO(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getInt(9),
                        rs.getString(10),
                        rs.getString(11)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<BlogCategory> getListCategory() {
        List<BlogCategory> list = new ArrayList<>();
        String query = "select id, value, description from Setting\n"
                + "where setting_type_id = 3";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new BlogCategory(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Blog> getLastPost() {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT top 5 *\n"
                + "FROM [dbo].[Blog]\n"
                + "ORDER BY created_date DESC;";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Blog(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getInt(9)));
            }
        } catch (Exception e) {
        }
        return list;
    }

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

    public static void main(String[] args) {
        DAO dao = new DAO();
        User user = dao.login("admin@fpt.edu.vn", "admin123");
        List<Slider> listS = dao.getSlider();
        List<BlogDTO> listP = dao.getListPost();
        List<CourseDTO> listC = dao.getCourse();
        List<BlogCategory> listBC = dao.getListCategory();
        for (BlogCategory o : listBC) {
            System.out.println(o);
        }

    }
}
