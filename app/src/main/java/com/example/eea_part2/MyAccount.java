package com.example.eea_part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Adapter.ViewClassroomAdapter;
import com.example.eea_part2.Model.Classroom;
import com.example.eea_part2.Model.UserDTO;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAccount extends AppCompatActivity {

    TextView firstName, lastName, email, contactNumber, batch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        firstName = (TextView)findViewById(R.id.first_name_account);
        lastName = (TextView)findViewById(R.id.last_name_account);
        email = (TextView)findViewById(R.id.email_account);
        contactNumber = (TextView)findViewById(R.id.contact_number);
        batch = (TextView)findViewById(R.id.batch_account);


    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;

        Call<UserDTO> getMyAccount = API.getRetrofit().create(CallAPI.class).getMyAccount(jwtToken);
        getMyAccount.enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                UserDTO userDTO = response.body();
                firstName.setText(userDTO.getFirstName());
                lastName.setText(userDTO.getLastName());
                email.setText(userDTO.getEmail());
                contactNumber.setText(userDTO.getContactNumber());
                batch.setText(userDTO.getBatchId().getBatchName());

            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {


            }
        });
    }
}