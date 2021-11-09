package com.example.gitlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gitlist.model.gitrepo;
import com.example.gitlist.service.getreposerviceapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class profileactivity extends AppCompatActivity {
    public List<String> data = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityprofile);
        setTitle("Profile");
        Intent intent = getIntent();
        String username = intent.getStringExtra(MainActivity.USERNAME_PARAM);
        TextView textViewprofileusername = findViewById(R.id.textviewprofileusername);
        ListView listViewrepos = findViewById(R.id.listviewrepos);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listViewrepos.setAdapter(stringArrayAdapter);

        textViewprofileusername.setText(username);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getreposerviceapi service = retrofit.create(getreposerviceapi.class);

        Call<List<gitrepo>> gitrepoCall = service.userRepos(username);
        gitrepoCall.enqueue(
                new Callback<List<gitrepo>>() {
                    @Override
                    public void onResponse(Call<List<gitrepo>> call, Response<List<gitrepo>> response) {
                        if (!response.isSuccessful()){
                            Log.e("error", String.valueOf(response.code()));
                            return;
                        }
                        List<gitrepo> gitrepos = (List<gitrepo>) response.body();
                        for (gitrepo gitrepo: gitrepos){
                            String context = "";
                            context += "id: " + gitrepo.id + "\n";
                            context += "name: " + gitrepo.name + "\n";
                            context += "language: " + gitrepo.language + "\n";

                            data.add(context);
                        }
                        stringArrayAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onFailure(Call<List<gitrepo>> call, Throwable t) {

                    }
                }
        );
    }
}
