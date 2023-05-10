package com.author.toan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("avatar")
    @Expose
    private Avatar avatar;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("password")
    private String password;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("isVerified")
    @Expose
    private Boolean isVerified;

    public User() {
    }
    public User(String id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public User(String id, String name, String phone, String role, String token, Boolean isVerified) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.token = token;
        this.isVerified = isVerified;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }


}
