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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Adapter.ViewBatchAdapter;
import com.example.eea_part2.Adapter.ViewClassroomAdapter;
import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.Model.Classroom;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassroomList extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    public CardView c1, c2;
    Button deleteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_list);

        c1 = (CardView) findViewById(R.id.addClassrooms);
        c2 = (CardView) findViewById(R.id.viewClassrooms);
        deleteButton = (Button)findViewById(R.id.deleteClassroom);


        c1.setOnClickListener(this);
        c2.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.recyclerViewClassroom);

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;

        Call<List<Classroom>> getAllClassrooms = API.getRetrofit().create(CallAPI.class).getAllClassrooms(jwtToken);
        System.out.println(jwtToken);

        getAllClassrooms.enqueue(new Callback<List<Classroom>>() {
            @Override
            public void onResponse(Call<List<Classroom>> call, Response<List<Classroom>> response) {
                List<Classroom> classrooms = response.body();
                ViewClassroomAdapter viewClassroomAdapter = new ViewClassroomAdapter(classrooms, getApplicationContext());
                recyclerView.setAdapter(viewClassroomAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ClassroomList.this));
//                viewClassroomAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Classroom>> call, Throwable t) {
                Toast.makeText(ClassroomList.this, "Loading Unsuccessful", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addClassrooms: {
                Intent intent = new Intent(ClassroomList.this, AddClassroom.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Add Classroom", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.viewClassrooms: {
                Intent intent = new Intent(ClassroomList.this, ClassroomList.class);
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