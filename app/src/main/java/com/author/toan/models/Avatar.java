package com.author.toan.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Avatar implements Serializable {
    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("public_id")
    @Expose
    private String public_id;

    public Avatar(String url, String public_id) {
        this.url = url;
        this.public_id = public_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPublic_id() {
        return public_id;
    }

    public void setPublic_id(String public_id) {
        this.public_id = public_id;
    }
}
