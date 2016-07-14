package com.pcm.pcmmanager.data;

/**
 * Created by LG on 2016-06-21.
 */
public class PersonalInfoSearch {
    int usersn;
    String username;
    String email;
    String phone;
    String roles;
    String status;
    String usertype1;
    String usertype2;

    public String getUsertype1() {
        return usertype1;
    }

    public void setUsertype1(String usertype1) {
        this.usertype1 = usertype1;
    }

    public String getUsertype2() {
        return usertype2;
    }

    public void setUsertype2(String usertype2) {
        this.usertype2 = usertype2;
    }

    public int getUsersn() {
        return usersn;
    }

    public void setUsersn(int usersn) {
        this.usersn = usersn;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
