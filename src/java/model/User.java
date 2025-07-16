package model;

import java.util.Date;

public class User {
    private int id;
    private String email;
    private String password;
    private int role_id;
    private Date dob;
    private Date created_date;
    private String first_name;
    private String last_name;
    private String gender;
    private String phone;
    private String image;
    private String address;
    private int status;

    public User() {
    }

    public User(int id, String email, String password, int role_id, Date dob, Date created_date, String first_name, String last_name, String gender, String phone, String image, String address, int status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role_id = role_id;
        this.dob = dob;
        this.created_date = created_date;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.phone = phone;
        this.image = image;
        this.address = address;
        this.status = status;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return this.role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getCreated_date() {
        return this.created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getFirst_name() {
        return this.first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return this.last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toString() {
        return "User{id=" + this.id + ", email=" + this.email + ", password=" + this.password + ", role_id=" + this.role_id + ", dob=" + this.dob + ", created_date=" + this.created_date + ", first_name=" + this.first_name + ", last_name=" + this.last_name + ", gender=" + this.gender + ", phone=" + this.phone + ", image=" + this.image + ", address=" + this.address + ", status=" + this.status + "}";
    }

    public String getFullName() {
        String ln = this.last_name != null ? this.last_name : "";
        String fn = this.first_name != null ? this.first_name : "";
        return (ln + " " + fn).trim();
    }
}