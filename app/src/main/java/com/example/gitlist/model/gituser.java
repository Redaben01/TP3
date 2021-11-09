package com.example.gitlist.model;

import com.google.gson.annotations.SerializedName;

public class gituser {
    public int id;
    @SerializedName("login")
    public String username;
    @SerializedName("avatar_url")
    public String avatarurl;
    public int score;
}
