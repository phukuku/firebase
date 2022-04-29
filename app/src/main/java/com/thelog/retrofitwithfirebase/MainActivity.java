package com.thelog.retrofitwithfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thelog.retrofitwithfirebase.adapter.UserAdapter;
import com.thelog.retrofitwithfirebase.model.User;
import com.thelog.retrofitwithfirebase.service.APIService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    Retrofit retrofit;
    APIService apiService;
    List<User> users = new ArrayList<>();
    private RecyclerView rcMain;
    private UserAdapter userAdapter;
    private final String URL = "http://192.168.1.10/api/";
    private EditText edtUsername, edtEmail;
    private Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = RetrofitObject.getClient(URL);
        apiService = retrofit.create(APIService.class);

        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        btnReg = findViewById(R.id.btnReg);
        rcMain = findViewById(R.id.rcMain);
        users = getUser();
        userAdapter = new UserAdapter(this, users);
        rcMain.setLayoutManager(new LinearLayoutManager(this));
        rcMain.setAdapter(userAdapter);

        btnReg.setOnClickListener(view -> {
            String retrievalUsername = edtUsername.getText().toString();
            String retrievalEmail = edtEmail.getText().toString();

            if (retrievalUsername.isEmpty() && retrievalEmail.isEmpty()) {
                Toast.makeText(this, "Please fill out these fields", Toast.LENGTH_SHORT).show();
                return;
            }

            createUser(retrievalUsername, retrievalEmail);
        });
    }

    private void createUser(String retrievalUsername, String retrievalEmail) {
        User user = new User(retrievalUsername, retrievalEmail);
        Call<User> call = apiService.create(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Add user successfully!", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error occurred::" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<User> getUser() {
        Call<List<User>> call = apiService.get();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    List<User> userR = response.body();
                    for (User user : userR){
                        users.add(new User(user.getUsername(), user.getEmail()));
                    }
                }
                return;
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error occured::" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return users;
    }
}