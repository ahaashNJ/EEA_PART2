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
import com.example.eea_part2.Adapter.StudentTimetableAdapter;
import com.example.eea_part2.Adapter.ViewStudentsAdapter;
import com.example.eea_part2.Model.Timetable;
import com.example.eea_part2.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewStudents extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    public CardView c1, c2, c3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_students);

        c1 = (CardView) findViewById(R.id.StudentView);
        c2 = (CardView) findViewById(R.id.Lecturer);
        c3 = (CardView) findViewById(R.id.addStudentView);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.recyclerViewStudent);

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;

        Call<List<User>> getAllStudents = API.getRetrofit().create(CallAPI.class).getAllStudents(jwtToken);
        System.out.println(jwtToken);

        getAllStudents.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> userList = response.body();
                ViewStudentsAdapter viewStudentsAdapter = new ViewStudentsAdapter(userList);
                recyclerView.setAdapter(viewStudentsAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ViewStudents.this));

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ViewStudents.this, "Loading Unsuccessful", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.StudentView: {
                Intent intent = new Intent(ViewStudents.this, ViewStudents.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "All Students", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.Lecturer: {
                Intent intent = new Intent(ViewStudents.this, ViewLecturers.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "All Lecturers", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.addStudentView: {
                Intent intent = new Intent(ViewStudents.this, AddStudent.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Add Student", Toast.LENGTH_LONG).show();
                break;
            }
            default: {
                Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_LONG).show();
                break;
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, AdminHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}