package com.example.eea_part2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.Model.Classroom;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBatch extends AppCompatActivity {

    DatePicker picker, picker1;
    Button startDate, endDate;
    TextView startDateText, endDateText;
    EditText e1;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batch);

        e1=(EditText)findViewById(R.id.batchName);
        add=(Button)findViewById(R.id.add);

        startDateText=(TextView)findViewById(R.id.startDateText);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        startDate=(Button)findViewById(R.id.button1);

        picker.setMinDate(System.currentTimeMillis() - 1000);
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String month = String.valueOf(picker.getMonth() + 1);
                String day = String.valueOf(picker.getDayOfMonth());
                if (month.length() == 1) {
                    month = "0" + month;
                }
                else if(day.length() ==1){
                    day = "0"+day;
                }

                startDateText.setText(picker.getYear()+"-"+(month)+"-"+ (day));

            }
        });

        endDateText=(TextView)findViewById(R.id.endDateText);
        picker1=(DatePicker)findViewById(R.id.datePicker2);
        endDate=(Button)findViewById(R.id.button2);
        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String months = String.valueOf(picker1.getMonth() + 1);
                String day = String.valueOf(picker1.getDayOfMonth());
                if (months.length() == 1) {
                    months = "0" + months;
                }
                else if(day.length() ==1){
                    day = "0"+day;
                }
                endDateText.setText(picker1.getYear()+"-"+(months)+"-"+ (day));
                System.out.println(picker1.getYear()+"-"+(months)+"-"+ picker1.getDayOfMonth()+"HELLOOOOOOOO");
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String batchName = e1.getText().toString();
                String startDate = startDateText.getText().toString();
                String endDate = endDateText.getText().toString();

                if(TextUtils.isEmpty(batchName)){
                    Toast.makeText(getApplicationContext(), "Enter Batch Name", Toast.LENGTH_LONG).show();
                }
                else if (TextUtils.isEmpty(startDate)){
                    Toast.makeText(getApplicationContext(), "Select Start Date", Toast.LENGTH_LONG).show();
                }
                else if(TextUtils.isEmpty(endDate)){
                    Toast.makeText(getApplicationContext(), "Select End Date", Toast.LENGTH_LONG).show();
                }
                else{
                    SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
                    String name = preferences.getString("token", null);
                    String jwtToken = "Bearer " +name;

                    Batch batch = new Batch();

                    batch.setBatchName(e1.getText().toString());
                    batch.setStartDate(startDateText.getText().toString());
                    System.out.println(startDateText.toString());
                    batch.setEndDate(endDateText.getText().toString());

                    CallAPI callAPI= API.getRetrofit().create(CallAPI.class);
                    Call<Batch> addBatch = callAPI.addBatch(jwtToken,batch);

                    addBatch.enqueue(new Callback<Batch>() {
                        @Override
                        public void onResponse(Call<Batch> call, Response<Batch> response) {

                            if(response.isSuccessful()){
                                Intent intent = new Intent(AddBatch.this, BatchList.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Batch Successfully Added", Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Failed to Add Batch", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Batch> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Failed to Add Batch" , Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

    }
}