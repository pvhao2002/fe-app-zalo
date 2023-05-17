package com.author.toan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Message implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("sender")
    @Expose
    private User sender;

    @SerializedName("chat")
    @Expose
    private Chat chat;

    public Message( String id, String content, User sender) {
        this.id = id;
        this.sender = sender;
        this.content = content;
    }

    public Message(String id, String content, User sender, Chat chat) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.chat = chat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }
}
