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
import com.example.eea_part2.Model.JwtRequest;
import com.example.eea_part2.Model.JwtResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity{

    public Button adminButton;


    EditText email;
    EditText password;
    public Button loginButton;

    private CallAPI call;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email =findViewById(R.id.email_input);
        password = findViewById(R.id.password);


        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String e1 = email.getText().toString();
                String e2 = password.getText().toString();

                if(TextUtils.isEmpty(e1)){
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(e2)){
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_LONG).show();
                }
                else{

                JwtRequest jwtRequest = new JwtRequest();
                API api = new API();

                jwtRequest.setEmail(email.getText().toString());
                jwtRequest.setPassword(password.getText().toString());

                CallAPI callAPI = api.getRetrofit().create(CallAPI.class);

                call = API.getRetrofit().create(CallAPI.class);
                Call<JwtResponse> jwtResponseCall = call.authenticateUser(jwtRequest);

                jwtResponseCall.enqueue(new Callback<JwtResponse>() {
                    @Override
                    public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                        if(response.isSuccessful()){
                            JwtResponse jwtResponse = response.body();

                            SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("token",jwtResponse.getJwttoken());

                            System.out.println(jwtResponse.getJwttoken());
                            editor.apply();
                            if(jwtResponse != null){
                                switch (jwtResponse.getUserType()) {
                                    case "Admin": {
                                        Intent intent = new Intent(MainActivity.this, AdminHome.class);
                                        startActivity(intent);
                                        break;
                                    }
                                    case "Lecturer": {
                                        Intent intent = new Intent(MainActivity.this, TimetableLecturer.class);
                                        startActivity(intent);
                                        break;
                                    }
                                    case "Student": {
                                        Intent intent = new Intent(MainActivity.this, TimetableStudent.class);
                                        startActivity(intent);
                                        break;
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "Login Successful! " + jwtResponse.getEmail(), Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Please Enter Valid Credentials", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<JwtResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                    }

                });
            }
            }
        });
    }

    public void Login(){

        JwtRequest jwtRequest = new JwtRequest();
        API api = new API();

        jwtRequest.setEmail(email.getText().toString());
        jwtRequest.setPassword(password.getText().toString());

        CallAPI callAPI = api.getRetrofit().create(CallAPI.class);

        call = API.getRetrofit().create(CallAPI.class);
        Call<JwtResponse> jwtResponseCall = call.authenticateUser(jwtRequest);

        jwtResponseCall.enqueue(new Callback<JwtResponse>() {
            @Override
            public void onResponse(Call<JwtResponse> call, Response<JwtResponse> response) {
                if(response.isSuccessful()){
                    JwtResponse jwtResponse = response.body();
                    if(jwtResponse != null){
                        switch (jwtResponse.getUserType()) {
                            case "Admin": {
                                Intent intent = new Intent(MainActivity.this, AdminHome.class);
                                startActivity(intent);
                                break;
                            }
                            case "Lecturer": {
                                Intent intent = new Intent(MainActivity.this, LecturerHome.class);
                                startActivity(intent);
                                break;
                            }
                            case "Student": {
                                Intent intent = new Intent(MainActivity.this, StudentHome.class);
                                startActivity(intent);
                                break;
                            }
                        }
                        Toast.makeText(getApplicationContext(), "Login Successful! " + jwtResponse.getEmail(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<JwtResponse> call, Throwable t) {

            }
        });
    }
}