package com.example.gitlist.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class gituserresponse {
    @SerializedName("total_count")
    public int totalcount;
    @SerializedName("items")
    public List<gituser> users = new ArrayList<>();
}
