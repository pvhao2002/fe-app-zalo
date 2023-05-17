package com.author.toan.response;

import com.author.toan.models.User;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestAddFriend implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("owner")
    @Expose
    private User owner;

    @SerializedName("receiver")
    @Expose
    private User receiver;

    @SerializedName("status")
    @Expose
    private String status;

    public RequestAddFriend(String id, User owner, User receiver, String status) {
        this.id = id;
        this.owner = owner;
        this.receiver = receiver;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
