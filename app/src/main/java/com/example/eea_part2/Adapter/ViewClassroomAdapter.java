package com.example.eea_part2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.AddTimetable;
import com.example.eea_part2.ClassroomList;
import com.example.eea_part2.Model.Classroom;
import com.example.eea_part2.Model.ModuleDTO;
import com.example.eea_part2.ModuleList;
import com.example.eea_part2.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewClassroomAdapter  extends RecyclerView.Adapter<ViewClassroomAdapter.ViewHolder> {



    List<Classroom> classroomList;
    SharedPreferences sharedPreference;

    public List<Classroom> getClassroomList() {
        return classroomList;
    }

    deleteClassroom deleteClickConfirm;
    Context mContext;




    public ViewClassroomAdapter(List<Classroom> classroomList, Context context) {
        this.classroomList = classroomList;
        this.mContext = context;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.classroom_card_view, parent,false);
        ViewHolder viewHolder = new ViewHolder(view, deleteClickConfirm);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Classroom classroom = classroomList.get(position);
        holder.classroomName.setText(classroom.getClassroomId());
        holder.seats.setText(classroom.getNoOfSeats());
        holder.floor.setText(classroom.getFloor());



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sharedPreference.getString("token", null);
                String jwtToken = "Bearer " +name;

                Call<Void> deleteClassroom = API.getRetrofit().create(CallAPI.class).deleteClassroom(classroom.getClassroomId(), jwtToken);
                deleteClassroom.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(view.getContext(), ClassroomList.class);
                            mContext.startActivity(intent);
                            Toast.makeText(mContext, "Classroom Successfully Deleted", Toast.LENGTH_LONG).show();

                        }
                        else{
                            Toast.makeText(mContext, "Cannot Delete Classroom", Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(mContext, "Error Occurred While Deleting", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return classroomList.size();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder{

        TextView classroomName;
        TextView floor;
        TextView seats;
        Button delete;


        public ViewHolder(@NonNull View itemView, deleteClassroom deleteClickButton) {
            super(itemView);

            classroomName = itemView.findViewById(R.id.classroom_classroom);
            floor = itemView.findViewById(R.id.floorCount);
            seats = itemView.findViewById(R.id.numOfSeatsCount);
            delete = itemView.findViewById(R.id.deleteClassroom);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    deleteClickButton.deleteClick(position);
                }
            });
        }
    }

    public interface deleteClassroom{
        public void deleteClick(int position);

    }

    public void deleteClickListener(deleteClassroom deleteClick){
        this.deleteClickConfirm=deleteClickConfirm;
    }
}
