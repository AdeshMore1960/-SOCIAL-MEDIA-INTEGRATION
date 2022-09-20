package com.example.internshipproject2.Models;

public class Users {

    String profilePic , userName , mail , password , phone , userid;

    public Users() {
    }

    public Users(String profilePic, String userName, String mail, String password, String phone , String userid) {
        this.profilePic = profilePic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.phone = phone;
        this.userid = userid;
    }

    public Users(String userName, String mail, String password, String phone) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.phone = phone;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
