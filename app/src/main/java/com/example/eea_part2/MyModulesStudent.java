package com.example.eea_part2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Adapter.ViewModuleAdapter;
import com.example.eea_part2.Adapter.ViewMyModulesAdapter;
import com.example.eea_part2.Model.ModuleDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyModulesStudent extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_modules_student);


    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.recyclerViewStudent);

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;

        Call<List<ModuleDTO>> getMyModulesStudent = API.getRetrofit().create(CallAPI.class).getMyModulesStudent(jwtToken);
        System.out.println(jwtToken);

        getMyModulesStudent.enqueue(new Callback<List<ModuleDTO>>() {
            @Override
            public void onResponse(Call<List<ModuleDTO>> call, Response<List<ModuleDTO>> response) {
                List<ModuleDTO> modules = response.body();
                ViewMyModulesAdapter viewModuleAdapter = new ViewMyModulesAdapter(modules);
                recyclerView.setAdapter(viewModuleAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(MyModulesStudent.this));

            }

            @Override
            public void onFailure(Call<List<ModuleDTO>> call, Throwable t) {
                Toast.makeText(MyModulesStudent.this, "Loading Unsuccessful", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });
    }
}