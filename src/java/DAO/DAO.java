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
import java.util.Date;
import java.util.List;
import model.Blog;
import model.BlogCategory;
import model.BlogDTO;
import model.CourseDTO;
import model.Slider;
import model.User;
import model.UserMedia;

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

    public User getUserById(String id) {
        String query = "select* from [User]\n"
                + "where id =?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, id);

            rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getByte(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
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

    public User checkPassword(String email, String password) {
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

    public boolean changePassword(int id, String password) {
        String query = "UPDATE [dbo].[User]\n"
                + "SET password = ?\n"
                + "WHERE id = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, password);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
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

    public List<UserMedia> getMedia(int userId) {
        List<UserMedia> list = new ArrayList<>();
        String query = "select * from UserMedia\n"
                + "where USER_ID = ?";

        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new UserMedia(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public UserMedia getMediaByid(String id) {
        String query = "select * from UserMedia\n"
                + "where id = ?";

        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new UserMedia(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void editMedia(String mediaId, String path, String type, String desc) {
        String query = "UPDATE UserMedia SET media_path = ?, media_type = ?, description = ? WHERE id = ?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, path);
            ps.setString(2, type);
            ps.setString(3, desc);
            ps.setString(4, mediaId);
            ps.executeUpdate();
        } catch (Exception e) {

        }
    }

    public void addMedia(String userId, String media_path, String media_type, String description) {
        String query = "INSERT INTO [dbo].[UserMedia]\n"
                + "           ([user_id]\n"
                + "           ,[media_path]\n"
                + "           ,[media_type]\n"
                + "           ,[description])\n"
                + "     VALUES\n"
                + "           (?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ps.setString(2, media_path);
            ps.setString(3, media_type);
            ps.setString(4, description);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void deleteMediaById(String id) {
        String query = "DELETE FROM UserMedia WHERE id = ?";
        try  {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateProfile(String firstName, String lastname, String gender, String phone, Date dob, String address, String uid) {
        String query = "update [User] set first_name = ? , last_name = ?, gender = ?,phone = ?, dob = ?, address = ? where id =?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastname);
            ps.setString(3, gender);
            ps.setString(4, phone);
            ps.setDate(5, (java.sql.Date) dob);
            ps.setString(6, address);
            ps.setString(7, uid);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
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

    public List<BlogDTO> getListPostByCategory(String id, int index) {
        List<BlogDTO> list = new ArrayList<>();
        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value\n"
                + "                FROM [dbo].[Blog] b\n"
                + "                JOIN [dbo].[User] u ON b.author_id = u.id\n"
                + "				join Setting s on b.category_id = s.id\n"
                + "				where b.category_id = ?\n"
                + "                ORDER BY b.id\n"
                + "				offset ? rows fetch next 3 rows only";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.setInt(2, (index - 1) * 3);
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

    public BlogDTO getBlogByid(String id) {

        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value\n"
                + "                FROM [dbo].[Blog] b\n"
                + "                JOIN [dbo].[User] u ON b.author_id = u.id\n"
                + "				join Setting s on b.category_id = s.id\n"
                + "				where b.id = ?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return new BlogDTO(
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
                        rs.getString(11));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public List<BlogDTO> pagingPost(int index) {
        List<BlogDTO> list = new ArrayList<>();
        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value\n"
                + "                FROM [dbo].[Blog] b\n"
                + "                JOIN [dbo].[User] u ON b.author_id = u.id\n"
                + "				join Setting s on b.category_id = s.id\n"
                + "                ORDER BY created_date DESC\n"
                + "				offset ? rows fetch next 3 rows only";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setInt(1, (index - 1) * 3);
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

    public List<BlogDTO> getListPostByName(String name, int index) {
        List<BlogDTO> list = new ArrayList<>();
        String query = "				\n"
                + "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value\n"
                + "                FROM [dbo].[Blog] b\n"
                + "                JOIN [dbo].[User] u ON b.author_id = u.id\n"
                + "				join Setting s on b.category_id = s.id\n"
                + "				where b.title like ?\n"
                + "                ORDER BY b.id\n"
                + "				offset ? rows fetch next 3 rows only";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            ps.setInt(2, (index - 1) * 3);
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

    public int getTotalBlog() {

        String query = "select COUNT(*) from Blog";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public int countByCategory(String number) {

        String query = "select COUNT(*) from Blog\n"
                + "where category_id = ?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, number);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public int countByTitle(String name) {

        String query = "select COUNT(*) from Blog\n"
                + "where title like ?";
        try {
            conn = new DBContext().getConnection();//mo ket noi voi sql
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + name + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public static void main(String[] args) {
        DAO dao = new DAO();
        User user = dao.login("admin@fpt.edu.vn", "admin123");
        List<Slider> listS = dao.getSlider();
        List<BlogDTO> listP = dao.getListPostByCategory("9", 1);
        List<CourseDTO> listC = dao.getCourse();
        List<BlogCategory> listBC = dao.getListCategory();
        List<UserMedia> listM = dao.getMedia(1);

        int num = dao.countByCategory("9");
        for (UserMedia o : listM) {
            System.out.println(o);
        }

        System.out.println("---");
        dao.editMedia("1", "test", "image", "mota");

    }
}
