package com.example.eea_part2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.eea_part2.Model.Timetable;
import com.google.android.material.navigation.NavigationView;

public class AdminHome extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    public CardView c1, c2, c3, c4, c5;
    public NavigationView navigationView;
    public DrawerLayout drawerLayout;
    public Toolbar toolbar;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);


        c1 = (CardView) findViewById(R.id.batchCard);
        c2 = (CardView) findViewById(R.id.moduleCard);
        c3 = (CardView) findViewById(R.id.userCard);
        c4 = (CardView) findViewById(R.id.classCard);
        c5 = (CardView) findViewById(R.id.timetableCard);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
        c3.setOnClickListener(this);
        c4.setOnClickListener(this);
        c5.setOnClickListener(this);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(AdminHome.this, drawerLayout, R.string.nav_open, R.string.nav_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.NavBatches:{
                        Intent intent = new Intent(AdminHome.this, BatchList.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "All Batches", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.NavModules: {
                        Intent intent = new Intent(AdminHome.this, ModuleList.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "All Modules", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.NavClasses: {
                        Intent intent = new Intent(AdminHome.this, ClassroomList.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "All Classrooms", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.NavUsers: {
                        Intent intent = new Intent(AdminHome.this, ViewStudents.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "View Students", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.NavTimetable: {
                        Intent intent = new Intent(AdminHome.this, TimetableAdmin.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "All Lectures", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case R.id.MyAccount: {
                        Intent intent = new Intent(AdminHome.this, AdminMyAccount.class);
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
                        Intent intent = new Intent(AdminHome.this, MainActivity.class);
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

//        setActionBar(toolbar);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.batchCard:{
                Intent intent = new Intent(AdminHome.this, BatchList.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "All Batches", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.moduleCard: {
                Intent intent = new Intent(AdminHome.this, ModuleList.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "All Modules", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.classCard: {
                Intent intent = new Intent(AdminHome.this, ClassroomList.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "All Classrooms", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.userCard: {
                Intent intent = new Intent(AdminHome.this, ViewStudents.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "View Students", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.timetableCard:{
                Intent intent = new Intent(AdminHome.this, TimetableAdmin.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "All Lectures", Toast.LENGTH_LONG).show();
                break;}
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
