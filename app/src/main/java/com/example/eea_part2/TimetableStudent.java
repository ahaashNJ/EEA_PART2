package com.example.eea_part2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Adapter.StudentTimetableAdapter;
import com.example.eea_part2.Adapter.TimetableNonAdminAdapter;
import com.example.eea_part2.Model.Timetable;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimetableStudent extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    public CardView c1, c2;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable_student);

        c1 = (CardView) findViewById(R.id.StudentToday);
        c2 = (CardView) findViewById(R.id.StudentAll);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.NavMyModules:{
                        Intent intent = new Intent(TimetableStudent.this, MyModulesStudent.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "My Modules", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.NavMyAccount: {
                        Intent intent = new Intent(TimetableStudent.this, MyAccount.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "My Account", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.Logout:{
                        SharedPreferences preferences =getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.clear();
                        editor.apply();
                        finish();
                        Intent intent = new Intent(TimetableStudent.this, MainActivity.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "You have logged out of the System", Toast.LENGTH_LONG).show();
                        break;}
                    default: {
                        Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_LONG).show();
                        break;
                    }
                }

                return true;
            }

        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.recyclerViewStudent);

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;

        Call<List<Timetable>> getTimetableStudent = API.getRetrofit().create(CallAPI.class).getAllLecturesStudent(jwtToken);
        System.out.println(jwtToken);

        getTimetableStudent.enqueue(new Callback<List<Timetable>>() {
            @Override
            public void onResponse(Call<List<Timetable>> call, Response<List<Timetable>> response) {
                List<Timetable> timetableList = response.body();
                TimetableNonAdminAdapter studentTimetableAdapter = new TimetableNonAdminAdapter(timetableList);
                recyclerView.setAdapter(studentTimetableAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(TimetableStudent.this));

            }

            @Override
            public void onFailure(Call<List<Timetable>> call, Throwable t) {
                Toast.makeText(TimetableStudent.this, "Loading Unsuccessful", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.StudentToday: {
                Intent intent = new Intent(TimetableStudent.this, StudentHomeNew.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Today's Lecture", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.StudentAll: {
                Intent intent = new Intent(TimetableStudent.this, TimetableStudent.class);
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}