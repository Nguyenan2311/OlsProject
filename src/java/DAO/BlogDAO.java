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

public class BlogDAO extends DBContext {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Blog> getPost() {
        List<Blog> list = new ArrayList<>();
        String query = "SELECT  * FROM [dbo].[Blog]";
        try {
            conn = new DBContext().getConnection();
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
        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value FROM [dbo].[Blog] b JOIN [dbo].[User] u ON b.author_id = u.id join Setting s on b.category_id = s.id ORDER BY created_date DESC";
        try {
            conn = new DBContext().getConnection();
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
        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value FROM [dbo].[Blog] b JOIN [dbo].[User] u ON b.author_id = u.id join Setting s on b.category_id = s.id where b.category_id = ? ORDER BY b.id offset ? rows fetch next 3 rows only";
        try {
            conn = new DBContext().getConnection();
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
        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value FROM [dbo].[Blog] b JOIN [dbo].[User] u ON b.author_id = u.id join Setting s on b.category_id = s.id where b.id = ?";
        try {
            conn = new DBContext().getConnection();
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
        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value FROM [dbo].[Blog] b JOIN [dbo].[User] u ON b.author_id = u.id join Setting s on b.category_id = s.id ORDER BY created_date DESC offset ? rows fetch next 3 rows only";
        try {
            conn = new DBContext().getConnection();
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
        String query = "SELECT b.id, b.thumbnail_url, b.title, b.content, b.brief_info, b.author_id, b.created_date,b.updated_date,b.category_id, u.first_name + ' ' + u.last_name AS author_name, s.value FROM [dbo].[Blog] b JOIN [dbo].[User] u ON b.author_id = u.id join Setting s on b.category_id = s.id where b.title like ? ORDER BY b.id offset ? rows fetch next 3 rows only";
        try {
            conn = new DBContext().getConnection();
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
        String query = "select id, value, description from Setting where setting_type_id = 3";
        try {
            conn = new DBContext().getConnection();
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
        String query = "SELECT top 5 * FROM [dbo].[Blog] ORDER BY created_date DESC;";
        try {
            conn = new DBContext().getConnection();
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

    public int getTotalBlog() {
        String query = "select COUNT(*) from Blog";
        try {
            conn = new DBContext().getConnection();
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
        String query = "select COUNT(*) from Blog where category_id = ?";
        try {
            conn = new DBContext().getConnection();
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
        String query = "select COUNT(*) from Blog where title like ?";
        try {
            conn = new DBContext().getConnection();
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
}
