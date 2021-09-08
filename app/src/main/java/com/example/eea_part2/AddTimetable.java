package com.example.eea_part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.Model.Classroom;
import com.example.eea_part2.Model.ClassroomDTO;
import com.example.eea_part2.Model.Module;
import com.example.eea_part2.Model.ModuleDTO;
import com.example.eea_part2.Model.Timetable;
import com.example.eea_part2.Model.TimetableDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTimetable extends AppCompatActivity {



    DatePicker datePicker;
    TimePicker startPicker, endPicker;
    Button bDate, bStart, bEnd, add;
    TextView startTime, endTime, Date;
    Spinner spinnerBatch, spinnerClassroom;
    ClassroomDTO classroomSelect;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timetable);

        spinnerBatch = (Spinner)findViewById(R.id.batchNameLecture);
        spinnerClassroom = (Spinner)findViewById(R.id.classroomNameLecture);

        datePicker = (DatePicker)findViewById(R.id.datePicker1);
        bDate = (Button)findViewById(R.id.button1);
        Date = (TextView)findViewById(R.id.DateTextLecture);
        datePicker.setMinDate(System.currentTimeMillis() - 1000);
        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month = String.valueOf(datePicker.getMonth() + 1);
                String day = String.valueOf(datePicker.getDayOfMonth());
                if (month.length() == 1) {
                    month = "0" + month;
                }
                else if(day.length() ==1){
                    day = "0"+day;
                }

                Date.setText(datePicker.getYear()+"-"+(month)+"-"+ (day));
            }
        });

        startPicker = (TimePicker)findViewById(R.id.timePicker2);
        startPicker.setIs24HourView(true);
        bStart = (Button)findViewById(R.id.button2);
        startTime = (TextView)findViewById(R.id.startTimeTextLecture);
        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime.setText(startPicker.getHour()+":"+startPicker.getMinute()+":00");
            }
        });

        endPicker = (TimePicker)findViewById(R.id.timePicker3);
        endPicker.setIs24HourView(true);
        bEnd = (Button)findViewById(R.id.button3);
        endTime = (TextView)findViewById(R.id.endTimeTextLecture);
        bEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTime.setText(endPicker.getHour()+":"+endPicker.getMinute()+":00");
            }
        });
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sTime = startTime.getText().toString();
                String eTime = endTime.getText().toString();
                String dDate = Date.getText().toString();

                if(TextUtils.isEmpty(dDate)){
                    Toast.makeText(getApplicationContext(), "Select Date", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(sTime)){
                    Toast.makeText(getApplicationContext(), "Select Start Time", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(eTime)){
                    Toast.makeText(getApplicationContext(), "Select End Time", Toast.LENGTH_LONG).show();
                }
                else{

                SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
                String name = preferences.getString("token", null);
                String jwtToken = "Bearer " +name;

                Intent intent = getIntent();
                ModuleDTO module = (ModuleDTO) intent.getParcelableExtra("Lecture");

                TimetableDTO timetable = new TimetableDTO();

                timetable.setClassroom(classroomSelect.toString());
                timetable.setTimetableDate(Date.getText().toString());
                timetable.setStartTime(startTime.getText().toString());
                timetable.setEndTime(endTime.getText().toString());
                timetable.setModule(module.getModuleName());
                timetable.setBatchList(module.getBatchList());
                System.out.println(module.getBatchList());




                CallAPI callAPI= API.getRetrofit().create(CallAPI.class);
                Call<TimetableDTO> addTimetable = callAPI.addTimetable(jwtToken,timetable);

                addTimetable.enqueue(new Callback<TimetableDTO>() {
                    @Override
                    public void onResponse(Call<TimetableDTO> call, Response<TimetableDTO> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(AddTimetable.this, ModuleList.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Lecture Scheduled Successfully", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Failed to Add Timetable" , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<TimetableDTO> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Error Adding Timetable" , Toast.LENGTH_LONG).show();
                    }
                });

            }
            }
        });



//        ModuleDTO moduleDTO = new ModuleDTO();
//
//        moduleDTO.setModuleName(module.getModuleName());
//        moduleDTO.setLecturer(module.getLecturer());
//        for(Batch batch: module.getBatchList()){
//            batch.getBatchName();
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;


        Call<List<ClassroomDTO>> getClassroomsForTimetable = API.getRetrofit().create(CallAPI.class).getClassroomsForTimetable(jwtToken);
        getClassroomsForTimetable.enqueue(new Callback<List<ClassroomDTO>>() {
            @Override
            public void onResponse(Call<List<ClassroomDTO>> call, Response<List<ClassroomDTO>> response) {
                List <ClassroomDTO> classroomsList = response.body();
                Spinner spinner = (Spinner)findViewById(R.id.classroomNameLecture);

                ArrayAdapter<ClassroomDTO> classroom = new ArrayAdapter<>(AddTimetable.this, android.R.layout.simple_spinner_item, classroomsList);
                classroom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(classroom);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        classroomSelect = (ClassroomDTO) adapterView.getItemAtPosition(i);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                        Toast.makeText(getApplicationContext(), "select Failed", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<ClassroomDTO>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Unable to load Classrooms", Toast.LENGTH_LONG).show();
            }
        });
    }
}