package com.example.eea_part2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eea_part2.Model.Timetable;
import com.example.eea_part2.R;

import java.util.List;

public class TimetableNonAdminAdapter extends RecyclerView.Adapter<TimetableNonAdminAdapter.ViewHolder>{

    List<Timetable> timetables;

    public TimetableNonAdminAdapter(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.timetable_card_student, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Timetable timetable = timetables.get(position);
        holder.startTime.setText(timetable.getStartTime());
        holder.endTime.setText(timetable.getEndTime());
        holder.moduleName.setText(timetable.getModule());
        holder.classroom.setText(timetable.getClassroom());
        holder.date.setText(timetable.getTimetableDate());

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.ModuleName);
            batchId = itemView.findViewById(R.id.BatchName);
            lecturerName = itemView.findViewById(R.id.LecturerName);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            date = itemView.findViewById(R.id.date);
            classroom = itemView.findViewById(R.id.classroom);
        }
    }
}
