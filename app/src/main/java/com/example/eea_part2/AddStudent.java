package com.example.eea_part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.Model.Classroom;
import com.example.eea_part2.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudent extends AppCompatActivity {

    EditText firstName, lastName, email, contactNumber, batchId;
    Button add;
    Spinner batch;
    String batchSelect;

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;

        Call<List<Batch>> getBatchesForStudent = API.getRetrofit().create(CallAPI.class).getBatchesForStudent(jwtToken);

        getBatchesForStudent.enqueue(new Callback<List<Batch>>() {
            @Override
            public void onResponse(Call<List<Batch>> call, Response<List<Batch>> response) {
                if(response.isSuccessful()){

                    List <Batch> batchList = response.body();
                    Spinner spinner = findViewById(R.id.select_batch);

                    ArrayAdapter<Batch> batches = new ArrayAdapter<>(AddStudent.this, android.R.layout.simple_spinner_item, batchList);
                    batches.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(batches);
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            batchSelect = adapterView.getItemAtPosition(i).toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                            Toast.makeText(getApplicationContext(), "Wada na hutto", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Batch>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Wada na", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        firstName = (EditText)findViewById(R.id.first_name);
        lastName = (EditText)findViewById(R.id.last_name);
        email = (EditText)findViewById(R.id.email);
        contactNumber = (EditText)findViewById(R.id.contact_number);
        add = (Button)findViewById(R.id.add_button);
        batch = (Spinner)findViewById(R.id.select_batch);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fName = firstName.getText().toString();
                String lName = lastName.getText().toString();
                String emailAddress = email.getText().toString();
                String number = contactNumber.getText().toString();

                if(TextUtils.isEmpty(fName)){
                    Toast.makeText(getApplicationContext(), "Enter First Name", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(lName)){
                    Toast.makeText(getApplicationContext(), "Enter Last Name", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(emailAddress)){
                    Toast.makeText(getApplicationContext(), "Please Enter Email Address", Toast.LENGTH_LONG).show();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())
                {
                    Toast.makeText(getApplicationContext(), "Please Enter a valid Email Address", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(number)){
                    Toast.makeText(getApplicationContext(), "Enter Contact Number", Toast.LENGTH_LONG).show();
                }
                else if(!(number.length() == 10)){
                    Toast.makeText(getApplicationContext(), "Contact Number should consist 10 digits", Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
                    String name = preferences.getString("token", null);
                    String jwtToken = "Bearer " +name;

                    User user =  new User();

                    user.setFirstName(firstName.getText().toString());
                    user.setLastName(lastName.getText().toString());
                    user.setEmail(email.getText().toString());
                    user.setContactNumber(contactNumber.getText().toString());
                    user.setBatchId(batchSelect);
                    System.out.println(batchSelect);
                    CallAPI callAPI= API.getRetrofit().create(CallAPI.class);
                    Call<User> addStudent = callAPI.addStudent(jwtToken,user);

                    addStudent.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                            if(response.isSuccessful()){
                                Intent intent = new Intent(AddStudent.this, ViewStudents.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Student Added Successfully", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Failed to Add Student" , Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failed to Add Student" , Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
        });
    }
}