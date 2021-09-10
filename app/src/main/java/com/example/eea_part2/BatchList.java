package com.example.eea_part2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Adapter.StudentTimetableAdapter;
import com.example.eea_part2.Adapter.ViewBatchAdapter;
import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.Model.Timetable;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BatchList extends AppCompatActivity implements View.OnClickListener{

    RecyclerView recyclerView;
    public CardView c1, c2;
    ImageButton search;
    EditText searchKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batch_list);

        c1 = (CardView) findViewById(R.id.addBatches);
        c2 = (CardView) findViewById(R.id.viewBatches);
        search = (ImageButton) findViewById(R.id.searchButton);
        searchKeyword = (EditText)findViewById(R.id.searchBatch);

        c1.setOnClickListener(this);
        c2.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        recyclerView = findViewById(R.id.recyclerViewStudent);

        SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        String name = preferences.getString("token", null);
        String jwtToken = "Bearer " +name;

        Call<List<Batch>> getAllBatches = API.getRetrofit().create(CallAPI.class).getAllBatches(jwtToken);
        System.out.println(jwtToken);

        getAllBatches.enqueue(new Callback<List<Batch>>() {
            @Override
            public void onResponse(Call<List<Batch>> call, Response<List<Batch>> response) {
                List<Batch> batches = response.body();
                ViewBatchAdapter viewBatchAdapter = new ViewBatchAdapter(batches, getApplicationContext());
                recyclerView.setAdapter(viewBatchAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(BatchList.this));

            }

            @Override
            public void onFailure(Call<List<Batch>> call, Throwable t) {
                Toast.makeText(BatchList.this, "Loading Unsuccessful", Toast.LENGTH_LONG).show();
                System.out.println(t);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
                String name = preferences.getString("token", null);
                String jwtToken = "Bearer " +name;

                Call<List<Batch>> getSearchBatches = API.getRetrofit().create(CallAPI.class).searchBatches(jwtToken, searchKeyword.getText().toString());
                getSearchBatches.enqueue(new Callback<List<Batch>>() {
                    @Override
                    public void onResponse(Call<List<Batch>> call, Response<List<Batch>> response) {
                        if(response.isSuccessful()){
                            List<Batch> batches = response.body();
                            ViewBatchAdapter viewBatchAdapter = new ViewBatchAdapter(batches, getApplicationContext());
                            recyclerView.setAdapter(viewBatchAdapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(BatchList.this));
                            Toast.makeText(BatchList.this, "Found items for " +searchKeyword.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(BatchList.this, "No items that matches Keyword"+searchKeyword.getText().toString(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Batch>> call, Throwable t) {
                        Toast.makeText(BatchList.this, "Loading Unsuccessful for " +searchKeyword.getText().toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.addBatches: {
                Intent intent = new Intent(BatchList.this, AddBatch.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "Add Batch", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.viewBatches: {
                Intent intent = new Intent(BatchList.this, BatchList.class);
                startActivity(intent);
                break;
            }
            default: {
                Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_LONG).show();
                break;
            }
        }

    }

    public void onBackPressed() {
        Intent intent = new Intent(this, AdminHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}