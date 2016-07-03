package com.pcm.pcmmanager.data;

/**
 * Created by LG on 2016-06-07.
 */
public class UserProfile {
    String username;
    String email;
    int expertsn;
    int usersn;
    Boolean pushyn;
    String roles;
    String status;

    public int getExpertsn() {
        return expertsn;
    }

    public void setExpertsn(int expertsn) {
        this.expertsn = expertsn;
    }

    public int getUsersn() {
        return usersn;
    }

    public void setUsersn(int usersn) {
        this.usersn = usersn;
    }

    public Boolean getPushyn() {
        return pushyn;
    }

    public void setPushyn(Boolean pushyn) {
        this.pushyn = pushyn;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
