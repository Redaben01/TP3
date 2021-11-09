package com.example.gitlist.service;

import com.example.gitlist.model.gitrepo;
import com.example.gitlist.model.gituserresponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface getreposerviceapi {
    @GET("search/users")
    public Call<gituserresponse> searchusers(@Query("q") String query);

    @GET("users/{u}/repos")
    public Call<List<gitrepo>> userRepos(@Path("u") String username);
}
