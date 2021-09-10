package com.example.eea_part2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.Model.Timetable;
import com.example.eea_part2.R;
import com.example.eea_part2.TimetableAdmin;
import com.example.eea_part2.ViewStudents;

import java.sql.Time;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentTimetableAdapter extends RecyclerView.Adapter<StudentTimetableAdapter.ViewHolder>{

    List<Timetable> timetables;
    SharedPreferences sharedPreference;
    Context context;

    public StudentTimetableAdapter(List<Timetable> timetables, Context context) {
        this.timetables = timetables;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public StudentTimetableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.timetable_card_view, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentTimetableAdapter.ViewHolder holder, int position) {

        Timetable timetable = timetables.get(position);
        holder.startTime.setText(timetable.getStartTime());
        holder.endTime.setText(timetable.getEndTime());
        holder.moduleName.setText(timetable.getModule());
//        holder.lecturerName.setText(timetable.getModule());
        holder.classroom.setText(timetable.getClassroom());
        holder.date.setText(timetable.getTimetableDate());
        holder.deleteTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sharedPreference.getString("token", null);
                String jwtToken = "Bearer " +name;

                Call<Void> deleteTimetable = API.getRetrofit().create(CallAPI.class).deleteTimetable(timetable.getTimetableId(), jwtToken);
                deleteTimetable.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(view.getContext(), TimetableAdmin.class);
                            context.startActivity(intent);
                            Toast.makeText(context, "Lecture Successfully Deleted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(context, "Cannot Delete Lecture", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "Error Occurred While Deleting", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return timetables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView moduleName;
        TextView batchId;
        TextView lecturerName;
        TextView startTime;
        TextView endTime;
        TextView date;
        TextView classroom;
        Button deleteTimetable;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            moduleName = itemView.findViewById(R.id.ModuleName);
            batchId = itemView.findViewById(R.id.BatchName);
            lecturerName = itemView.findViewById(R.id.LecturerName);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            date = itemView.findViewById(R.id.date);
            classroom = itemView.findViewById(R.id.classroom);
            deleteTimetable = itemView.findViewById(R.id.deleteTimetable);
        }
    }
}
