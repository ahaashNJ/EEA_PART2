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
import com.example.eea_part2.Model.Timetable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimetableAdmin extends AppCompatActivity implements View.OnClickListener{


    RecyclerView recyclerView;
    public CardView c1, c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_admin);

        c1 = (CardView) findViewById(R.id.AdminToday);
        c2 = (CardView) findViewById(R.id.AdminAll);

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

        Call<List<Timetable>> getAdminAllTimetable = API.getRetrofit().create(CallAPI.class).getAllLecturesAdmin(jwtToken);
        System.out.println(jwtToken);

        getAdminAllTimetable.enqueue(new Callback<List<Timetable>>() {
            @Override
            public void onResponse(Call<List<Timetable>> call, Response<List<Timetable>> response) {
                List<Timetable> timetableList = response.body();
                StudentTimetableAdapter studentTimetableAdapter = new StudentTimetableAdapter(timetableList, getApplicationContext());
                recyclerView.setAdapter(studentTimetableAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(TimetableAdmin.this));

            }

            @Override
            public void onFailure(Call<List<Timetable>> call, Throwable t) {
                Toast.makeText(TimetableAdmin.this, "Loading Unsuccessful", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.AdminToday: {
                Intent intent = new Intent(TimetableAdmin.this, TimetableTodayAdmin.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Today's Lecture", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.AdminAll: {
                Intent intent = new Intent(TimetableAdmin.this, TimetableAdmin.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "All Lectures", Toast.LENGTH_LONG).show();
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