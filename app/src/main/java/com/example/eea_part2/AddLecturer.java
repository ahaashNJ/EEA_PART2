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
import com.example.eea_part2.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLecturer extends AppCompatActivity {

    EditText e1, e2, e3, e4;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lecturer);

        e1 = (EditText)findViewById(R.id.first_name);
        e2 = (EditText)findViewById(R.id.last_name);
        e3 = (EditText)findViewById(R.id.email);
        e4 = (EditText)findViewById(R.id.contact_number);
        add = (Button)findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fName = e1.getText().toString();
                String lName = e2.getText().toString();
                String emailAddress = e3.getText().toString();
                String number = e4.getText().toString();

                if(TextUtils.isEmpty(fName)){
                    Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(lName)){
                    Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(emailAddress)){
                    Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(number)){
                    Toast.makeText(getApplicationContext(), "Enter Contact Number", Toast.LENGTH_LONG).show();
                }
                else if(!(number.length() == 10)){
                    Toast.makeText(getApplicationContext(), "Contact Number should consist 10 digits", Toast.LENGTH_LONG).show();
                }
                else if(fName.length()>20){
                    Toast.makeText(getApplicationContext(), "Maximum number of characters(20) exceeded for first Name", Toast.LENGTH_LONG).show();
                }
                else if(lName.length()>20){
                    Toast.makeText(getApplicationContext(), "Maximum number of characters(20) exceeded for last Name", Toast.LENGTH_LONG).show();
                }
                else if(emailAddress.length()>30){
                    Toast.makeText(getApplicationContext(), "Maximum number of characters(30) exceeded for last Name", Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
                    String name = preferences.getString("token", null);
                    String jwtToken = "Bearer " +name;

                    User user =  new User();

                    user.setFirstName(e1.getText().toString());
                    user.setLastName(e2.getText().toString());
                    user.setEmail(e3.getText().toString());
                    user.setContactNumber(e4.getText().toString());
                    CallAPI callAPI= API.getRetrofit().create(CallAPI.class);
                    Call<User> addLecturer = callAPI.addLecturer(jwtToken,user);

                    addLecturer.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if(response.isSuccessful()){
                                Intent intent = new Intent(AddLecturer.this, ViewLecturers.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Lecturer Added Successfully", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Failed to Add Lecturer" , Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failed to Add Lecturer" , Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }
}