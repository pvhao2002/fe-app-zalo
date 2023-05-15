package com.author.toan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Chat implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("isGroup")
    @Expose
    private boolean isGroup;

    @SerializedName("users")
    @Expose
    private List<User> users;

    @SerializedName("messages")
    @Expose
    private List<Message> messages;

    @SerializedName("owner")
    @Expose
    private User owner;

    @SerializedName("avatar")
    @Expose
    private Avatar avatar;

    public Chat(String id, String name, boolean isGroup, List<User> users, List<Message> messages, User owner, Avatar avatar) {
        this.id = id;
        this.name = name;
        this.isGroup = isGroup;
        this.users = users;
        this.messages = messages;
        this.owner = owner;
        this.avatar = avatar;
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

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}
