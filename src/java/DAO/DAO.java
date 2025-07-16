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

   
}
