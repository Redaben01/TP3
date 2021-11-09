package com.example.gitlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gitlist.model.gituser;
import com.example.gitlist.model.gituserresponse;
import com.example.gitlist.model.userslistviewmodel;
import com.example.gitlist.service.getreposerviceapi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
public List<gituser> data = new ArrayList<>();
public static final String USERNAME_PARAM = "username";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.edittext);
        final ListView listView = findViewById(R.id.listview);

        final userslistviewmodel listviewmodel = new userslistviewmodel(this, R.layout.userslistviewlayout, data);
        listView.setAdapter(listviewmodel);

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String q = editText.getText().toString();
                         getreposerviceapi service = retrofit.create(getreposerviceapi.class);
                        Call<gituserresponse> gituserresponseCall = service.searchusers(q);
                        gituserresponseCall.enqueue(
                                new Callback<gituserresponse>() {
                                    @Override
                                    public void onResponse(Call<gituserresponse> call, Response<gituserresponse> response) {
                                        if (!response.isSuccessful()){
                                            Log.i("error" , String.valueOf(response.code()));
                                            return;
                                        }
                                        gituserresponse gituserresponse = response.body();
                                        for (gituser user : gituserresponse.users){
                                            Log.i("username" , user.username);
                                            data.add(user);
                                        }
                                        listviewmodel.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onFailure(Call<gituserresponse> call, Throwable t) {
                                        Log.i("error", "Error onFailure");
                                    }
                                }
                        );

                    }
                }
        );
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        int i = 0;
                        String username = data.get(i).username;
                        Intent intent = new Intent(getApplicationContext(), profileactivity.class);
                        intent.putExtra(USERNAME_PARAM, username);
                        startActivity(intent);
                    }
                }
        );
    }
}