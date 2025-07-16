package DAO;

import context.DBContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.UserMedia;

public class UserMediaDAO extends DBContext {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<UserMedia> getMedia(int userId) {
        List<UserMedia> list = new ArrayList<>();
        String query = "select * from UserMedia where USER_ID = ?";
        try {
            conn = new DBContext().getConnection();
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
        } catch (Exception e) {}
        return list;
    }

    public UserMedia getMediaByid(String id) {
        String query = "select * from UserMedia where id = ?";
        try {
            conn = new DBContext().getConnection();
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
        } catch (Exception e) {}
        return null;
    }

    public void editMedia(String mediaId, String path, String type, String desc) {
        String query = "UPDATE UserMedia SET media_path = ?, media_type = ?, description = ? WHERE id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, path);
            ps.setString(2, type);
            ps.setString(3, desc);
            ps.setString(4, mediaId);
            ps.executeUpdate();
        } catch (Exception e) {}
    }

    public void addMedia(String userId, String media_path, String media_type, String description) {
        String query = "INSERT INTO [dbo].[UserMedia] ([user_id],[media_path],[media_type],[description]) VALUES (?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ps.setString(2, media_path);
            ps.setString(3, media_type);
            ps.setString(4, description);
            ps.executeUpdate();
        } catch (Exception e) {}
    }

    public void deleteMediaById(String id) {
        String query = "DELETE FROM UserMedia WHERE id = ?";
        try  {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {}
    }
}
