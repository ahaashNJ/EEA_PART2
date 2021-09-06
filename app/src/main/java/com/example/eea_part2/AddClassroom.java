package com.example.eea_part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Model.Classroom;
import com.example.eea_part2.Model.JwtResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddClassroom extends AppCompatActivity{

    EditText e1, e2, e3;
    Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_classroom);

        e1 = (EditText) findViewById(R.id.classroom_id_input);
        e2 = (EditText) findViewById(R.id.number_seats_input);
        e3 = (EditText) findViewById(R.id.floor_number_input);
        add = (Button) findViewById(R.id.add_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String classroomId = e1.getText().toString();
                String numberOfSeats = e2.getText().toString();
                String floorNumber = e3.getText().toString();

                if(TextUtils.isEmpty(classroomId)){
                    Toast.makeText(getApplicationContext(), "Enter Classroom ID", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(numberOfSeats)){
                    Toast.makeText(getApplicationContext(), "Enter Seat Number", Toast.LENGTH_LONG).show();
                }
                else if(numberOfSeats.length() > 4){
                    Toast.makeText(getApplicationContext(), "Please Enter a Number less than 999", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(floorNumber)){
                    Toast.makeText(getApplicationContext(), "Enter Floor", Toast.LENGTH_LONG).show();
                }
                else if(floorNumber.length() > 2){
                    Toast.makeText(getApplicationContext(), "Please Enter a Number less than 10", Toast.LENGTH_LONG).show();
                }
                else{

                    SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
                    String name = preferences.getString("token", null);
                    String jwtToken = "Bearer " +name;

                    Classroom classroom = new Classroom();

                    classroom.setClassroomId(e1.getText().toString());
                    classroom.setNoOfSeats(e2.getText().toString());
                    classroom.setFloor(e3.getText().toString());

                    CallAPI callAPI= API.getRetrofit().create(CallAPI.class);
                    Call<Classroom> addClassroom = callAPI.addClassroom(jwtToken,classroom);

                    addClassroom.enqueue(new Callback<Classroom>() {
                        @Override
                        public void onResponse(Call<Classroom> call, Response<Classroom> response) {
                            if(response.isSuccessful()){
                                Intent intent = new Intent(AddClassroom.this, ClassroomList.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Classroom Successfully Added", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Failed to Add Classroom", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Classroom> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failed to Add Classroom" , Toast.LENGTH_LONG).show();
                        }
                    });


                }

            }
        });


    }
}