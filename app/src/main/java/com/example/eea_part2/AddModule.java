package com.example.eea_part2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.eea_part2.Model.Module;
import com.example.eea_part2.Model.User;
import com.example.eea_part2.Model.UserDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddModule extends AppCompatActivity {

    EditText e1;
    Spinner e2, e3;
    Button add;
    String batchSelect;
    String lecturerSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_module);

        e1 = (EditText) findViewById(R.id.module_name);
        e2 = (Spinner) findViewById(R.id.select_batch);
        e3 = (Spinner) findViewById(R.id.select_lecturer);
        add = (Button) findViewById(R.id.add_button);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String moduleName = e1.getText().toString();

                if(TextUtils.isEmpty(moduleName)){
                    Toast.makeText(getApplicationContext(), "Enter Module Name", Toast.LENGTH_LONG).show();
                }
                else if(moduleName.length()>40){
                    Toast.makeText(getApplicationContext(), "Maximum number of characters(40) exceeded for module Name", Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
                    String name = preferences.getString("token", null);
                    String jwtToken = "Bearer " +name;

                    Module module = new Module();

                    module.setModuleName(e1.getText().toString());
                    String[] batchString = new String[1];

                    batchString[0] = batchSelect;
                    module.setBatchList(batchString);

                    module.setLecturer(lecturerSelect);
                    System.out.println(lecturerSelect);

                    CallAPI callAPI= API.getRetrofit().create(CallAPI.class);
                    Call<Module> addModule = callAPI.addModule(jwtToken,module);

                    addModule.enqueue(new Callback<Module>() {
                        @Override
                        public void onResponse(Call<Module> call, Response<Module> response) {
                            System.out.println(batchSelect);
                            if(response.isSuccessful()){
                                Intent intent = new Intent(AddModule.this, ModuleList.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Module Added Successfully", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Error Adding Module" , Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Module> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failed to Add Module" , Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;

        Call<List<Batch>> getBatchesForModules = API.getRetrofit().create(CallAPI.class).getBatchesForStudent(jwtToken);
        getBatchesForModules.enqueue(new Callback<List<Batch>>() {
            @Override
            public void onResponse(Call<List<Batch>> call, Response<List<Batch>> response) {

                List <Batch> batchList = response.body();
                Spinner spinner = findViewById(R.id.select_batch);

                ArrayAdapter<Batch> batches = new ArrayAdapter<>(AddModule.this, android.R.layout.simple_spinner_item, batchList);
                batches.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(batches);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        batchSelect = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Toast.makeText(getApplicationContext(), "select Failed", Toast.LENGTH_LONG).show();
                    }
                });
                }

            @Override
            public void onFailure(Call<List<Batch>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load batches", Toast.LENGTH_LONG).show();
            }
        });

        Call<List<UserDTO>> getLecturersForModules = API.getRetrofit().create(CallAPI.class).getLecturersForModules(jwtToken);
        getLecturersForModules.enqueue(new Callback<List<UserDTO>>() {
            @Override
            public void onResponse(Call<List<UserDTO>> call, Response<List<UserDTO>> response) {
                List <UserDTO> userList = response.body();
                Spinner spinner1 = findViewById(R.id.select_lecturer);

                ArrayAdapter<UserDTO> users = new ArrayAdapter<>(AddModule.this, android.R.layout.simple_spinner_item, userList);
                users.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner1.setAdapter(users);
                spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        lecturerSelect = adapterView.getItemAtPosition(i).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Toast.makeText(getApplicationContext(), "Select Fail", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<UserDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load Lecturers", Toast.LENGTH_LONG).show();
            }
        });
    }



}