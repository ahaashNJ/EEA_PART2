package com.example.eea_part2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Adapter.ViewBatchAdapter;
import com.example.eea_part2.Adapter.ViewModuleAdapter;
import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.Model.Module;
import com.example.eea_part2.Model.ModuleDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModuleList extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    public CardView c1,c2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_list);

        c1 = (CardView) findViewById(R.id.viewModules);
        c2 = (CardView) findViewById(R.id.addModule);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.recyclerViewStudent);

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;

        Call<List<ModuleDTO>> getAllModules = API.getRetrofit().create(CallAPI.class).getAllModules(jwtToken);
        System.out.println(jwtToken);

        getAllModules.enqueue(new Callback<List<ModuleDTO>>() {
            @Override
            public void onResponse(Call<List<ModuleDTO>> call, Response<List<ModuleDTO>> response) {
                List<ModuleDTO> modules = response.body();
                ViewModuleAdapter viewModuleAdapter = new ViewModuleAdapter(modules);
                recyclerView.setAdapter(viewModuleAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ModuleList.this));

            }

            @Override
            public void onFailure(Call<List<ModuleDTO>> call, Throwable t) {
                Toast.makeText(ModuleList.this, "Loading Unsuccessful", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addModule: {
                Intent intent = new Intent(ModuleList.this, AddModule.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Add Module", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.viewModules: {
                Intent intent = new Intent(ModuleList.this, ModuleList.class);
                startActivity(intent);
                break;
            }
            default: {
                Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_LONG).show();
                break;
            }
        }
    }
}